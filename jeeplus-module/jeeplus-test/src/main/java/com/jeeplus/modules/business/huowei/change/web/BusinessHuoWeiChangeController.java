/**
 * 
 */
package com.jeeplus.modules.business.huowei.change.web;

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
import com.jeeplus.modules.business.huowei.change.entity.BusinessHuoWeiChange;
import com.jeeplus.modules.business.huowei.change.service.BusinessHuoWeiChangeService;

/**
 * 货位调整Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/huowei/change/businessHuoWeiChange")
public class BusinessHuoWeiChangeController extends BaseController {

	@Autowired
	private BusinessHuoWeiChangeService businessHuoWeiChangeService;
	
	@ModelAttribute
	public BusinessHuoWeiChange get(@RequestParam(required=false) String id) {
		BusinessHuoWeiChange entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessHuoWeiChangeService.get(id);
		}
		if (entity == null){
			entity = new BusinessHuoWeiChange();
		}
		return entity;
	}
	
	/**
	 * 货位调整列表页面
	 */
	@RequiresPermissions("business:huowei:change:businessHuoWeiChange:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessHuoWeiChange businessHuoWeiChange, Model model) {
		model.addAttribute("businessHuoWeiChange", businessHuoWeiChange);
		return "modules/business/huowei/change/businessHuoWeiChangeList";
	}
	
		/**
	 * 货位调整列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:huowei:change:businessHuoWeiChange:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessHuoWeiChange businessHuoWeiChange, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessHuoWeiChange> page = businessHuoWeiChangeService.findPage(new Page<BusinessHuoWeiChange>(request, response), businessHuoWeiChange); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑货位调整表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:huowei:change:businessHuoWeiChange:view","business:huowei:change:businessHuoWeiChange:add","business:huowei:change:businessHuoWeiChange:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessHuoWeiChange businessHuoWeiChange, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessHuoWeiChange", businessHuoWeiChange);
		return "modules/business/huowei/change/businessHuoWeiChangeForm";
	}

	/**
	 * 保存货位调整
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:huowei:change:businessHuoWeiChange:add","business:huowei:change:businessHuoWeiChange:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessHuoWeiChange businessHuoWeiChange, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessHuoWeiChange);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessHuoWeiChangeService.save(businessHuoWeiChange);//保存
		j.setSuccess(true);
		j.setMsg("保存货位调整成功");
		return j;
	}

	
	/**
	 * 批量删除货位调整
	 */
	@ResponseBody
	@RequiresPermissions("business:huowei:change:businessHuoWeiChange:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessHuoWeiChangeService.delete(businessHuoWeiChangeService.get(id));
		}
		j.setMsg("删除货位调整成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:huowei:change:businessHuoWeiChange:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessHuoWeiChange businessHuoWeiChange, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "货位调整"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessHuoWeiChange> page = businessHuoWeiChangeService.findPage(new Page<BusinessHuoWeiChange>(request, response, -1), businessHuoWeiChange);
    		new ExportExcel("货位调整", BusinessHuoWeiChange.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出货位调整记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessHuoWeiChange detail(String id) {
		return businessHuoWeiChangeService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:huowei:change:businessHuoWeiChange:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessHuoWeiChange> list = ei.getDataList(BusinessHuoWeiChange.class);
			for (BusinessHuoWeiChange businessHuoWeiChange : list){
				try{
					businessHuoWeiChangeService.save(businessHuoWeiChange);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条货位调整记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条货位调整记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入货位调整失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入货位调整数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:huowei:change:businessHuoWeiChange:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "货位调整数据导入模板.xlsx";
    		List<BusinessHuoWeiChange> list = Lists.newArrayList(); 
    		new ExportExcel("货位调整数据", BusinessHuoWeiChange.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}