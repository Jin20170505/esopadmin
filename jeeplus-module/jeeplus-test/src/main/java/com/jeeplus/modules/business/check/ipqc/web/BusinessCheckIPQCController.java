/**
 *
 */
package com.jeeplus.modules.business.check.ipqc.web;

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
import com.jeeplus.modules.business.check.ipqc.entity.BusinessCheckIPQC;
import com.jeeplus.modules.business.check.ipqc.service.BusinessCheckIPQCService;

/**
 * IPQC检验Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/check/ipqc/businessCheckIPQC")
public class BusinessCheckIPQCController extends BaseController {

	@Autowired
	private BusinessCheckIPQCService businessCheckIPQCService;
	
	@ModelAttribute
	public BusinessCheckIPQC get(@RequestParam(required=false) String id) {
		BusinessCheckIPQC entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessCheckIPQCService.get(id);
		}
		if (entity == null){
			entity = new BusinessCheckIPQC();
		}
		return entity;
	}
	
	/**
	 * IPQC检验列表页面
	 */
	@RequiresPermissions("business:check:ipqc:businessCheckIPQC:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessCheckIPQC businessCheckIPQC, Model model) {
		model.addAttribute("businessCheckIPQC", businessCheckIPQC);
		return "modules/business/check/ipqc/businessCheckIPQCList";
	}
	
		/**
	 * IPQC检验列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:check:ipqc:businessCheckIPQC:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessCheckIPQC businessCheckIPQC, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessCheckIPQC> page = businessCheckIPQCService.findPage(new Page<BusinessCheckIPQC>(request, response), businessCheckIPQC); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑IPQC检验表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:check:ipqc:businessCheckIPQC:view","business:check:ipqc:businessCheckIPQC:add","business:check:ipqc:businessCheckIPQC:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessCheckIPQC businessCheckIPQC, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessCheckIPQC", businessCheckIPQC);
		return "modules/business/check/ipqc/businessCheckIPQCForm";
	}

	/**
	 * 保存IPQC检验
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:check:ipqc:businessCheckIPQC:add","business:check:ipqc:businessCheckIPQC:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessCheckIPQC businessCheckIPQC, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessCheckIPQC);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessCheckIPQCService.save(businessCheckIPQC);//保存
		j.setSuccess(true);
		j.setMsg("保存IPQC检验成功");
		return j;
	}

	
	/**
	 * 批量删除IPQC检验
	 */
	@ResponseBody
	@RequiresPermissions("business:check:ipqc:businessCheckIPQC:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessCheckIPQCService.delete(businessCheckIPQCService.get(id));
		}
		j.setMsg("删除IPQC检验成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:check:ipqc:businessCheckIPQC:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessCheckIPQC businessCheckIPQC, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "IPQC检验"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessCheckIPQC> page = businessCheckIPQCService.findPage(new Page<BusinessCheckIPQC>(request, response, -1), businessCheckIPQC);
    		new ExportExcel("IPQC检验", BusinessCheckIPQC.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出IPQC检验记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:check:ipqc:businessCheckIPQC:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessCheckIPQC> list = ei.getDataList(BusinessCheckIPQC.class);
			for (BusinessCheckIPQC businessCheckIPQC : list){
				try{
					businessCheckIPQCService.save(businessCheckIPQC);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条IPQC检验记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条IPQC检验记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入IPQC检验失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入IPQC检验数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:check:ipqc:businessCheckIPQC:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "IPQC检验数据导入模板.xlsx";
    		List<BusinessCheckIPQC> list = Lists.newArrayList(); 
    		new ExportExcel("IPQC检验数据", BusinessCheckIPQC.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}