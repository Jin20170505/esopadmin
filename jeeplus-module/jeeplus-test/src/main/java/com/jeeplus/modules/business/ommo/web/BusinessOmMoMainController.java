/**
 * 
 */
package com.jeeplus.modules.business.ommo.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;
import com.jeeplus.modules.business.ommo.bom.mapper.BussinessOmMoYongItemMapper;
import com.jeeplus.modules.business.ommo.bom.service.BussinessOmMoDetailOnlyService;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoDetail;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import com.jeeplus.modules.u8data.morder.service.U8MoallocateService;
import com.jeeplus.modules.u8data.morder.service.U8MorderService;
import com.jeeplus.modules.u8data.ommo.entity.U8MOMaterials;
import com.jeeplus.modules.u8data.ommo.entity.U8OmMoMain;
import com.jeeplus.modules.u8data.ommo.service.U8MOMaterialsService;
import com.jeeplus.modules.u8data.ommo.service.U8MoMoMainService;
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
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoMain;
import com.jeeplus.modules.business.ommo.service.BusinessOmMoMainService;

/**
 * ????????????Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ommo/businessOmMoMain")
public class BusinessOmMoMainController extends BaseController {

	@Autowired
	private BusinessOmMoMainService businessOmMoMainService;
	
	@ModelAttribute
	public BusinessOmMoMain get(@RequestParam(required=false) String id) {
		BusinessOmMoMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessOmMoMainService.get(id);
		}
		if (entity == null){
			entity = new BusinessOmMoMain();
		}
		return entity;
	}
	@RequestMapping("/qr")
	public void getQrImage(String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessOmMoDetail detail = businessOmMoMainService.getDetail(rid);
			String qr = "{\"wwcode\":\""+detail.getMo().getCode()+"\",\"lineno\":\""+detail.getNo()+"\",\"wwid\":\""+detail.getMo().getId()+"\",\"wwhid\":\""+detail.getId()+"\"}";
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
	@RequestMapping("chaidan")
	public AjaxJson chaidan(String rid,Double num){
		AjaxJson json = new AjaxJson();
		try{
			businessOmMoMainService.chaidan(rid, num);
			json.setMsg("????????????");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("?????????????????????:"+e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("handlerchaidan")
	public AjaxJson handlerchaidan(String rid,Double gdnum,Double nonum,Double num){
		AjaxJson json = new AjaxJson();
		try{
			businessOmMoMainService.handlerchaidan(rid, gdnum,nonum,num);
			json.setMsg("????????????");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("?????????????????????:"+e.getMessage());
		}
		return json;
	}
	/**
	 * ????????????????????????
	 */
	@RequiresPermissions("business:ommo:businessOmMoMain:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessOmMoMain businessOmMoMain, Model model) {
		model.addAttribute("businessOmMoMain", businessOmMoMain);
		return "modules/business/ommo/businessOmMoMainList";
	}
	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		BussinessOmMoDetailOnly omMoDetailOnly = bussinessOmMoDetailOnlyService.get(rid);
		model.addAttribute("order",omMoDetailOnly);
		return "modules/business/ommo/list/print";
	}
	@RequestMapping("/img/{rid}")
	public void getImage(@PathVariable("rid") String rid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			String qrcode = "";
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
	@ResponseBody
	@RequestMapping(value = "print")
	public AjaxJson print(String rid) {
		AjaxJson j = new AjaxJson();
		businessOmMoMainService.print(rid);
		j.setSuccess(true);
		j.setMsg("??????");
		return j;
	}
		/**
	 * ????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:businessOmMoMain:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessOmMoMain businessOmMoMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessOmMoMain> page = businessOmMoMainService.findPage(new Page<BusinessOmMoMain>(request, response), businessOmMoMain); 
		return getBootstrapData(page);
	}

	@RequestMapping(value = "gotolist")
	public String gotolist( Model model) {
		return "modules/business/ommo/list/list";
	}
	@ResponseBody
	@RequestMapping(value = "mxdata")
	public Map<String, Object> mxdata(BusinessOmMoDetail businessOmMoDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessOmMoDetail> page = businessOmMoMainService.findMxPage(new Page<BusinessOmMoDetail>(request, response), businessOmMoDetail);
		return getBootstrapData(page);
	}

	/**
	 * ????????????????????????????????????????????????
	 * params:
	 * 	mode: add, edit, view, ???????????????????????????
	 */
	@RequiresPermissions(value={"business:ommo:businessOmMoMain:view","business:ommo:businessOmMoMain:add","business:ommo:businessOmMoMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessOmMoMain businessOmMoMain, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessOmMoMain", businessOmMoMain);
		return "modules/business/ommo/businessOmMoMainForm";
	}

	/**
	 * ??????????????????
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ommo:businessOmMoMain:add","business:ommo:businessOmMoMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessOmMoMain businessOmMoMain, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * ??????hibernate-validation????????????
		 */
		String errMsg = beanValidator(businessOmMoMain);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//???????????????????????????
		businessOmMoMainService.save(businessOmMoMain);//??????
		j.setSuccess(true);
		j.setMsg("????????????????????????");
		return j;
	}

	@RequestMapping("goToDateSelect")
	public String goToDateSelect(){
		return "modules/business/ommo/sync_daka";
	}
	@Autowired
	private U8MoMoMainService u8MoMoMainService;
	@Autowired
	private U8MOMaterialsService u8MOMaterialsService;
	@Autowired
	private BussinessOmMoDetailOnlyService bussinessOmMoDetailOnlyService;

	@Autowired
	private BussinessOmMoYongItemMapper bussinessOmMoYongItemMapper;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(String start,String end,String code){
		AjaxJson json = new AjaxJson();
		try{
			U8OmMoMain main = new U8OmMoMain();
			if(StringUtils.isNotEmpty(code)){
				main.setcCode(code);
			}
			if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
				main.setStart(DateUtils.parseDate(start)).setEnd(DateUtils.parseDate(end));
			}
			List<U8OmMoMain> data = u8MoMoMainService.findList(main);
			if(data==null||data.isEmpty()){
				json.setMsg("????????????(u8?????????)");
				json.setSuccess(true);
				return json;
			}
			List<String> whids =businessOmMoMainService.sychu8(data);
			whids.forEach(whid ->{
				bussinessOmMoYongItemMapper.deleteByOmHid(whid);
			});
			List<U8MOMaterials> bomdata = u8MOMaterialsService.findByWid(whids);
			bussinessOmMoDetailOnlyService.sychU8(bomdata);
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}


	
	/**
	 * ????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:businessOmMoMain:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessOmMoMainService.delete(businessOmMoMainService.get(id));
		}
		j.setMsg("????????????????????????");
		return j;
	}
	
	/**
	 * ??????excel??????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:businessOmMoMain:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessOmMoMain businessOmMoMain, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "????????????"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessOmMoMain> page = businessOmMoMainService.findPage(new Page<BusinessOmMoMain>(request, response, -1), businessOmMoMain);
    		new ExportExcel("????????????", BusinessOmMoMain.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("????????????????????????????????????????????????"+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessOmMoMain detail(String id) {
		return businessOmMoMainService.get(id);
	}
	

	/**
	 * ??????Excel??????

	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:businessOmMoMain:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessOmMoMain> list = ei.getDataList(BusinessOmMoMain.class);
			for (BusinessOmMoMain businessOmMoMain : list){
				try{
					businessOmMoMainService.save(businessOmMoMain);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "????????? "+failureNum+" ????????????????????????");
			}
			j.setMsg( "??????????????? "+successNum+" ?????????????????????"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("??????????????????????????????????????????"+e.getMessage());
		}
		return j;
    }
	
	/**
	 * ????????????????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:ommo:businessOmMoMain:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "??????????????????????????????.xlsx";
    		List<BusinessOmMoMain> list = Lists.newArrayList(); 
    		new ExportExcel("??????????????????", BusinessOmMoMain.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "??????????????????????????????????????????"+e.getMessage());
		}
		return j;
    }
	

}