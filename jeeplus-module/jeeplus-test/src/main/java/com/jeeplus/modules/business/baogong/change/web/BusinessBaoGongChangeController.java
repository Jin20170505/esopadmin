/**
 *
 */
package com.jeeplus.modules.business.baogong.change.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.business.baogong.record.entity.BusinessBaoGongRecord;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
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
import com.jeeplus.modules.business.baogong.change.entity.BusinessBaoGongChange;
import com.jeeplus.modules.business.baogong.change.service.BusinessBaoGongChangeService;

/**
 * 报工修改Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/baogong/change/businessBaoGongChange")
public class BusinessBaoGongChangeController extends BaseController {

	@Autowired
	private BusinessBaoGongChangeService businessBaoGongChangeService;
	
	@ModelAttribute
	public BusinessBaoGongChange get(@RequestParam(required=false) String id) {
		BusinessBaoGongChange entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessBaoGongChangeService.get(id);
		}
		if (entity == null){
			entity = new BusinessBaoGongChange();
		}
		return entity;
	}
	
	/**
	 * 报工修改列表页面
	 */
	@RequiresPermissions("business:baogong:change:businessBaoGongChange:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessBaoGongChange businessBaoGongChange, Model model) {
		model.addAttribute("businessBaoGongChange", businessBaoGongChange);
		return "modules/business/baogong/change/businessBaoGongChangeList";
	}
	
		/**
	 * 报工修改列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:change:businessBaoGongChange:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessBaoGongChange businessBaoGongChange, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessBaoGongChange> page = businessBaoGongChangeService.findPage(new Page<BusinessBaoGongChange>(request, response), businessBaoGongChange); 
		return getBootstrapData(page);
	}

	@Autowired
	private BusinessBaoGongRecordService businessBaoGongRecordService;
	/**
	 * 查看，增加，编辑报工修改表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode,String recordid, BusinessBaoGongChange businessBaoGongChange, Model model) {
		model.addAttribute("mode", mode);
		if(StringUtils.isNotEmpty(recordid)){
			BusinessBaoGongRecord record = businessBaoGongRecordService.get(recordid);
			businessBaoGongChange.setBgcode(record.getBgcode());
			businessBaoGongChange.setBghid(record.getBghid());
			businessBaoGongChange.setGbid(record.getBgid());
			businessBaoGongChange.setRecordid(recordid);
			businessBaoGongChange.setDouser(record.getDouser());
			businessBaoGongChange.setFgnum(record.getFgnum());
			businessBaoGongChange.setGfnum(record.getGfnum());
			businessBaoGongChange.setLfnum(record.getLfnum());
			businessBaoGongChange.setHgnum(record.getHgnum());

			businessBaoGongChange.setYdouser(record.getDouser());
			businessBaoGongChange.setYfgnum(record.getFgnum());
			businessBaoGongChange.setYgfnum(record.getGfnum());
			businessBaoGongChange.setYlfnum(record.getLfnum());
			businessBaoGongChange.setYhgnum(record.getHgnum());
			businessBaoGongChange.setSite(record.getSite());
		}
		model.addAttribute("businessBaoGongChange", businessBaoGongChange);
		return "modules/business/baogong/change/businessBaoGongChangeForm";
	}

	/**
	 * 保存报工修改
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessBaoGongChange businessBaoGongChange, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessBaoGongChange);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessBaoGongChangeService.save(businessBaoGongChange);//保存
		j.setSuccess(true);
		j.setMsg("保存报工修改成功");
		return j;
	}

	
	/**
	 * 批量删除报工修改
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:change:businessBaoGongChange:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessBaoGongChangeService.delete(businessBaoGongChangeService.get(id));
		}
		j.setMsg("删除报工修改成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:change:businessBaoGongChange:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessBaoGongChange businessBaoGongChange, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报工修改"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessBaoGongChange> page = businessBaoGongChangeService.findPage(new Page<BusinessBaoGongChange>(request, response, -1), businessBaoGongChange);
    		new ExportExcel("报工修改", BusinessBaoGongChange.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出报工修改记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:change:businessBaoGongChange:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessBaoGongChange> list = ei.getDataList(BusinessBaoGongChange.class);
			for (BusinessBaoGongChange businessBaoGongChange : list){
				try{
					businessBaoGongChangeService.save(businessBaoGongChange);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条报工修改记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条报工修改记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入报工修改失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入报工修改数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:change:businessBaoGongChange:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报工修改数据导入模板.xlsx";
    		List<BusinessBaoGongChange> list = Lists.newArrayList(); 
    		new ExportExcel("报工修改数据", BusinessBaoGongChange.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}