/**
 *
 */
package com.jeeplus.modules.base.workshop.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.jeeplus.modules.base.workshop.entity.BaseWorkShop;
import com.jeeplus.modules.base.workshop.service.BaseWorkShopService;

/**
 * 车间管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/workshop/baseWorkShop")
public class BaseWorkShopController extends BaseController {

	@Autowired
	private BaseWorkShopService baseWorkShopService;
	
	@ModelAttribute
	public BaseWorkShop get(@RequestParam(required=false) String id) {
		BaseWorkShop entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseWorkShopService.get(id);
		}
		if (entity == null){
			entity = new BaseWorkShop();
		}
		return entity;
	}
	
	/**
	 * 车间列表页面
	 */
	@RequiresPermissions("base:workshop:baseWorkShop:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseWorkShop baseWorkShop, Model model) {
		model.addAttribute("baseWorkShop", baseWorkShop);
		return "modules/base/workshop/baseWorkShopList";
	}
	
		/**
	 * 车间列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:workshop:baseWorkShop:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseWorkShop baseWorkShop, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseWorkShop> page = baseWorkShopService.findPage(new Page<BaseWorkShop>(request, response), baseWorkShop); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑车间表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:workshop:baseWorkShop:view","base:workshop:baseWorkShop:add","base:workshop:baseWorkShop:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseWorkShop baseWorkShop, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseWorkShop", baseWorkShop);
		return "modules/base/workshop/baseWorkShopForm";
	}

	/**
	 * 保存车间
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:workshop:baseWorkShop:add","base:workshop:baseWorkShop:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseWorkShop baseWorkShop, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseWorkShop);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseWorkShopService.save(baseWorkShop);//保存
		j.setSuccess(true);
		j.setMsg("保存车间成功");
		return j;
	}

	
	/**
	 * 批量删除车间
	 */
	@ResponseBody
	@RequiresPermissions("base:workshop:baseWorkShop:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseWorkShopService.delete(baseWorkShopService.get(id));
		}
		j.setMsg("删除车间成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:workshop:baseWorkShop:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseWorkShop baseWorkShop, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "车间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseWorkShop> page = baseWorkShopService.findPage(new Page<BaseWorkShop>(request, response, -1), baseWorkShop);
    		new ExportExcel("车间", BaseWorkShop.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出车间记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:workshop:baseWorkShop:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseWorkShop> list = ei.getDataList(BaseWorkShop.class);
			for (BaseWorkShop baseWorkShop : list){
				try{
					baseWorkShopService.save(baseWorkShop);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条车间记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条车间记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入车间失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入车间数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:workshop:baseWorkShop:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "车间数据导入模板.xlsx";
    		List<BaseWorkShop> list = Lists.newArrayList(); 
    		new ExportExcel("车间数据", BaseWorkShop.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}