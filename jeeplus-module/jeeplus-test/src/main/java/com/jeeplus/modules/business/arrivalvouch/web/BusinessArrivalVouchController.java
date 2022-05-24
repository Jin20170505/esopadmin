/**
 * 
 */
package com.jeeplus.modules.business.arrivalvouch.web;

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
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
import com.jeeplus.modules.u8data.arrivalvouch.entity.U8ArrivalVouch;
import com.jeeplus.modules.u8data.arrivalvouch.service.U8ArrivalVouchService;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
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
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.service.BusinessArrivalVouchService;

/**
 * 采购到货单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/arrivalvouch/businessArrivalVouch")
public class BusinessArrivalVouchController extends BaseController {

	@Autowired
	private BusinessArrivalVouchService businessArrivalVouchService;
	
	@ModelAttribute
	public BusinessArrivalVouch get(@RequestParam(required=false) String id) {
		BusinessArrivalVouch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessArrivalVouchService.get(id);
		}
		if (entity == null){
			entity = new BusinessArrivalVouch();
		}
		return entity;
	}
	@RequestMapping("goToDateSelect")
	public String goToDateSelect(){
		return "modules/business/arrivalvouch/sync_daka";
	}

	@Autowired
	private U8ArrivalVouchService u8ArrivalVouchService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(String start,String end){
		AjaxJson json = new AjaxJson();
		try{
			U8ArrivalVouch vouch = new U8ArrivalVouch();
			if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
				vouch.setStart(DateUtils.parseDate(start)).setEnd(DateUtils.parseDate(end));
			}
			List<U8ArrivalVouch> data = u8ArrivalVouchService.findList(vouch);
			if(data==null){
				json.setMsg("同步成功(u8数据空)");
				json.setSuccess(true);
				return json;
			}
			businessArrivalVouchService.sychu8(data);
			json.setSuccess(true);
			json.setMsg("同步成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("同步失败,原因："+e.getMessage());
		}
		return json;
	}

	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		BusinessArrivalVouch vouch = businessArrivalVouchService.get(rid);
		model.addAttribute("bean",vouch);
		return "modules/business/arrivalvouch/print";
	}
	@RequestMapping("/img/{rid}")
	public void getImage(@PathVariable("rid") String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessArrivalVouch vouch = businessArrivalVouchService.get(rid);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"cgcode\":\"").append(vouch.getCode()).append("\",\"arrivaldate\":\"")
					.append(DateUtils.formatDate(vouch.getArriveDate(),"yyyy-MM-dd")).append("\",\"deptname\":\"").append(vouch.getDept().getName())
					.append("\",\"vendorname\":\"").append(vouch.getVendor().getName()).append("\",\"cgid\":\"")
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
	@RequestMapping("goToTagPrint")
	public String goToTagPrint(String rids,Model model){
		model.addAttribute("rids",rids);
		BusinessArrivalVouchMx bean = businessArrivalVouchService.getMx(rids);
		if(bean.getMinnum()==null || bean.getMinnum()<=0){
			throw new RuntimeException("最小包装数未填写");
		}
		List<ProductTagBean> tagBeans = Lists.newArrayList();
		double gdnum = bean.getNum();
		double num  = bean.getMinnum();
		while (gdnum>num){
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno(bean.getBatchno()).setCinvcode(bean.getCinvcode()).setCinvname(bean.getCinvname()).setCinvstd(bean.getCinvstd())
					.setNum(num+"").setUnit(bean.getUnit())
					.setDate(bean.getScdate());
			tagBean.setQrcode(bean.getId());
			tagBeans.add(tagBean);
			gdnum = gdnum - num;
		}
		if(gdnum>0.0001){
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno(bean.getBatchno()).setCinvcode(bean.getCinvcode()).setCinvname(bean.getCinvname()).setCinvstd(bean.getCinvstd())
					.setNum(gdnum+"").setUnit(bean.getUnit())
					.setDate(bean.getScdate());
			tagBean.setQrcode(bean.getId());
			tagBeans.add(tagBean);
		}
		model.addAttribute("beans", tagBeans);
		return "modules/business/arrivalvouch/list/print";
	}

	@RequestMapping("/qr")
	public void getQrImage(String rid,String num, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessArrivalVouchMx tagBean = businessArrivalVouchService.getMx(rid);
			String qr = "'cinvcode':'"+tagBean.getCinvcode()+"','cinvname':'"+tagBean.getCinvname()+"','cinvstd':'"+tagBean.getCinvstd()+"','batchno':'"+tagBean.getBatchno()+"','date':'"+tagBean.getScdate()+"','num':'"+num+"','unit':'"+tagBean.getUnit()+"','cghid':'"+tagBean.getId()+"','cgid':'"+tagBean.getP().getId()+"'";
			out = response.getOutputStream();
			QRCodeUtil.encode(qr,out);
			out.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(out!=null){
				out.close();
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "print")
	public AjaxJson print(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessArrivalVouchService.print(id);
		}
		j.setMsg("成功");
		return j;
	}
	/**
	 * 采购到货列表页面
	 */
	@RequiresPermissions("business:arrivalvouch:businessArrivalVouch:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessArrivalVouch businessArrivalVouch, Model model) {
		model.addAttribute("businessArrivalVouch", businessArrivalVouch);
		return "modules/business/arrivalvouch/businessArrivalVouchList";
	}

	@RequestMapping(value = "mxlist")
	public String mxlist(BusinessArrivalVouch businessArrivalVouch, Model model) {
		model.addAttribute("businessArrivalVouch", businessArrivalVouch);
		return "modules/business/arrivalvouch/list/list";
	}
	@ResponseBody
	@RequestMapping(value = "mxdata")
	public Map<String, Object> mxdata(BusinessArrivalVouchMx businessArrivalVouchMx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessArrivalVouchMx> page = businessArrivalVouchService.findMxPage(new Page<BusinessArrivalVouchMx>(request, response), businessArrivalVouchMx);
		return getBootstrapData(page);
	}
		/**
	 * 采购到货列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:arrivalvouch:businessArrivalVouch:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessArrivalVouch businessArrivalVouch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessArrivalVouch> page = businessArrivalVouchService.findPage(new Page<BusinessArrivalVouch>(request, response), businessArrivalVouch); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑采购到货表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:arrivalvouch:businessArrivalVouch:view","business:arrivalvouch:businessArrivalVouch:add","business:arrivalvouch:businessArrivalVouch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessArrivalVouch businessArrivalVouch, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessArrivalVouch", businessArrivalVouch);
		return "modules/business/arrivalvouch/businessArrivalVouchForm";
	}

	/**
	 * 保存采购到货
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:arrivalvouch:businessArrivalVouch:add","business:arrivalvouch:businessArrivalVouch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessArrivalVouch businessArrivalVouch, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessArrivalVouch);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessArrivalVouchService.save(businessArrivalVouch);//保存
		j.setSuccess(true);
		j.setMsg("保存采购到货成功");
		return j;
	}

	
	/**
	 * 批量删除采购到货
	 */
	@ResponseBody
	@RequiresPermissions("business:arrivalvouch:businessArrivalVouch:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessArrivalVouchService.delete(businessArrivalVouchService.get(id));
		}
		j.setMsg("删除采购到货成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:arrivalvouch:businessArrivalVouch:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessArrivalVouch businessArrivalVouch, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购到货"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessArrivalVouch> page = businessArrivalVouchService.findPage(new Page<BusinessArrivalVouch>(request, response, -1), businessArrivalVouch);
    		new ExportExcel("采购到货", BusinessArrivalVouch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出采购到货记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessArrivalVouch detail(String id) {
		return businessArrivalVouchService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:arrivalvouch:businessArrivalVouch:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessArrivalVouch> list = ei.getDataList(BusinessArrivalVouch.class);
			for (BusinessArrivalVouch businessArrivalVouch : list){
				try{
					businessArrivalVouchService.save(businessArrivalVouch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条采购到货记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条采购到货记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入采购到货失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入采购到货数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:arrivalvouch:businessArrivalVouch:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购到货数据导入模板.xlsx";
    		List<BusinessArrivalVouch> list = Lists.newArrayList(); 
    		new ExportExcel("采购到货数据", BusinessArrivalVouch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}