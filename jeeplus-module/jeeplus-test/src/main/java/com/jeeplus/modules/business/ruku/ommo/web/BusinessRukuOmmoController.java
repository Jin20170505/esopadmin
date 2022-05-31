/**
 * 
 */
package com.jeeplus.modules.business.ruku.ommo.web;

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
import com.jeeplus.modules.business.ruku.ommo.entity.BusinessRukuOmmo;
import com.jeeplus.modules.business.ruku.ommo.service.BusinessRukuOmmoService;

/**
 * 委外入库Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ruku/ommo/businessRukuOmmo")
public class BusinessRukuOmmoController extends BaseController {

	@Autowired
	private BusinessRukuOmmoService businessRukuOmmoService;
	
	@ModelAttribute
	public BusinessRukuOmmo get(@RequestParam(required=false) String id) {
		BusinessRukuOmmo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessRukuOmmoService.get(id);
		}
		if (entity == null){
			entity = new BusinessRukuOmmo();
		}
		return entity;
	}
	
	/**
	 * 委外入库列表页面
	 */
	@RequiresPermissions("business:ruku:ommo:businessRukuOmmo:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessRukuOmmo businessRukuOmmo, Model model) {
		model.addAttribute("businessRukuOmmo", businessRukuOmmo);
		return "modules/business/ruku/ommo/businessRukuOmmoList";
	}
	
		/**
	 * 委外入库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:ommo:businessRukuOmmo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessRukuOmmo businessRukuOmmo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessRukuOmmo> page = businessRukuOmmoService.findPage(new Page<BusinessRukuOmmo>(request, response), businessRukuOmmo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑委外入库表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:ruku:ommo:businessRukuOmmo:view","business:ruku:ommo:businessRukuOmmo:add","business:ruku:ommo:businessRukuOmmo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessRukuOmmo businessRukuOmmo, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessRukuOmmo", businessRukuOmmo);
		return "modules/business/ruku/ommo/businessRukuOmmoForm";
	}

	/**
	 * 保存委外入库
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ruku:ommo:businessRukuOmmo:add","business:ruku:ommo:businessRukuOmmo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessRukuOmmo businessRukuOmmo, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessRukuOmmo);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessRukuOmmoService.save(businessRukuOmmo);//保存
		j.setSuccess(true);
		j.setMsg("保存委外入库成功");
		return j;
	}

	
	/**
	 * 批量删除委外入库
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:ommo:businessRukuOmmo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessRukuOmmoService.delete(businessRukuOmmoService.get(id));
		}
		j.setMsg("删除委外入库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:ommo:businessRukuOmmo:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessRukuOmmo businessRukuOmmo, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外入库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessRukuOmmo> page = businessRukuOmmoService.findPage(new Page<BusinessRukuOmmo>(request, response, -1), businessRukuOmmo);
    		new ExportExcel("委外入库", BusinessRukuOmmo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出委外入库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessRukuOmmo detail(String id) {
		return businessRukuOmmoService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:ommo:businessRukuOmmo:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessRukuOmmo> list = ei.getDataList(BusinessRukuOmmo.class);
			for (BusinessRukuOmmo businessRukuOmmo : list){
				try{
					businessRukuOmmoService.save(businessRukuOmmo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条委外入库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条委外入库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入委外入库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入委外入库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:ommo:businessRukuOmmo:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外入库数据导入模板.xlsx";
    		List<BusinessRukuOmmo> list = Lists.newArrayList(); 
    		new ExportExcel("委外入库数据", BusinessRukuOmmo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}