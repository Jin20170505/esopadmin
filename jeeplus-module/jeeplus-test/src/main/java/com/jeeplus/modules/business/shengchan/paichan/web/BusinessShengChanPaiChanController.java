/**
 * 
 */
package com.jeeplus.modules.business.shengchan.paichan.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.u8data.invpostionsum.service.U8InvPostionSumService;
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
import com.jeeplus.modules.business.shengchan.paichan.entity.BusinessShengChanPaiChan;
import com.jeeplus.modules.business.shengchan.paichan.service.BusinessShengChanPaiChanService;

/**
 * 生产排产Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/shengchan/paichan/businessShengChanPaiChan")
public class BusinessShengChanPaiChanController extends BaseController {

	@Autowired
	private BusinessShengChanPaiChanService businessShengChanPaiChanService;
	@Autowired
	private U8InvPostionSumService u8InvPostionSumService;
	@ModelAttribute
	public BusinessShengChanPaiChan get(@RequestParam(required=false) String id) {
		BusinessShengChanPaiChan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessShengChanPaiChanService.get(id);
			if(entity.getBusinessShengChanPaiChaiMxList()!=null){
				entity.getBusinessShengChanPaiChaiMxList().forEach(d->{
					Double num = u8InvPostionSumService.getSumNumByCinvcode(d.getCinvcode());
					d.setRemarks(num+"");
				});
			}
		}
		if (entity == null){
			entity = new BusinessShengChanPaiChan();
		}
		return entity;
	}
	
	/**
	 * 生产排产列表页面
	 */
	@RequiresPermissions("business:shengchan:paichan:businessShengChanPaiChan:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanPaiChan businessShengChanPaiChan, Model model) {
		model.addAttribute("businessShengChanPaiChan", businessShengChanPaiChan);
		return "modules/business/shengchan/paichan/businessShengChanPaiChanList";
	}
	
		/**
	 * 生产排产列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:businessShengChanPaiChan:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessShengChanPaiChan businessShengChanPaiChan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanPaiChan> page = businessShengChanPaiChanService.findPage(new Page<BusinessShengChanPaiChan>(request, response), businessShengChanPaiChan); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑生产排产表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:shengchan:paichan:businessShengChanPaiChan:view","business:shengchan:paichan:businessShengChanPaiChan:add","business:shengchan:paichan:businessShengChanPaiChan:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessShengChanPaiChan businessShengChanPaiChan, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessShengChanPaiChan", businessShengChanPaiChan);
		return "modules/business/shengchan/paichan/businessShengChanPaiChanForm";
	}

	/**
	 * 保存生产排产
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:shengchan:paichan:businessShengChanPaiChan:add","business:shengchan:paichan:businessShengChanPaiChan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanPaiChan businessShengChanPaiChan, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessShengChanPaiChan);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessShengChanPaiChanService.save(businessShengChanPaiChan);//保存
		j.setSuccess(true);
		j.setMsg("保存生产排产成功");
		return j;
	}

	
	/**
	 * 批量删除生产排产
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:businessShengChanPaiChan:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanPaiChanService.delete(businessShengChanPaiChanService.get(id));
		}
		j.setMsg("删除生产排产成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:businessShengChanPaiChan:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanPaiChan businessShengChanPaiChan, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产排产"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanPaiChan> page = businessShengChanPaiChanService.findPage(new Page<BusinessShengChanPaiChan>(request, response, -1), businessShengChanPaiChan);
    		new ExportExcel("生产排产", BusinessShengChanPaiChan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出生产排产记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessShengChanPaiChan detail(String id) {
		return businessShengChanPaiChanService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:businessShengChanPaiChan:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessShengChanPaiChan> list = ei.getDataList(BusinessShengChanPaiChan.class);
			for (BusinessShengChanPaiChan businessShengChanPaiChan : list){
				try{
					businessShengChanPaiChanService.save(businessShengChanPaiChan);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条生产排产记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条生产排产记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入生产排产失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入生产排产数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:paichan:businessShengChanPaiChan:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产排产数据导入模板.xlsx";
    		List<BusinessShengChanPaiChan> list = Lists.newArrayList(); 
    		new ExportExcel("生产排产数据", BusinessShengChanPaiChan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}