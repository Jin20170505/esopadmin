/**
 *
 */
package com.jeeplus.modules.business.nengyuan.air.web;

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
import com.jeeplus.modules.business.nengyuan.air.entity.BusinessNengYuanAir;
import com.jeeplus.modules.business.nengyuan.air.service.BusinessNengYuanAirService;

/**
 * 气能管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/nengyuan/air/businessNengYuanAir")
public class BusinessNengYuanAirController extends BaseController {

	@Autowired
	private BusinessNengYuanAirService businessNengYuanAirService;
	
	@ModelAttribute
	public BusinessNengYuanAir get(@RequestParam(required=false) String id) {
		BusinessNengYuanAir entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessNengYuanAirService.get(id);
		}
		if (entity == null){
			entity = new BusinessNengYuanAir();
		}
		return entity;
	}
	
	/**
	 * 气能列表页面
	 */
	@RequiresPermissions("business:nengyuan:air:businessNengYuanAir:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessNengYuanAir businessNengYuanAir, Model model) {
		model.addAttribute("businessNengYuanAir", businessNengYuanAir);
		return "modules/business/nengyuan/air/businessNengYuanAirList";
	}
	
		/**
	 * 气能列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:air:businessNengYuanAir:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessNengYuanAir businessNengYuanAir, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessNengYuanAir> page = businessNengYuanAirService.findPage(new Page<BusinessNengYuanAir>(request, response), businessNengYuanAir); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑气能表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:nengyuan:air:businessNengYuanAir:view","business:nengyuan:air:businessNengYuanAir:add","business:nengyuan:air:businessNengYuanAir:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessNengYuanAir businessNengYuanAir, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessNengYuanAir", businessNengYuanAir);
		return "modules/business/nengyuan/air/businessNengYuanAirForm";
	}

	/**
	 * 保存气能
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:nengyuan:air:businessNengYuanAir:add","business:nengyuan:air:businessNengYuanAir:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessNengYuanAir businessNengYuanAir, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessNengYuanAir);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessNengYuanAirService.save(businessNengYuanAir);//保存
		j.setSuccess(true);
		j.setMsg("保存气能成功");
		return j;
	}

	
	/**
	 * 批量删除气能
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:air:businessNengYuanAir:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessNengYuanAirService.delete(businessNengYuanAirService.get(id));
		}
		j.setMsg("删除气能成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:air:businessNengYuanAir:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessNengYuanAir businessNengYuanAir, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "气能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessNengYuanAir> page = businessNengYuanAirService.findPage(new Page<BusinessNengYuanAir>(request, response, -1), businessNengYuanAir);
    		new ExportExcel("气能", BusinessNengYuanAir.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出气能记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:air:businessNengYuanAir:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessNengYuanAir> list = ei.getDataList(BusinessNengYuanAir.class);
			for (BusinessNengYuanAir businessNengYuanAir : list){
				try{
					businessNengYuanAirService.save(businessNengYuanAir);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条气能记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条气能记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入气能失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入气能数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:air:businessNengYuanAir:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "气能数据导入模板.xlsx";
    		List<BusinessNengYuanAir> list = Lists.newArrayList(); 
    		new ExportExcel("气能数据", BusinessNengYuanAir.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}