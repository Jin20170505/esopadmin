/**
 *
 */
package com.jeeplus.modules.business.nengyuan.energy.web;

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
import com.jeeplus.modules.business.nengyuan.energy.entity.BusinessNengYuanEnergy;
import com.jeeplus.modules.business.nengyuan.energy.service.BusinessNengYuanEnergyService;

/**
 * 电能管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/nengyuan/energy/businessNengYuanEnergy")
public class BusinessNengYuanEnergyController extends BaseController {

	@Autowired
	private BusinessNengYuanEnergyService businessNengYuanEnergyService;
	
	@ModelAttribute
	public BusinessNengYuanEnergy get(@RequestParam(required=false) String id) {
		BusinessNengYuanEnergy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessNengYuanEnergyService.get(id);
		}
		if (entity == null){
			entity = new BusinessNengYuanEnergy();
		}
		return entity;
	}
	
	/**
	 * 电能列表页面
	 */
	@RequiresPermissions("business:nengyuan:energy:businessNengYuanEnergy:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessNengYuanEnergy businessNengYuanEnergy, Model model) {
		model.addAttribute("businessNengYuanEnergy", businessNengYuanEnergy);
		return "modules/business/nengyuan/energy/businessNengYuanEnergyList";
	}
	
		/**
	 * 电能列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:energy:businessNengYuanEnergy:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessNengYuanEnergy businessNengYuanEnergy, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessNengYuanEnergy> page = businessNengYuanEnergyService.findPage(new Page<BusinessNengYuanEnergy>(request, response), businessNengYuanEnergy); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑电能表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:nengyuan:energy:businessNengYuanEnergy:view","business:nengyuan:energy:businessNengYuanEnergy:add","business:nengyuan:energy:businessNengYuanEnergy:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessNengYuanEnergy businessNengYuanEnergy, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessNengYuanEnergy", businessNengYuanEnergy);
		return "modules/business/nengyuan/energy/businessNengYuanEnergyForm";
	}

	/**
	 * 保存电能
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:nengyuan:energy:businessNengYuanEnergy:add","business:nengyuan:energy:businessNengYuanEnergy:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessNengYuanEnergy businessNengYuanEnergy, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessNengYuanEnergy);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessNengYuanEnergyService.save(businessNengYuanEnergy);//保存
		j.setSuccess(true);
		j.setMsg("保存电能成功");
		return j;
	}

	
	/**
	 * 批量删除电能
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:energy:businessNengYuanEnergy:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessNengYuanEnergyService.delete(businessNengYuanEnergyService.get(id));
		}
		j.setMsg("删除电能成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:energy:businessNengYuanEnergy:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessNengYuanEnergy businessNengYuanEnergy, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "电能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessNengYuanEnergy> page = businessNengYuanEnergyService.findPage(new Page<BusinessNengYuanEnergy>(request, response, -1), businessNengYuanEnergy);
    		new ExportExcel("电能", BusinessNengYuanEnergy.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出电能记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:energy:businessNengYuanEnergy:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessNengYuanEnergy> list = ei.getDataList(BusinessNengYuanEnergy.class);
			for (BusinessNengYuanEnergy businessNengYuanEnergy : list){
				try{
					businessNengYuanEnergyService.save(businessNengYuanEnergy);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条电能记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条电能记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入电能失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入电能数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:nengyuan:energy:businessNengYuanEnergy:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "电能数据导入模板.xlsx";
    		List<BusinessNengYuanEnergy> list = Lists.newArrayList(); 
    		new ExportExcel("电能数据", BusinessNengYuanEnergy.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}