/**
 * 
 */
package com.jeeplus.modules.business.dispatch.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.u8data.dispatch.entity.U8Dispatch;
import com.jeeplus.modules.u8data.dispatch.serivce.U8DispatchService;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import com.jeeplus.modules.u8data.morder.service.U8MorderService;
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
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;
import com.jeeplus.modules.business.dispatch.service.BusinessDispatchService;

/**
 * 销售发货单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/dispatch/businessDispatch")
public class BusinessDispatchController extends BaseController {

	@Autowired
	private BusinessDispatchService businessDispatchService;
	
	@ModelAttribute
	public BusinessDispatch get(@RequestParam(required=false) String id) {
		BusinessDispatch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessDispatchService.get(id);
		}
		if (entity == null){
			entity = new BusinessDispatch();
		}
		return entity;
	}
	@Autowired
	private U8DispatchService u8DispatchService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(String start,String end){
		AjaxJson json = new AjaxJson();
		try{
			U8Dispatch dispatch = new U8Dispatch();
			if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
				dispatch.setStart(DateUtils.parseDate(start)).setEnd(DateUtils.parseDate(end));
			}
			List<U8Dispatch> data = u8DispatchService.findList(dispatch);
			if(data==null){
				json.setMsg("同步成功(u8数据空)");
				json.setSuccess(true);
				return json;
			}
			businessDispatchService.sychu8(data);
			json.setSuccess(true);
			json.setMsg("同步成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("同步失败,原因："+e.getMessage());
		}
		return json;
	}
	/**
	 * 销售发货单列表页面
	 */
	@RequiresPermissions("business:dispatch:businessDispatch:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessDispatch businessDispatch, Model model) {
		model.addAttribute("businessDispatch", businessDispatch);
		return "modules/business/dispatch/businessDispatchList";
	}
	@RequestMapping("goToDateSelect")
	public String goToDateSelect(){
		return "modules/business/dispatch/sync_daka";
	}
		/**
	 * 销售发货单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:dispatch:businessDispatch:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessDispatch businessDispatch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessDispatch> page = businessDispatchService.findPage(new Page<BusinessDispatch>(request, response), businessDispatch); 
		return getBootstrapData(page);
	}
	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		BusinessDispatch vouch = businessDispatchService.get(rid);
		model.addAttribute("bean",vouch);
		return "modules/business/dispatch/print";
	}
	@RequestMapping("/img/{rid}")
	public void getImage(@PathVariable("rid") String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessDispatch vouch = businessDispatchService.get(rid);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"xsfhcode\":\"").append(vouch.getCode()).append("\",\"date\":\"")
					.append(DateUtils.formatDate(vouch.getFahuodate(),"yyyy-MM-dd")).append("\",\"deptname\":\"").append(vouch.getDept().getName())
					.append("\",\"cname\":\"").append(vouch.getCustomer().getName()).append("\",\"xsfhid\":\"")
					.append(vouch.getId()).append("\"}");
			out = response.getOutputStream();
			QRCodeUtil.encode(sb.toString(),out);
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
		}
	}

	@RequestMapping("print")
	@ResponseBody
	public AjaxJson mainPrint(String rid){
		AjaxJson j = new AjaxJson();
		businessDispatchService.print(rid);
		j.setSuccess(true);
		return j;
	}
	/**
	 * 查看，增加，编辑销售发货单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:dispatch:businessDispatch:view","business:dispatch:businessDispatch:add","business:dispatch:businessDispatch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessDispatch businessDispatch, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessDispatch", businessDispatch);
		return "modules/business/dispatch/businessDispatchForm";
	}

	/**
	 * 保存销售发货单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:dispatch:businessDispatch:add","business:dispatch:businessDispatch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessDispatch businessDispatch, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessDispatch);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessDispatchService.save(businessDispatch);//保存
		j.setSuccess(true);
		j.setMsg("保存销售发货单成功");
		return j;
	}

	
	/**
	 * 批量删除销售发货单
	 */
	@ResponseBody
	@RequiresPermissions("business:dispatch:businessDispatch:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessDispatchService.delete(businessDispatchService.get(id));
		}
		j.setMsg("删除销售发货单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:dispatch:businessDispatch:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessDispatch businessDispatch, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售发货单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessDispatch> page = businessDispatchService.findPage(new Page<BusinessDispatch>(request, response, -1), businessDispatch);
    		new ExportExcel("销售发货单", BusinessDispatch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出销售发货单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessDispatch detail(String id) {
		return businessDispatchService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:dispatch:businessDispatch:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessDispatch> list = ei.getDataList(BusinessDispatch.class);
			for (BusinessDispatch businessDispatch : list){
				try{
					businessDispatchService.save(businessDispatch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条销售发货单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条销售发货单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入销售发货单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入销售发货单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:dispatch:businessDispatch:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销售发货单数据导入模板.xlsx";
    		List<BusinessDispatch> list = Lists.newArrayList(); 
    		new ExportExcel("销售发货单数据", BusinessDispatch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}