/**
 * 
 */
package com.jeeplus.modules.business.ruku.product.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
import com.jeeplus.modules.u8data.customercinvcode.service.U8CusInvContraposeService;
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
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;
import com.jeeplus.modules.business.ruku.product.service.BusinessRuKuProductService;

/**
 * 产成品入库Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ruku/product/businessRuKuProduct")
public class BusinessRuKuProductController extends BaseController {

	@Autowired
	private BusinessRuKuProductService businessRuKuProductService;
	
	@ModelAttribute
	public BusinessRuKuProduct get(@RequestParam(required=false) String id) {
		BusinessRuKuProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessRuKuProductService.get(id);
		}
		if (entity == null){
			entity = new BusinessRuKuProduct();
		}
		return entity;
	}


	@RequestMapping("goToRuKuBuChong")
	public String goToRuKuBuChong(Model model){
		return "modules/business/ruku/product/rukubuchong";
	}

	@RequestMapping("buchong")
	@ResponseBody
	public AjaxJson buchong(String bgid,String batchno,Double rukunum,String ckid,String hw,String remarks){
		AjaxJson json = new AjaxJson();
		try{
			businessRuKuProductService.buchong(bgid,batchno,rukunum,ckid,hw,remarks);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("补录失败。失败原因："+e.getMessage());
		}
		return json;
	}

	@RequestMapping("goToPrintcustomer")
	public String goToPrintcustomer(String rid,Model model){
		BusinessRuKuProduct bean = businessRuKuProductService.get(rid);
		if(bean.getBusinessRuKuProductMxList()==null || bean.getBusinessRuKuProductMxList().isEmpty()){
			throw new RuntimeException("入库明细缺失，无法打印。");
		}
		BusinessRuKuProductMx mx = bean.getBusinessRuKuProductMxList().get(0);
		model.addAttribute("rid",rid);
		model.addAttribute("cinvcode",mx.getCinvcode());
		return "modules/business/ruku/product/printcustomer";
	}
	@Autowired
	private U8CusInvContraposeService u8CusInvContraposeService;
	@RequestMapping("goToTagPrint")
	public String goToTagPrint(String rid,String customercinvcode,Integer num,Integer hdnum,Model model){
		BusinessRuKuProduct bean = businessRuKuProductService.get(rid);
		if(num==null){
			num=1;
		}
		if(bean.getBusinessRuKuProductMxList()==null || bean.getBusinessRuKuProductMxList().isEmpty()){
			throw new RuntimeException("入库明细缺失，无法打印。");
		}
		BusinessRuKuProductMx mx = bean.getBusinessRuKuProductMxList().get(0);
		List<ProductTagBean> tagBeans = Lists.newArrayList();
		double gdnum = bean.getNum();
		String customercinvname = "";
		if(StringUtils.isNotEmpty(customercinvcode)){
			customercinvname = u8CusInvContraposeService.getCusCinvName(customercinvcode);
		}
		if(hdnum!=null){
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno(bean.getBatchno()).setCinvstd(mx.getCinvstd())
					.setNum(hdnum+"").setUnit(mx.getUnit()).setId(bean.getId())
					.setDate(DateUtils.getDate("YYYY-MM-dd"));
			if(StringUtils.isNotEmpty(customercinvcode)){
				tagBean.setCinvcode(customercinvcode).setCinvname(customercinvname);
			}else {
				tagBean.setCinvcode(mx.getCinvcode()).setCinvname(mx.getCinvname());
			}
			tagBeans.add(tagBean);
			model.addAttribute("beans", tagBeans);
			return "modules/business/ruku/product/tagprint";
		}

		while (gdnum>num){
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno(bean.getBatchno()).setCinvstd(mx.getCinvstd())
					.setNum(num+"").setUnit(mx.getUnit()).setId(bean.getId())
					.setDate(DateUtils.getDate("YYYY-MM-dd"));
			if(StringUtils.isNotEmpty(customercinvcode)){
				tagBean.setCinvcode(customercinvcode).setCinvname(customercinvname);
			}else {
				tagBean.setCinvcode(mx.getCinvcode()).setCinvname(mx.getCinvname());
			}
			tagBeans.add(tagBean);
			gdnum = gdnum - num;
		}
		if(gdnum>0.0001){
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno(bean.getBatchno()).setCinvstd(mx.getCinvstd())
					.setNum(gdnum+"").setUnit(mx.getUnit()).setId(bean.getId())
					.setDate(DateUtils.getDate("YYYY-MM-dd"));
			if(StringUtils.isNotEmpty(customercinvcode)){
				tagBean.setCinvcode(customercinvcode).setCinvname(customercinvname);
			}else {
				tagBean.setCinvcode(mx.getCinvcode()).setCinvname(mx.getCinvname());
			}
			tagBeans.add(tagBean);
		}

		model.addAttribute("beans", tagBeans);
		return "modules/business/ruku/product/tagprint";
	}

	@RequestMapping("/qr")
	public void getQrImage(String rid,String num,String customercinvcode, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		String customercinvname ="";
		if(StringUtils.isNotEmpty(customercinvcode)){
			customercinvname = u8CusInvContraposeService.getCusCinvName(customercinvcode);
		}
		try{
			BusinessRuKuProduct bean = businessRuKuProductService.get(rid);
			BusinessRuKuProductMx mx = bean.getBusinessRuKuProductMxList().get(0);
			ProductTagBean tagBean = new ProductTagBean();
			if(StringUtils.isNotEmpty(customercinvcode)){
				tagBean.setCinvcode(customercinvcode).setCinvname(customercinvname);
			}else {
				tagBean.setCinvcode(mx.getCinvcode()).setCinvname(mx.getCinvname());
			}
			tagBean.setBatchno(bean.getBatchno()).setCinvstd(mx.getCinvstd())
					.setNum(num+"").setUnit(mx.getUnit()).setId(bean.getId())
					.setDate(DateUtils.getDate("YYYY-MM-dd"));
			String qr = "'cinvcode':'"+tagBean.getCinvcode()+"','cinvcodename':'"+tagBean.getCinvname()+"','batchno':'"+tagBean.getBatchno()+"','date':'"+tagBean.getDate()+"','num':'"+tagBean.getNum()+"','unit':'"+tagBean.getUnit()+"'";
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
	/**
	 * 产成品入库列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BusinessRuKuProduct businessRuKuProduct, Model model) {
		model.addAttribute("businessRuKuProduct", businessRuKuProduct);
		return "modules/business/ruku/product/businessRuKuProductList";
	}
	
		/**
	 * 产成品入库列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessRuKuProduct businessRuKuProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessRuKuProduct> page = businessRuKuProductService.findPage(new Page<BusinessRuKuProduct>(request, response), businessRuKuProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑产成品入库表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:ruku:product:businessRuKuProduct:view","business:ruku:product:businessRuKuProduct:add","business:ruku:product:businessRuKuProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessRuKuProduct businessRuKuProduct, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessRuKuProduct", businessRuKuProduct);
		return "modules/business/ruku/product/businessRuKuProductForm";
	}

	/**
	 * 保存产成品入库
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ruku:product:businessRuKuProduct:add","business:ruku:product:businessRuKuProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessRuKuProduct businessRuKuProduct, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessRuKuProduct);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessRuKuProductService.save(businessRuKuProduct);//保存
		j.setSuccess(true);
		j.setMsg("保存产成品入库成功");
		return j;
	}

	
	/**
	 * 批量删除产成品入库
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:product:businessRuKuProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessRuKuProductService.delete(businessRuKuProductService.get(id));
		}
		j.setMsg("删除产成品入库成功");
		return j;
	}

	@RequestMapping("u8ruku")
	@ResponseBody
	public AjaxJson u8ruku(String rids,String codes){
		AjaxJson json = new AjaxJson();
		List<String> ids = Arrays.asList(rids.split(","));
		String[] codearr = codes.split(",");
		StringBuilder sb = new StringBuilder();
		int i =0;
		for (String id:ids){
			try{
				businessRuKuProductService.u8ruku(id);
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
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:product:businessRuKuProduct:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessRuKuProduct businessRuKuProduct, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产成品入库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessRuKuProduct> page = businessRuKuProductService.findPage(new Page<BusinessRuKuProduct>(request, response, -1), businessRuKuProduct);
    		new ExportExcel("产成品入库", BusinessRuKuProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出产成品入库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessRuKuProduct detail(String id) {
		return businessRuKuProductService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:product:businessRuKuProduct:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessRuKuProduct> list = ei.getDataList(BusinessRuKuProduct.class);
			for (BusinessRuKuProduct businessRuKuProduct : list){
				try{
					businessRuKuProductService.save(businessRuKuProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产成品入库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条产成品入库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入产成品入库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入产成品入库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:product:businessRuKuProduct:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产成品入库数据导入模板.xlsx";
    		List<BusinessRuKuProduct> list = Lists.newArrayList(); 
    		new ExportExcel("产成品入库数据", BusinessRuKuProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}