/**
 * 
 */
package com.jeeplus.modules.business.shengchan.beiliao.apply.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengchanBeiliaoApplyMx;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
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
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengChanBeiLiaoApply;
import com.jeeplus.modules.business.shengchan.beiliao.apply.service.BusinessShengChanBeiLiaoApplyService;

/**
 * ????????????Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApply")
public class BusinessShengChanBeiLiaoApplyController extends BaseController {

	@Autowired
	private BusinessShengChanBeiLiaoApplyService businessShengChanBeiLiaoApplyService;
	
	@ModelAttribute
	public BusinessShengChanBeiLiaoApply get(@RequestParam(required=false) String id) {
		BusinessShengChanBeiLiaoApply entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessShengChanBeiLiaoApplyService.get(id);
		}
		if (entity == null){
			entity = new BusinessShengChanBeiLiaoApply();
		}
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "print")
	public AjaxJson print(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanBeiLiaoApplyService.print(id);
		}
		j.setMsg("??????");
		return j;
	}
	@Autowired
	private U8InvPostionSumService u8InvPostionSumService;
	@RequestMapping("goToBeiLiaoPrint")
	public String goToBeiLiaoPrint(String rid,Model model){
		BusinessShengChanBeiLiaoApply mingXi = businessShengChanBeiLiaoApplyService.get(rid);
		model.addAttribute("hmx",mingXi);
		List<BusinessShengchanBeiliaoApplyMx> boms = mingXi.getBusinessShengchanBeiliaoApplyMxList();
		if(boms!=null && !boms.isEmpty()){
			boms.forEach(d->{
				d.setXcnum(u8InvPostionSumService.getSumNumByCinvcode(d.getCinvcode()));
			});
		}
		model.addAttribute("boms",boms);
		return "modules/business/shengchan/beiliao/apply/beiliaoprint";
	}
	@RequestMapping("/qr")
	public void getQrImage(String bomid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessShengchanBeiliaoApplyMx bean = businessShengChanBeiLiaoApplyService.getBom(bomid);
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno("").setCinvcode(bean.getCinvcode()).setCinvname(bean.getCinvname()).setCinvstd(bean.getCinvstd())
					.setNum(bean.getNum()+"").setUnit(bean.getUnit()).setId(bean.getId())
					.setDate("");
			String qr = "'cinvcode':'"+tagBean.getCinvcode()+"','cinvcodename':'"+tagBean.getSimpleCinvname()+"','batchno':'"+tagBean.getBatchno()+"','date':'"+tagBean.getDate()+"','num':'"+tagBean.getNum()+"','unit':'"+tagBean.getUnit()+"'";
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
	 * ????????????????????????
	 */
	@RequiresPermissions("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply, Model model) {
		model.addAttribute("businessShengChanBeiLiaoApply", businessShengChanBeiLiaoApply);
		return "modules/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApplyList";
	}
	
		/**
	 * ????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanBeiLiaoApply> page = businessShengChanBeiLiaoApplyService.findPage(new Page<BusinessShengChanBeiLiaoApply>(request, response), businessShengChanBeiLiaoApply);
		return getBootstrapData(page);
	}

	/**
	 * ????????????????????????????????????????????????
	 * params:
	 * 	mode: add, edit, view, ???????????????????????????
	 */
	@RequiresPermissions(value={"business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:view","business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:add","business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessShengChanBeiLiaoApply", businessShengChanBeiLiaoApply);
		return "modules/business/shengchan/beiliao/apply/businessShengChanBeiLiaoApplyForm";
	}

	/**
	 * ??????????????????
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:add","business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * ??????hibernate-validation????????????
		 */
		String errMsg = beanValidator(businessShengChanBeiLiaoApply);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//???????????????????????????
		businessShengChanBeiLiaoApplyService.save(businessShengChanBeiLiaoApply);//??????
		j.setSuccess(true);
		j.setMsg("????????????????????????");
		return j;
	}

	
	/**
	 * ????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanBeiLiaoApplyService.delete(businessShengChanBeiLiaoApplyService.get(id));
		}
		j.setMsg("????????????????????????");
		return j;
	}
	
	/**
	 * ??????excel??????
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "????????????"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanBeiLiaoApply> page = businessShengChanBeiLiaoApplyService.findPage(new Page<BusinessShengChanBeiLiaoApply>(request, response, -1), businessShengChanBeiLiaoApply);
    		new ExportExcel("????????????", BusinessShengChanBeiLiaoApply.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("????????????????????????????????????????????????"+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessShengChanBeiLiaoApply detail(String id) {
		BusinessShengChanBeiLiaoApply apply = businessShengChanBeiLiaoApplyService.get(id);
		apply.getBusinessShengchanBeiliaoApplyMxList().forEach(d->{
			d.setXcnum(u8InvPostionSumService.getSumNumByCinvcode(d.getCinvcode()));
		});
		return apply;
	}
	

	/**
	 * ??????Excel??????

	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessShengChanBeiLiaoApply> list = ei.getDataList(BusinessShengChanBeiLiaoApply.class);
			for (BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply : list){
				try{
					businessShengChanBeiLiaoApplyService.save(businessShengChanBeiLiaoApply);
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
	@RequiresPermissions("business:shengchan:beiliao:apply:businessShengChanBeiLiaoApply:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "??????????????????????????????.xlsx";
    		List<BusinessShengChanBeiLiaoApply> list = Lists.newArrayList(); 
    		new ExportExcel("??????????????????", BusinessShengChanBeiLiaoApply.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "??????????????????????????????????????????"+e.getMessage());
		}
		return j;
    }
	

}