/**
 *
 */
package com.jeeplus.modules.base.factory.web;

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
import com.jeeplus.modules.base.factory.entity.BaseFactory;
import com.jeeplus.modules.base.factory.service.BaseFactoryService;

/**
 * 工厂管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/factory/baseFactory")
public class BaseFactoryController extends BaseController {

	@Autowired
	private BaseFactoryService baseFactoryService;
	
	@ModelAttribute
	public BaseFactory get(@RequestParam(required=false) String id) {
		BaseFactory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseFactoryService.get(id);
		}
		if (entity == null){
			entity = new BaseFactory();
		}
		return entity;
	}
	
	/**
	 * 工厂列表页面
	 */
	@RequiresPermissions("base:factory:baseFactory:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseFactory baseFactory, Model model) {
		model.addAttribute("baseFactory", baseFactory);
		return "modules/base/factory/baseFactoryList";
	}
	
		/**
	 * 工厂列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseFactory baseFactory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseFactory> page = baseFactoryService.findPage(new Page<BaseFactory>(request, response), baseFactory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工厂表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:factory:baseFactory:view","base:factory:baseFactory:add","base:factory:baseFactory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseFactory baseFactory, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseFactory", baseFactory);
		return "modules/base/factory/baseFactoryForm";
	}

	/**
	 * 保存工厂
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:factory:baseFactory:add","base:factory:baseFactory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseFactory baseFactory, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseFactory);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseFactoryService.save(baseFactory);//保存
		j.setSuccess(true);
		j.setMsg("保存工厂成功");
		return j;
	}

	
	/**
	 * 批量删除工厂
	 */
	@ResponseBody
	@RequiresPermissions("base:factory:baseFactory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseFactoryService.delete(baseFactoryService.get(id));
		}
		j.setMsg("删除工厂成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:factory:baseFactory:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseFactory baseFactory, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工厂"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseFactory> page = baseFactoryService.findPage(new Page<BaseFactory>(request, response, -1), baseFactory);
    		new ExportExcel("工厂", BaseFactory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工厂记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:factory:baseFactory:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseFactory> list = ei.getDataList(BaseFactory.class);
			for (BaseFactory baseFactory : list){
				try{
					baseFactoryService.save(baseFactory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工厂记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条工厂记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入工厂失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入工厂数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:factory:baseFactory:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工厂数据导入模板.xlsx";
    		List<BaseFactory> list = Lists.newArrayList(); 
    		new ExportExcel("工厂数据", BaseFactory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}