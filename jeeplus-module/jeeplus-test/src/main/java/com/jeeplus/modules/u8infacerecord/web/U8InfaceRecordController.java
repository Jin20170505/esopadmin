/**
 *
 */
package com.jeeplus.modules.u8infacerecord.web;

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
import com.jeeplus.modules.u8infacerecord.entity.U8InfaceRecord;
import com.jeeplus.modules.u8infacerecord.service.U8InfaceRecordService;

/**
 * ERP接口记录Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/u8infacerecord/u8InfaceRecord")
public class U8InfaceRecordController extends BaseController {

	@Autowired
	private U8InfaceRecordService u8InfaceRecordService;
	
	@ModelAttribute
	public U8InfaceRecord get(@RequestParam(required=false) String id) {
		U8InfaceRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = u8InfaceRecordService.get(id);
		}
		if (entity == null){
			entity = new U8InfaceRecord();
		}
		return entity;
	}
	
	/**
	 * ERP接口记录列表页面
	 */
	@RequiresPermissions("u8infacerecord:u8InfaceRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(U8InfaceRecord u8InfaceRecord, Model model) {
		model.addAttribute("u8InfaceRecord", u8InfaceRecord);
		return "modules/u8infacerecord/u8InfaceRecordList";
	}
	
		/**
	 * ERP接口记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("u8infacerecord:u8InfaceRecord:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(U8InfaceRecord u8InfaceRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<U8InfaceRecord> page = u8InfaceRecordService.findPage(new Page<U8InfaceRecord>(request, response), u8InfaceRecord); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑ERP接口记录表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"u8infacerecord:u8InfaceRecord:view","u8infacerecord:u8InfaceRecord:add","u8infacerecord:u8InfaceRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, U8InfaceRecord u8InfaceRecord, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("u8InfaceRecord", u8InfaceRecord);
		return "modules/u8infacerecord/u8InfaceRecordForm";
	}

	/**
	 * 保存ERP接口记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"u8infacerecord:u8InfaceRecord:add","u8infacerecord:u8InfaceRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(U8InfaceRecord u8InfaceRecord, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(u8InfaceRecord);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		u8InfaceRecordService.save(u8InfaceRecord);//保存
		j.setSuccess(true);
		j.setMsg("保存ERP接口记录成功");
		return j;
	}

	
	/**
	 * 批量删除ERP接口记录
	 */
	@ResponseBody
	@RequiresPermissions("u8infacerecord:u8InfaceRecord:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			u8InfaceRecordService.delete(u8InfaceRecordService.get(id));
		}
		j.setMsg("删除ERP接口记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("u8infacerecord:u8InfaceRecord:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(U8InfaceRecord u8InfaceRecord, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ERP接口记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<U8InfaceRecord> page = u8InfaceRecordService.findPage(new Page<U8InfaceRecord>(request, response, -1), u8InfaceRecord);
    		new ExportExcel("ERP接口记录", U8InfaceRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出ERP接口记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("u8infacerecord:u8InfaceRecord:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<U8InfaceRecord> list = ei.getDataList(U8InfaceRecord.class);
			for (U8InfaceRecord u8InfaceRecord : list){
				try{
					u8InfaceRecordService.save(u8InfaceRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条ERP接口记录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条ERP接口记录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入ERP接口记录失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入ERP接口记录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("u8infacerecord:u8InfaceRecord:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ERP接口记录数据导入模板.xlsx";
    		List<U8InfaceRecord> list = Lists.newArrayList(); 
    		new ExportExcel("ERP接口记录数据", U8InfaceRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}