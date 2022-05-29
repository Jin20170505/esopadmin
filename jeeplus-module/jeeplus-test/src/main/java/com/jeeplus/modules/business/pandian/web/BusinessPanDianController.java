/**
 * 
 */
package com.jeeplus.modules.business.pandian.web;

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
import com.jeeplus.modules.business.pandian.entity.BusinessPanDian;
import com.jeeplus.modules.business.pandian.service.BusinessPanDianService;

/**
 * 盘点单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/pandian/businessPanDian")
public class BusinessPanDianController extends BaseController {

	@Autowired
	private BusinessPanDianService businessPanDianService;
	
	@ModelAttribute
	public BusinessPanDian get(@RequestParam(required=false) String id) {
		BusinessPanDian entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessPanDianService.get(id);
		}
		if (entity == null){
			entity = new BusinessPanDian();
		}
		return entity;
	}
	
	/**
	 * 盘点单列表页面
	 */
	@RequiresPermissions("business:pandian:businessPanDian:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessPanDian businessPanDian, Model model) {
		model.addAttribute("businessPanDian", businessPanDian);
		return "modules/business/pandian/businessPanDianList";
	}
	
		/**
	 * 盘点单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:pandian:businessPanDian:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessPanDian businessPanDian, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessPanDian> page = businessPanDianService.findPage(new Page<BusinessPanDian>(request, response), businessPanDian); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑盘点单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:pandian:businessPanDian:view","business:pandian:businessPanDian:add","business:pandian:businessPanDian:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessPanDian businessPanDian, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessPanDian", businessPanDian);
		return "modules/business/pandian/businessPanDianForm";
	}

	/**
	 * 保存盘点单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:pandian:businessPanDian:add","business:pandian:businessPanDian:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessPanDian businessPanDian, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessPanDian);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessPanDianService.save(businessPanDian);//保存
		j.setSuccess(true);
		j.setMsg("保存盘点单成功");
		return j;
	}

	
	/**
	 * 批量删除盘点单
	 */
	@ResponseBody
	@RequiresPermissions("business:pandian:businessPanDian:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessPanDianService.delete(businessPanDianService.get(id));
		}
		j.setMsg("删除盘点单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:pandian:businessPanDian:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessPanDian businessPanDian, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "盘点单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessPanDian> page = businessPanDianService.findPage(new Page<BusinessPanDian>(request, response, -1), businessPanDian);
    		new ExportExcel("盘点单", BusinessPanDian.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出盘点单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessPanDian detail(String id) {
		return businessPanDianService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:pandian:businessPanDian:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessPanDian> list = ei.getDataList(BusinessPanDian.class);
			for (BusinessPanDian businessPanDian : list){
				try{
					businessPanDianService.save(businessPanDian);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条盘点单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条盘点单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入盘点单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入盘点单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:pandian:businessPanDian:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "盘点单数据导入模板.xlsx";
    		List<BusinessPanDian> list = Lists.newArrayList(); 
    		new ExportExcel("盘点单数据", BusinessPanDian.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}