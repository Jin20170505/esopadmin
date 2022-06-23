/**
 * 
 */
package com.jeeplus.modules.business.chuku.dispatch.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
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
import com.jeeplus.modules.business.chuku.dispatch.entity.BusinessChukuDispatch;
import com.jeeplus.modules.business.chuku.dispatch.service.BusinessChukuDispatchService;

/**
 * 销售出库单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/chuku/dispatch/businessChukuDispatch")
public class BusinessChukuDispatchController extends BaseController {

	@Autowired
	private BusinessChukuDispatchService businessChukuDispatchService;
	
	@ModelAttribute
	public BusinessChukuDispatch get(@RequestParam(required=false) String id) {
		BusinessChukuDispatch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessChukuDispatchService.get(id);
		}
		if (entity == null){
			entity = new BusinessChukuDispatch();
		}
		return entity;
	}
	@RequestMapping("u8in")
	@ResponseBody
	public AjaxJson u8ruku(String rids,String codes){
		AjaxJson json = new AjaxJson();
		List<String> ids = Arrays.asList(rids.split(","));
		String[] codearr = codes.split(",");
		StringBuilder sb = new StringBuilder();
		int i =0;
		for (String id:ids){
			try{
				businessChukuDispatchService.u8in(id);
			}catch (Exception e){
				e.printStackTrace();
				sb.append(codearr[i]).append(":").append(e.getMessage()).append("\n");
			}
			i++;
		}
		if(sb.length()>0){
			json.setMsg("同步未成功单号及原因："+sb.toString());
			json.setSuccess(false);
		}else {
			json.setMsg("同步成功");
			json.setSuccess(true);
		}
		return json;
	}
	/**
	 * 销售出库单列表页面
	 */
	@RequiresPermissions("business:chuku:dispatch:businessChukuDispatch:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessChukuDispatch businessChukuDispatch, Model model) {
		model.addAttribute("businessChukuDispatch", businessChukuDispatch);
		return "modules/business/chuku/dispatch/businessChukuDispatchList";
	}
	
		/**
	 * 销售出库单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:dispatch:businessChukuDispatch:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessChukuDispatch businessChukuDispatch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessChukuDispatch> page = businessChukuDispatchService.findPage(new Page<BusinessChukuDispatch>(request, response), businessChukuDispatch); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑销售出库单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:chuku:dispatch:businessChukuDispatch:view","business:chuku:dispatch:businessChukuDispatch:add","business:chuku:dispatch:businessChukuDispatch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessChukuDispatch businessChukuDispatch, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessChukuDispatch", businessChukuDispatch);
		return "modules/business/chuku/dispatch/businessChukuDispatchForm";
	}

	/**
	 * 保存销售出库单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:chuku:dispatch:businessChukuDispatch:add","business:chuku:dispatch:businessChukuDispatch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessChukuDispatch businessChukuDispatch, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessChukuDispatch);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessChukuDispatchService.save(businessChukuDispatch);//保存
		j.setSuccess(true);
		j.setMsg("保存销售出库单成功");
		return j;
	}

	
	/**
	 * 批量删除销售出库单
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:dispatch:businessChukuDispatch:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessChukuDispatchService.delete(businessChukuDispatchService.get(id));
		}
		j.setMsg("删除销售出库单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:dispatch:businessChukuDispatch:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessChukuDispatch businessChukuDispatch, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售出库单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessChukuDispatch> page = businessChukuDispatchService.findPage(new Page<BusinessChukuDispatch>(request, response, -1), businessChukuDispatch);
    		new ExportExcel("销售出库单", BusinessChukuDispatch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出销售出库单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessChukuDispatch detail(String id) {
		return businessChukuDispatchService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:dispatch:businessChukuDispatch:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessChukuDispatch> list = ei.getDataList(BusinessChukuDispatch.class);
			for (BusinessChukuDispatch businessChukuDispatch : list){
				try{
					businessChukuDispatchService.save(businessChukuDispatch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条销售出库单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条销售出库单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入销售出库单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入销售出库单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:dispatch:businessChukuDispatch:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售出库单数据导入模板.xlsx";
    		List<BusinessChukuDispatch> list = Lists.newArrayList(); 
    		new ExportExcel("销售出库单数据", BusinessChukuDispatch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}