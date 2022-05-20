/**
 *
 */
package com.jeeplus.modules.base.site.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.u8data.operation.entity.U8Operation;
import com.jeeplus.modules.u8data.operation.service.U8OperationService;
import com.jeeplus.modules.u8data.unit.entity.U8Unit;
import com.jeeplus.modules.u8data.unit.service.U8UnitService;
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
import com.jeeplus.modules.base.site.entity.BaseSite;
import com.jeeplus.modules.base.site.service.BaseSiteService;

/**
 * 工作站管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/site/baseSite")
public class BaseSiteController extends BaseController {

	@Autowired
	private BaseSiteService baseSiteService;
	
	@ModelAttribute
	public BaseSite get(@RequestParam(required=false) String id) {
		BaseSite entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseSiteService.get(id);
		}
		if (entity == null){
			entity = new BaseSite();
		}
		return entity;
	}
	@Autowired
	private U8OperationService u8OperationService;

	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(){
		AjaxJson json = new AjaxJson();
		try{
			U8Operation unit = new U8Operation();
			List<U8Operation> data = u8OperationService.findList(unit);
			if(data==null){
				json.setMsg("同步成功(u8数据空)");
				json.setSuccess(true);
				return json;
			}
			baseSiteService.sychU8(data);
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
	 * 工作站列表页面
	 */
	@RequiresPermissions("base:site:baseSite:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseSite baseSite, Model model) {
		model.addAttribute("baseSite", baseSite);
		return "modules/base/site/baseSiteList";
	}
	
		/**
	 * 工作站列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseSite baseSite, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseSite> page = baseSiteService.findPage(new Page<BaseSite>(request, response), baseSite); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工作站表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:site:baseSite:view","base:site:baseSite:add","base:site:baseSite:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseSite baseSite, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseSite", baseSite);
		return "modules/base/site/baseSiteForm";
	}

	/**
	 * 保存工作站
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:site:baseSite:add","base:site:baseSite:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseSite baseSite, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseSite);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseSiteService.save(baseSite);//保存
		j.setSuccess(true);
		j.setMsg("保存工作站成功");
		return j;
	}

	
	/**
	 * 批量删除工作站
	 */
	@ResponseBody
	@RequiresPermissions("base:site:baseSite:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseSiteService.delete(baseSiteService.get(id));
		}
		j.setMsg("删除工作站成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:site:baseSite:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseSite baseSite, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工作站"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseSite> page = baseSiteService.findPage(new Page<BaseSite>(request, response, -1), baseSite);
    		new ExportExcel("工作站", BaseSite.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工作站记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:site:baseSite:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseSite> list = ei.getDataList(BaseSite.class);
			for (BaseSite baseSite : list){
				try{
					baseSiteService.save(baseSite);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工作站记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条工作站记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入工作站失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入工作站数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:site:baseSite:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工作站数据导入模板.xlsx";
    		List<BaseSite> list = Lists.newArrayList(); 
    		new ExportExcel("工作站数据", BaseSite.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}