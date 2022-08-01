/**
 *
 */
package com.jeeplus.modules.base.huowei.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import com.jeeplus.modules.u8data.warehouse.entity.U8Position;
import com.jeeplus.modules.u8data.warehouse.service.U8PositionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.huowei.service.BaseHuoWeiService;

/**
 * 货位档案Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/huowei/baseHuoWei")
public class BaseHuoWeiController extends BaseController {

	@Autowired
	private BaseHuoWeiService baseHuoWeiService;
	
	@ModelAttribute
	public BaseHuoWei get(@RequestParam(required=false) String id) {
		BaseHuoWei entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseHuoWeiService.get(id);
		}
		if (entity == null){
			entity = new BaseHuoWei();
		}
		return entity;
	}
	
	/**
	 * 货位列表页面
	 */
	@RequiresPermissions("base:huowei:baseHuoWei:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseHuoWei baseHuoWei, Model model) {
		model.addAttribute("baseHuoWei", baseHuoWei);
		return "modules/base/huowei/baseHuoWeiList";
	}

	/**
	 * 货位标签打印
	 * @param rids
	 * @param model
	 * @return
	 */
	@RequestMapping("gotoprint")
	public String gotoprint(String rids,Model model){
		model.addAttribute("beans",baseHuoWeiService.findPrintInfo(rids));
		return "modules/base/huowei/huoweiprint";
	}

	@Autowired
	private U8PositionService u8PositionService;

	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(){
		AjaxJson json = new AjaxJson();
		try{
			U8Position position = new U8Position();
			List<U8Position> data = u8PositionService.findList(position);
			if(data==null){
				json.setMsg("同步成功(ERP货位数据空)");
				json.setSuccess(true);
				return json;
			}
			baseHuoWeiService.sychU8(data);
			json.setMsg("同步成功");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("同步失败,原因："+e.getMessage());
		}
		return json;
	}
		/**
	 * 货位列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:huowei:baseHuoWei:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseHuoWei baseHuoWei, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseHuoWei> page = baseHuoWeiService.findPage(new Page<BaseHuoWei>(request, response), baseHuoWei); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑货位表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:huowei:baseHuoWei:view","base:huowei:baseHuoWei:add","base:huowei:baseHuoWei:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseHuoWei baseHuoWei, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseHuoWei", baseHuoWei);
		return "modules/base/huowei/baseHuoWeiForm";
	}

	/**
	 * 保存货位
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:huowei:baseHuoWei:add","base:huowei:baseHuoWei:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseHuoWei baseHuoWei, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseHuoWei);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseHuoWeiService.save(baseHuoWei);//保存
		j.setSuccess(true);
		j.setMsg("保存货位成功");
		return j;
	}

	
	/**
	 * 批量删除货位
	 */
	@ResponseBody
	@RequiresPermissions("base:huowei:baseHuoWei:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseHuoWeiService.delete(baseHuoWeiService.get(id));
		}
		j.setMsg("删除货位成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:huowei:baseHuoWei:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseHuoWei baseHuoWei, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "货位"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseHuoWei> page = baseHuoWeiService.findPage(new Page<BaseHuoWei>(request, response, -1), baseHuoWei);
    		new ExportExcel("货位", BaseHuoWei.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出货位记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:huowei:baseHuoWei:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseHuoWei> list = ei.getDataList(BaseHuoWei.class);
			for (BaseHuoWei baseHuoWei : list){
				try{
					baseHuoWeiService.save(baseHuoWei);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条货位记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条货位记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入货位失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入货位数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:huowei:baseHuoWei:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "货位数据导入模板.xlsx";
    		List<BaseHuoWei> list = Lists.newArrayList(); 
    		new ExportExcel("货位数据", BaseHuoWei.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}