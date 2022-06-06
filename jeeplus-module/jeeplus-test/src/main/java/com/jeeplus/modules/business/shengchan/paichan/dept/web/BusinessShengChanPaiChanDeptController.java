/**
 *
 */
package com.jeeplus.modules.business.shengchan.paichan.dept.web;

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
import com.jeeplus.modules.business.shengchan.paichan.dept.entity.BusinessShengChanPaiChanDept;
import com.jeeplus.modules.business.shengchan.paichan.dept.service.BusinessShengChanPaiChanDeptService;

/**
 * 排产部门Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/shengchan/paichan/dept/businessShengChanPaiChanDept")
public class BusinessShengChanPaiChanDeptController extends BaseController {

	@Autowired
	private BusinessShengChanPaiChanDeptService businessShengChanPaiChanDeptService;
	
	@ModelAttribute
	public BusinessShengChanPaiChanDept get(@RequestParam(required=false) String id) {
		BusinessShengChanPaiChanDept entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessShengChanPaiChanDeptService.get(id);
		}
		if (entity == null){
			entity = new BusinessShengChanPaiChanDept();
		}
		return entity;
	}
	
	/**
	 * 排产部门列表页面
	 */
	@RequiresPermissions("business:shengchan:paichan:dept:businessShengChanPaiChanDept:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanPaiChanDept businessShengChanPaiChanDept, Model model) {
		model.addAttribute("businessShengChanPaiChanDept", businessShengChanPaiChanDept);
		return "modules/business/shengchan/paichan/dept/businessShengChanPaiChanDeptList";
	}
	
		/**
	 * 排产部门列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:dept:businessShengChanPaiChanDept:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessShengChanPaiChanDept businessShengChanPaiChanDept, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanPaiChanDept> page = businessShengChanPaiChanDeptService.findPage(new Page<BusinessShengChanPaiChanDept>(request, response), businessShengChanPaiChanDept); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑排产部门表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:shengchan:paichan:dept:businessShengChanPaiChanDept:view","business:shengchan:paichan:dept:businessShengChanPaiChanDept:add","business:shengchan:paichan:dept:businessShengChanPaiChanDept:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessShengChanPaiChanDept businessShengChanPaiChanDept, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessShengChanPaiChanDept", businessShengChanPaiChanDept);
		return "modules/business/shengchan/paichan/dept/businessShengChanPaiChanDeptForm";
	}

	/**
	 * 保存排产部门
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:shengchan:paichan:dept:businessShengChanPaiChanDept:add","business:shengchan:paichan:dept:businessShengChanPaiChanDept:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanPaiChanDept businessShengChanPaiChanDept, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessShengChanPaiChanDept);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessShengChanPaiChanDeptService.save(businessShengChanPaiChanDept);//保存
		j.setSuccess(true);
		j.setMsg("保存排产部门成功");
		return j;
	}

	
	/**
	 * 批量删除排产部门
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:dept:businessShengChanPaiChanDept:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanPaiChanDeptService.delete(businessShengChanPaiChanDeptService.get(id));
		}
		j.setMsg("删除排产部门成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:dept:businessShengChanPaiChanDept:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanPaiChanDept businessShengChanPaiChanDept, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "排产部门"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanPaiChanDept> page = businessShengChanPaiChanDeptService.findPage(new Page<BusinessShengChanPaiChanDept>(request, response, -1), businessShengChanPaiChanDept);
    		new ExportExcel("排产部门", BusinessShengChanPaiChanDept.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出排产部门记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:dept:businessShengChanPaiChanDept:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessShengChanPaiChanDept> list = ei.getDataList(BusinessShengChanPaiChanDept.class);
			for (BusinessShengChanPaiChanDept businessShengChanPaiChanDept : list){
				try{
					businessShengChanPaiChanDeptService.save(businessShengChanPaiChanDept);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条排产部门记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条排产部门记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入排产部门失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入排产部门数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:dept:businessShengChanPaiChanDept:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "排产部门数据导入模板.xlsx";
    		List<BusinessShengChanPaiChanDept> list = Lists.newArrayList(); 
    		new ExportExcel("排产部门数据", BusinessShengChanPaiChanDept.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}