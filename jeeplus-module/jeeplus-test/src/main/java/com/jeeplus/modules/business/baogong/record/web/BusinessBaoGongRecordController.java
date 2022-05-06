/**
 *
 */
package com.jeeplus.modules.business.baogong.record.web;

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
import com.jeeplus.modules.business.baogong.record.entity.BusinessBaoGongRecord;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;

/**
 * 员工报工Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/baogong/record/businessBaoGongRecord")
public class BusinessBaoGongRecordController extends BaseController {

	@Autowired
	private BusinessBaoGongRecordService businessBaoGongRecordService;
	
	@ModelAttribute
	public BusinessBaoGongRecord get(@RequestParam(required=false) String id) {
		BusinessBaoGongRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessBaoGongRecordService.get(id);
		}
		if (entity == null){
			entity = new BusinessBaoGongRecord();
		}
		return entity;
	}
	
	/**
	 * 员工报工列表页面
	 */
	@RequiresPermissions("business:baogong:record:businessBaoGongRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessBaoGongRecord businessBaoGongRecord, Model model) {
		model.addAttribute("businessBaoGongRecord", businessBaoGongRecord);
		return "modules/business/baogong/record/businessBaoGongRecordList";
	}
	
		/**
	 * 员工报工列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:record:businessBaoGongRecord:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessBaoGongRecord businessBaoGongRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessBaoGongRecord> page = businessBaoGongRecordService.findPage(new Page<BusinessBaoGongRecord>(request, response), businessBaoGongRecord); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工报工表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:baogong:record:businessBaoGongRecord:view","business:baogong:record:businessBaoGongRecord:add","business:baogong:record:businessBaoGongRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessBaoGongRecord businessBaoGongRecord, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessBaoGongRecord", businessBaoGongRecord);
		return "modules/business/baogong/record/businessBaoGongRecordForm";
	}

	/**
	 * 保存员工报工
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:baogong:record:businessBaoGongRecord:add","business:baogong:record:businessBaoGongRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessBaoGongRecord businessBaoGongRecord, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessBaoGongRecord);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessBaoGongRecordService.save(businessBaoGongRecord);//保存
		j.setSuccess(true);
		j.setMsg("保存员工报工成功");
		return j;
	}

	
	/**
	 * 批量删除员工报工
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:record:businessBaoGongRecord:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessBaoGongRecordService.delete(businessBaoGongRecordService.get(id));
		}
		j.setMsg("删除员工报工成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:record:businessBaoGongRecord:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessBaoGongRecord businessBaoGongRecord, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工报工"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessBaoGongRecord> page = businessBaoGongRecordService.findPage(new Page<BusinessBaoGongRecord>(request, response, -1), businessBaoGongRecord);
    		new ExportExcel("员工报工", BusinessBaoGongRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工报工记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:record:businessBaoGongRecord:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessBaoGongRecord> list = ei.getDataList(BusinessBaoGongRecord.class);
			for (BusinessBaoGongRecord businessBaoGongRecord : list){
				try{
					businessBaoGongRecordService.save(businessBaoGongRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工报工记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工报工记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工报工失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工报工数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:record:businessBaoGongRecord:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工报工数据导入模板.xlsx";
    		List<BusinessBaoGongRecord> list = Lists.newArrayList(); 
    		new ExportExcel("员工报工数据", BusinessBaoGongRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}