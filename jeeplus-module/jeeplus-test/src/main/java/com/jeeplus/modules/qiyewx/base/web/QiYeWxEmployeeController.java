/**
 *
 */
package com.jeeplus.modules.qiyewx.base.web;

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
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;
import com.jeeplus.modules.qiyewx.base.service.QiYeWxEmployeeService;

/**
 * 企业微信_员工Controller
 * @author Jin
 * @version 2021-08-25
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/base/qiYeWxEmployee")
public class QiYeWxEmployeeController extends BaseController {

	@Autowired
	private QiYeWxEmployeeService qiYeWxEmployeeService;
	
	@ModelAttribute
	public QiYeWxEmployee get(@RequestParam(required=false) String id) {
		QiYeWxEmployee entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxEmployeeService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxEmployee();
		}
		return entity;
	}
	
	/**
	 * 企业微信_员工列表页面
	 */
	@RequiresPermissions("qiyewx:base:qiYeWxEmployee:list")
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxEmployee qiYeWxEmployee, Model model) {
		model.addAttribute("qiYeWxEmployee", qiYeWxEmployee);
		return "modules/qiyewx/base/qiYeWxEmployeeList";
	}

	/***
	 * 更新员工状态
	 * @param rids
	 * @param status 1：已激活 2：已禁用 3 未激活 5 退出企业
	 * @return
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public AjaxJson updateStatus(String rids,Integer status){
		AjaxJson json = new AjaxJson();
		try {
			qiYeWxEmployeeService.updateStatus(rids,status);
			json.setSuccess(true);
			json.setMsg("修改成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setMsg("修改失败啦");
			json.setSuccess(false);
		}
		return json;
	}

		/**
	 * 企业微信_员工列表数据
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxEmployee:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYeWxEmployee qiYeWxEmployee, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYeWxEmployee> page = qiYeWxEmployeeService.findPage(new Page<QiYeWxEmployee>(request, response), qiYeWxEmployee); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑企业微信_员工表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:base:qiYeWxEmployee:view","qiyewx:base:qiYeWxEmployee:add","qiyewx:base:qiYeWxEmployee:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxEmployee qiYeWxEmployee, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxEmployee", qiYeWxEmployee);
		return "modules/qiyewx/base/qiYeWxEmployeeForm";
	}

	/**
	 * 保存企业微信_员工
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:base:qiYeWxEmployee:add","qiyewx:base:qiYeWxEmployee:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxEmployee qiYeWxEmployee, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxEmployee);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYeWxEmployeeService.save(qiYeWxEmployee);//保存
		j.setSuccess(true);
		j.setMsg("保存企业微信_员工成功");
		return j;
	}

	/**
	 * 同步微信数据
	 */
	@ResponseBody
	@RequestMapping(value = "syncData")
	public AjaxJson syncData() {
		AjaxJson j = new AjaxJson();
		try {
			j.setSuccess(true);
			qiYeWxEmployeeService.syncData();
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		j.setMsg("同步成功");
		return j;
	}
	/**
	 * 批量删除企业微信_员工
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxEmployee:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYeWxEmployeeService.delete(qiYeWxEmployeeService.get(id));
		}
		j.setMsg("删除企业微信_员工成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxEmployee:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYeWxEmployee qiYeWxEmployee, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "企业微信_员工"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYeWxEmployee> page = qiYeWxEmployeeService.findPage(new Page<QiYeWxEmployee>(request, response, -1), qiYeWxEmployee);
    		new ExportExcel("企业微信_员工", QiYeWxEmployee.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出企业微信_员工记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxEmployee:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYeWxEmployee> list = ei.getDataList(QiYeWxEmployee.class);
			for (QiYeWxEmployee qiYeWxEmployee : list){
				try{
					qiYeWxEmployeeService.save(qiYeWxEmployee);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条企业微信_员工记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条企业微信_员工记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入企业微信_员工失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入企业微信_员工数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxEmployee:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "企业微信_员工数据导入模板.xlsx";
    		List<QiYeWxEmployee> list = Lists.newArrayList(); 
    		new ExportExcel("企业微信_员工数据", QiYeWxEmployee.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}