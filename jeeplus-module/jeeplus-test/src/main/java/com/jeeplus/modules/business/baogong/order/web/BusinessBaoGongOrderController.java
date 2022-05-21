/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.sys.utils.FileKit;
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
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;

/**
 * 报工单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/baogong/order/businessBaoGongOrder")
public class BusinessBaoGongOrderController extends BaseController {

	@Autowired
	private BusinessBaoGongOrderService businessBaoGongOrderService;
	
	@ModelAttribute
	public BusinessBaoGongOrder get(@RequestParam(required=false) String id) {
		BusinessBaoGongOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessBaoGongOrderService.get(id);
		}
		if (entity == null){
			entity = new BusinessBaoGongOrder();
		}
		return entity;
	}

	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		BusinessBaoGongOrder order = businessBaoGongOrderService.get(rid);
		model.addAttribute("order",order);
		return "modules/business/baogong/order/printbaogongdan";
	}
	@RequestMapping("/img/{rid}")
	public void getImage(@PathVariable("rid") String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			String qrcode = businessBaoGongOrderService.getQrCode(rid);
			out = response.getOutputStream();
			QRCodeUtil.encode(qrcode,out);
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
		}
	}
	/**
	 * 报工单列表页面
	 */
	@RequiresPermissions("business:baogong:order:businessBaoGongOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessBaoGongOrder businessBaoGongOrder, Model model) {
		model.addAttribute("businessBaoGongOrder", businessBaoGongOrder);
		return "modules/business/baogong/order/businessBaoGongOrderList";
	}
	
		/**
	 * 报工单列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessBaoGongOrder businessBaoGongOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessBaoGongOrder> page = businessBaoGongOrderService.findPage(new Page<BusinessBaoGongOrder>(request, response), businessBaoGongOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑报工单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:baogong:order:businessBaoGongOrder:view","business:baogong:order:businessBaoGongOrder:add","business:baogong:order:businessBaoGongOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessBaoGongOrder businessBaoGongOrder, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessBaoGongOrder", businessBaoGongOrder);
		return "modules/business/baogong/order/businessBaoGongOrderForm";
	}

	/**
	 * 保存报工单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:baogong:order:businessBaoGongOrder:add","business:baogong:order:businessBaoGongOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessBaoGongOrder businessBaoGongOrder, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessBaoGongOrder);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessBaoGongOrderService.save(businessBaoGongOrder);//保存
		j.setSuccess(true);
		j.setMsg("保存报工单成功");
		return j;
	}

	
	/**
	 * 批量删除报工单
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:order:businessBaoGongOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessBaoGongOrderService.delete(businessBaoGongOrderService.get(id));
		}
		j.setMsg("删除报工单成功");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "print")
	public AjaxJson print(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessBaoGongOrderService.print(id);
		}
		j.setMsg("删除报工单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:order:businessBaoGongOrder:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessBaoGongOrder businessBaoGongOrder, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报工单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessBaoGongOrder> page = businessBaoGongOrderService.findPage(new Page<BusinessBaoGongOrder>(request, response, -1), businessBaoGongOrder);
    		new ExportExcel("报工单", BusinessBaoGongOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出报工单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessBaoGongOrder detail(String id) {
		return businessBaoGongOrderService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:order:businessBaoGongOrder:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessBaoGongOrder> list = ei.getDataList(BusinessBaoGongOrder.class);
			for (BusinessBaoGongOrder businessBaoGongOrder : list){
				try{
					businessBaoGongOrderService.save(businessBaoGongOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条报工单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条报工单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入报工单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入报工单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:baogong:order:businessBaoGongOrder:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "报工单数据导入模板.xlsx";
    		List<BusinessBaoGongOrder> list = Lists.newArrayList(); 
    		new ExportExcel("报工单数据", BusinessBaoGongOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}