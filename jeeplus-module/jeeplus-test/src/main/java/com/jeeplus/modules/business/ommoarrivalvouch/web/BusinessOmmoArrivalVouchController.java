/**
 * 
 */
package com.jeeplus.modules.business.ommoarrivalvouch.web;

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
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalvouchMx;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
import com.jeeplus.modules.u8data.arrivalvouch.entity.U8ArrivalVouch;
import com.jeeplus.modules.u8data.arrivalvouch.service.U8ArrivalVouchService;
import com.jeeplus.modules.u8data.ommoarrivalvouch.entity.U8OmmoArrivalVouch;
import com.jeeplus.modules.u8data.ommoarrivalvouch.service.U8OmmoArrivalVouchService;
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
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalVouch;
import com.jeeplus.modules.business.ommoarrivalvouch.service.BusinessOmmoArrivalVouchService;

/**
 * ???????????????Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ommoarrivalvouch/businessOmmoArrivalVouch")
public class BusinessOmmoArrivalVouchController extends BaseController {

	@Autowired
	private BusinessOmmoArrivalVouchService businessOmmoArrivalVouchService;
	
	@ModelAttribute
	public BusinessOmmoArrivalVouch get(@RequestParam(required=false) String id) {
		BusinessOmmoArrivalVouch entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessOmmoArrivalVouchService.get(id);
		}
		if (entity == null){
			entity = new BusinessOmmoArrivalVouch();
		}
		return entity;
	}

	@RequestMapping("goToDateSelect")
	public String goToDateSelect(){
		return "modules/business/ommoarrivalvouch/sync_daka";
	}

	@Autowired
	private U8OmmoArrivalVouchService u8OmmoArrivalVouchService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(String start,String end){
		AjaxJson json = new AjaxJson();
		try{
			U8OmmoArrivalVouch vouch = new U8OmmoArrivalVouch();
			if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
				vouch.setStart(DateUtils.parseDate(start)).setEnd(DateUtils.parseDate(end));
			}
			List<U8OmmoArrivalVouch> data = u8OmmoArrivalVouchService.findList(vouch);
			if(data==null){
				json.setMsg("????????????(u8?????????)");
				json.setSuccess(true);
				return json;
			}
			businessOmmoArrivalVouchService.sychu8(data);
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}

	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		BusinessOmmoArrivalVouch vouch = businessOmmoArrivalVouchService.get(rid);
		model.addAttribute("bean",vouch);
		return "modules/business/ommoarrivalvouch/print";
	}

	@RequestMapping("/img/{rid}")
	public void getImage(@PathVariable("rid") String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessOmmoArrivalVouch vouch = businessOmmoArrivalVouchService.get(rid);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"wwcode\":\"").append(vouch.getCode()).append("\",\"arrivaldate\":\"")
					.append(DateUtils.formatDate(vouch.getDdate(),"yyyy-MM-dd")).append("\",\"deptname\":\"").append(vouch.getDept().getName())
					.append("\",\"vendorname\":\"").append(vouch.getVendor().getName()).append("\",\"wwid\":\"")
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
		BusinessOmmoArrivalvouchMx bean = businessOmmoArrivalVouchService.getMx(rids);
		if(bean.getMinnum()==null || bean.getMinnum()<=0){
			throw new RuntimeException("????????????????????????");
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
		return "modules/business/ommoarrivalvouch/list/print";
	}

	@RequestMapping("/qr")
	public void getQrImage(String rid,String num, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessOmmoArrivalvouchMx tagBean = businessOmmoArrivalVouchService.getMx(rid);
			String qr = "'cinvcode':'"+tagBean.getCinvcode()+"','cinvcodename':'"+tagBean.getCinvname()+"','batchno':'"+tagBean.getBatchno()+"','date':'"+tagBean.getScdate()+"','num':'"+num+"','unit':'"+tagBean.getUnit()+"'";
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
	 * ???????????????????????????
	 */
	@RequiresPermissions("business:ommoarrivalvouch:businessOmmoArrivalVouch:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessOmmoArrivalVouch businessOmmoArrivalVouch, Model model) {
		model.addAttribute("businessOmmoArrivalVouch", businessOmmoArrivalVouch);
		return "modules/business/ommoarrivalvouch/businessOmmoArrivalVouchList";
	}
	@RequestMapping(value = "mxlist")
	public String mxlist(BusinessOmmoArrivalVouch businessOmmoArrivalVouch, Model model) {
		model.addAttribute("businessOmmoArrivalVouch", businessOmmoArrivalVouch);
		return "modules/business/ommoarrivalvouch/list/list";
	}

	@ResponseBody
	@RequestMapping(value = "print")
	public AjaxJson print(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessOmmoArrivalVouchService.print(id);
		}
		j.setMsg("??????");
		return j;
	}

	@RequestMapping("mainPrint")
	@ResponseBody
	public AjaxJson mainPrint(String rid){
		AjaxJson j = new AjaxJson();
		businessOmmoArrivalVouchService.mainPrint(rid);
		j.setSuccess(true);
		return j;
	}
	@ResponseBody
	@RequestMapping(value = "mxdata")
	public Map<String, Object> mxdata(BusinessOmmoArrivalvouchMx businessOmmoArrivalvouchMx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessOmmoArrivalvouchMx> page = businessOmmoArrivalVouchService.findMxPage(new Page<BusinessOmmoArrivalvouchMx>(request, response), businessOmmoArrivalvouchMx);
		return getBootstrapData(page);
	}
		/**
	 * ???????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommoarrivalvouch:businessOmmoArrivalVouch:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessOmmoArrivalVouch businessOmmoArrivalVouch, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessOmmoArrivalVouch> page = businessOmmoArrivalVouchService.findPage(new Page<BusinessOmmoArrivalVouch>(request, response), businessOmmoArrivalVouch); 
		return getBootstrapData(page);
	}

	/**
	 * ???????????????????????????????????????????????????
	 * params:
	 * 	mode: add, edit, view, ???????????????????????????
	 */
	@RequiresPermissions(value={"business:ommoarrivalvouch:businessOmmoArrivalVouch:view","business:ommoarrivalvouch:businessOmmoArrivalVouch:add","business:ommoarrivalvouch:businessOmmoArrivalVouch:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessOmmoArrivalVouch businessOmmoArrivalVouch, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessOmmoArrivalVouch", businessOmmoArrivalVouch);
		return "modules/business/ommoarrivalvouch/businessOmmoArrivalVouchForm";
	}

	/**
	 * ?????????????????????
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ommoarrivalvouch:businessOmmoArrivalVouch:add","business:ommoarrivalvouch:businessOmmoArrivalVouch:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessOmmoArrivalVouch businessOmmoArrivalVouch, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * ??????hibernate-validation????????????
		 */
		String errMsg = beanValidator(businessOmmoArrivalVouch);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//???????????????????????????
		businessOmmoArrivalVouchService.save(businessOmmoArrivalVouch);//??????
		j.setSuccess(true);
		j.setMsg("???????????????????????????");
		return j;
	}

	
	/**
	 * ???????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommoarrivalvouch:businessOmmoArrivalVouch:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessOmmoArrivalVouchService.delete(businessOmmoArrivalVouchService.get(id));
		}
		j.setMsg("???????????????????????????");
		return j;
	}
	
	/**
	 * ??????excel??????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommoarrivalvouch:businessOmmoArrivalVouch:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessOmmoArrivalVouch businessOmmoArrivalVouch, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "???????????????"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessOmmoArrivalVouch> page = businessOmmoArrivalVouchService.findPage(new Page<BusinessOmmoArrivalVouch>(request, response, -1), businessOmmoArrivalVouch);
    		new ExportExcel("???????????????", BusinessOmmoArrivalVouch.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("???????????????????????????????????????????????????"+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessOmmoArrivalVouch detail(String id) {
		return businessOmmoArrivalVouchService.get(id);
	}
	

	/**
	 * ??????Excel??????

	 */
	@ResponseBody
	@RequiresPermissions("business:ommoarrivalvouch:businessOmmoArrivalVouch:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessOmmoArrivalVouch> list = ei.getDataList(BusinessOmmoArrivalVouch.class);
			for (BusinessOmmoArrivalVouch businessOmmoArrivalVouch : list){
				try{
					businessOmmoArrivalVouchService.save(businessOmmoArrivalVouch);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ???????????????????????????");
			}
			j.setMsg( "??????????????? "+successNum+" ????????????????????????"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("?????????????????????????????????????????????"+e.getMessage());
		}
		return j;
    }
	
	/**
	 * ???????????????????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommoarrivalvouch:businessOmmoArrivalVouch:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "?????????????????????????????????.xlsx";
    		List<BusinessOmmoArrivalVouch> list = Lists.newArrayList(); 
    		new ExportExcel("?????????????????????", BusinessOmmoArrivalVouch.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "??????????????????????????????????????????"+e.getMessage());
		}
		return j;
    }
	

}