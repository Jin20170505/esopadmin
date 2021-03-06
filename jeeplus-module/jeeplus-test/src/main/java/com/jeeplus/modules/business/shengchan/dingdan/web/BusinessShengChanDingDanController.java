/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.business.product.archive.mapper.BusinessProductMapper;
import com.jeeplus.modules.business.ruku.product.entity.ProductTagBean;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengChanBeiLiaoApply;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengchanBeiliaoApplyMx;
import com.jeeplus.modules.business.shengchan.beiliao.apply.service.BusinessShengChanBeiLiaoApplyService;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanDingdanMx;
import com.jeeplus.modules.business.shengchan.bom.service.BusinessShengChanDingdanMxService;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.service.BusinessShengChanDingDanService;
import com.jeeplus.modules.u8data.invpostionsum.service.U8InvPostionSumService;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import com.jeeplus.modules.u8data.morder.service.U8MoallocateService;
import com.jeeplus.modules.u8data.morder.service.U8MorderService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ????????????Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/shengchan/dingdan/businessShengChanDingDan")
public class BusinessShengChanDingDanController extends BaseController {

	@Autowired
	private BusinessShengChanDingDanService businessShengChanDingDanService;
	
	@ModelAttribute
	public BusinessShengChanDingDan get(@RequestParam(required=false) String id) {
		BusinessShengChanDingDan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessShengChanDingDanService.get(id);
		}
		if (entity == null){
			entity = new BusinessShengChanDingDan();
		}
		return entity;
	}
	@Autowired
	private U8MorderService u8MorderService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(String start,String end,String code){
		AjaxJson json = new AjaxJson();
		try{
			U8Morder order = new U8Morder();
			order.setStatus("3");
			if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
				order.setStart(DateUtils.parseDate(start)).setEnd(DateUtils.parseDate(end));
			}
			order.setMoCode(code);
			List<U8Morder> data = u8MorderService.findList(order);
			if(data==null){
				json.setMsg("????????????(u8?????????)");
				json.setSuccess(true);
				return json;
			}
			List<String> schids =businessShengChanDingDanService.sychu8(data);
			schids.forEach(schid->sychU8bom(schid));
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}
	@Autowired
	private U8MoallocateService u8MoallocateService;
	@ResponseBody
	@RequestMapping("sychu8bom")
	public AjaxJson sychU8bom(String schid){
		AjaxJson json = new AjaxJson();
		try{
			U8Moallocate moallocate = new U8Moallocate();
			moallocate.setMoDId(schid);
			List<U8Moallocate> data = u8MoallocateService.findList(moallocate);
			if(data==null){
				json.setMsg("????????????(u8?????????)");
				json.setSuccess(true);
				return json;
			}
			businessShengChanDingdanMxService.sychU8bom(data);
		}catch (Exception e){
			e.printStackTrace();
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}

	/***
	 * ????????????
	 */
	@RequestMapping("gotobomsearch")
	public String gotobomsearch(String rid,Model model){
		model.addAttribute("schid",rid);
		return "modules/business/shengchan/dingdan/list/bomsearch";
	}
	@Autowired
	private U8InvPostionSumService u8InvPostionSumService;
	@ResponseBody
	@RequestMapping(value = "bomsearchdata")
	public Map<String, Object> bomsearchdata(String schid) {
		Page<BusinessShengChanBom> page = new Page<>();
		List<BusinessShengChanBom> list = businessShengChanDingdanMxService.findBomList(schid);
		if(list==null){
			list = new ArrayList<>(0);
		}
		list.forEach(d->{
			d.setDonenum(u8InvPostionSumService.getSumNumByCinvcode(d.getCinvcode()));
		});
		page.setList(list);
		page.setCount(list.size());
		page.setPageSize(-1);
		return getBootstrapData(page);
	}


	@RequestMapping("goToDateSelect")
	public String goToDateSelect(){
		return "modules/business/shengchan/dingdan/sync_daka";
	}
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanDingDan businessShengChanDingDan, Model model) {
		model.addAttribute("businessShengChanDingDan", businessShengChanDingDan);
		return "modules/business/shengchan/dingdan/businessShengChanDingDanList";
	}

	@RequestMapping(value = "approvedlist")
	public String approvedlist(BusinessShengChanDingDan businessShengChanDingDan,Model model) {
		model.addAttribute("businessShengChanDingDan", businessShengChanDingDan);
		return "modules/business/shengchan/dingdan/approved/businessShengChanDingDanList";
	}
	@RequestMapping(value = "closedlist")
	public String closedlist(BusinessShengChanDingDan businessShengChanDingDan,Model model) {
		model.addAttribute("businessShengChanDingDan", businessShengChanDingDan);
		return "modules/business/shengchan/dingdan/closed/businessShengChanDingDanList";
	}
	@RequestMapping("goToList")
	public String goToList(){
		return "modules/business/shengchan/dingdan/list/list";
	}

	@Autowired
	private BusinessShengChanDingdanMxService businessShengChanDingdanMxService;

	@Autowired
	private BusinessShengChanBeiLiaoApplyService applyService;
	@RequestMapping("goScBeiLiao")
	public String goScBeiLiao(String rid,Model model){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanService.getMxId(rid);
		Double num = mingXi.getNum();
		Double donenum = applyService.getDoneNum(rid);
		if(donenum==null){
			donenum = 0.0;
		}
		model.addAttribute("hmx",mingXi);
		model.addAttribute("donenum",donenum);
		model.addAttribute("doingnum",(num-donenum));
		return "modules/business/shengchan/dingdan/list/beiliao";
	}
	@RequestMapping("doScBeiLiao")
	@ResponseBody
	public AjaxJson doScBeiLiao(String rid,Double donum){
		AjaxJson json = new AjaxJson();
		BusinessShengChanDingDanMingXi main = businessShengChanDingDanService.getMxId(rid);
		BusinessShengChanBeiLiaoApply apply = new BusinessShengChanBeiLiaoApply();
		apply.setBatchno(main.getBatchno()).setStartdate(main.getStartdate()).setEnddate(main.getEnddate()).setSchid(rid);
		apply.setScid(main.getP().getId());apply.setSccode(main.getP().getCode());apply.setScline(main.getNo()+"");
		apply.setCinvcode(main.getCinv().getCode());apply.setCinvname(main.getCinvname());apply.setCinvstd(main.getStd());
		apply.setNum(donum);apply.setUnit(main.getUnit());apply.setDept(main.getDept());
		apply.setRemarks(main.getRemarks());
		double r = donum/main.getNum();
		List<BusinessShengChanBom> boms = businessShengChanDingdanMxService.findBomList(rid);
		if(boms!=null&&!boms.isEmpty()){
			boms.forEach(d->{
				BusinessShengchanBeiliaoApplyMx mx = new BusinessShengchanBeiliaoApplyMx();
				mx.setId("");mx.setDelFlag("0");
				mx.setNo(Integer.valueOf(d.getNo()));mx.setCinvcode(d.getCinvcode());mx.setCinvname(d.getCinvname());
				mx.setCinvstd(d.getCinvstd());mx.setNum(r*d.getNum());mx.setUnit(d.getUnitname());
				mx.setHw(d.getHw());
				apply.getBusinessShengchanBeiliaoApplyMxList().add(mx);
			});
		}
		applyService.save(apply);
		return json;
	}

	@RequestMapping("goToBeiLiaoPrint")
	public String goToBeiLiaoPrint(String rid,Model model){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanService.getMxId(rid);
		model.addAttribute("hmx",mingXi);
		List<BusinessShengChanBom> boms = businessShengChanDingdanMxService.findBomList(rid);
		model.addAttribute("boms",boms);
		return "modules/business/shengchan/dingdan/list/beiliaoprint";
	}
	@RequestMapping("/qr")
	public void getQrImage(String bomid, HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try{
			BusinessShengChanBom bean = businessShengChanDingdanMxService.getBom(bomid);
			ProductTagBean tagBean = new ProductTagBean();
			tagBean.setBatchno("").setCinvcode(bean.getCinvcode()).setCinvname(bean.getCinvname()).setCinvstd(bean.getCinvstd())
					.setNum(bean.getNum()+"").setUnit(bean.getUnitname()).setId(bean.getId())
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
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessShengChanDingDan businessShengChanDingDan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanDingDan> page = businessShengChanDingDanService.findPage(new Page<BusinessShengChanDingDan>(request, response), businessShengChanDingDan); 
		return getBootstrapData(page);
	}

	@ResponseBody
	@RequestMapping(value = "mxdata")
	public Map<String, Object> mxdata(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanDingDanMingXi> page = businessShengChanDingDanService.findPage(new Page<BusinessShengChanDingDanMingXi>(request, response), businessShengChanDingDanMingXi);
		return getBootstrapData(page);
	}
	@Autowired
	private BusinessProductMapper businessProductMapper;
	@ResponseBody
	@RequestMapping(value = "mxdatapaichan")
	public Map<String, Object> mxdatapaichan(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanDingDanMingXi> page = businessShengChanDingDanService.findShengChanDingDanMingXiByPaiChanPage(new Page<BusinessShengChanDingDanMingXi>(request, response), businessShengChanDingDanMingXi);
		List<BusinessShengChanDingDanMingXi> list = page.getList();
		if(list!=null){
			list.forEach(d->{
				d.setDonenum(u8InvPostionSumService.getSumNumByCinvcode(d.getIschaidan()));
				d.setStatus(businessProductMapper.getKeZhongOfCinvcode(d.getIschaidan()));
			});
		}
		return getBootstrapData(page);
	}
	@ResponseBody
	@RequestMapping(value = "mxdatascbb")
	public Map<String, Object> mxdatascbb(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanDingDanMingXi> page = businessShengChanDingDanService.findShengChanDingDanMingXiByShengChanBaoBiaoPage(new Page<BusinessShengChanDingDanMingXi>(request, response), businessShengChanDingDanMingXi);
		return getBootstrapData(page);
	}
	/**
	 * ????????????????????????????????????????????????
	 * params:
	 * 	mode: add, edit, view, ???????????????????????????
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessShengChanDingDan businessShengChanDingDan, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessShengChanDingDan", businessShengChanDingDan);
		return "modules/business/shengchan/dingdan/businessShengChanDingDanForm";
	}
	@ResponseBody
	@RequestMapping("chaidan")
	public AjaxJson chaidan(String rid,Double num){
		AjaxJson json = new AjaxJson();
		try {
			businessShengChanDingDanService.chaidan(rid,num);
			businessShengChanDingDanService.weichaCheck(rid);
			json.setMsg("????????????");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("doPlan")
	public AjaxJson doPlan(String rids){
		AjaxJson json = new AjaxJson();
		StringBuffer sb  = new StringBuffer();
		try {
			Arrays.asList(rids.split(",")).forEach(rid->{
				String rs =businessShengChanDingDanService.doPlan(rid);
				if(rs.length()>1){
					sb.append(rs).append("\n");
				}
			});
			if(sb.length()>1){
				json.setMsg("?????????????????????"+sb.toString());
				json.setSuccess(false);
				return json;
			}
			json.setMsg("????????????");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}


	/**
	 * ????????????
	 * @param rid ????????????ID
	 * @param gdnum ????????????
	 * @param nonum ????????????
	 * @param num ????????????
	 * @return
	 */
	@ResponseBody
	@RequestMapping("handlePlan")
	public AjaxJson handlePlan(String rid,Double gdnum,Double nonum,Double num){
		AjaxJson json = new AjaxJson();
		try {
			businessShengChanDingDanService.handlerPlan(rid,gdnum,nonum,num);
			if(nonum<=num){
				businessShengChanDingDanService.weichaCheck(rid);
			}
			json.setMsg("????????????");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}
	/**
	 * ??????????????????
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:shengchan:dingdan:businessShengChanDingDan:add","business:shengchan:dingdan:businessShengChanDingDan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanDingDan businessShengChanDingDan, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * ??????hibernate-validation????????????
		 */
		String errMsg = beanValidator(businessShengChanDingDan);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//???????????????????????????
		businessShengChanDingDanService.save(businessShengChanDingDan);//??????
		j.setSuccess(true);
		j.setMsg("????????????????????????");
		return j;
	}

	@ResponseBody
	@RequestMapping("shenhe")
	public AjaxJson shenhe(String ids){
		AjaxJson json = new AjaxJson();
		try {
			businessShengChanDingDanService.shenhe(ids);
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????,?????????"+e.getMessage());
		}
		return json;
	}
	@ResponseBody
	@RequestMapping("fanshen")
	public AjaxJson fanshen(String ids){
		AjaxJson json = new AjaxJson();
		try {
			businessShengChanDingDanService.fanshen(ids);
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????");
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("closeorder")
	public AjaxJson closeorder(String mids,String ids){
		AjaxJson json = new AjaxJson();
		try {
			if(StringUtils.isNotEmpty(mids)){
				Arrays.asList(mids.split(",")).forEach(mid->{
					businessShengChanDingDanService.closeMid(mid);
				});
			}else {
				businessShengChanDingDanService.closeorder(ids);
			}
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????");
		}
		return json;
	}
	// ??????
	@ResponseBody
	@RequestMapping("recover")
	public AjaxJson recover(String mids,String ids){
		AjaxJson json = new AjaxJson();
		try {
			if(StringUtils.isNotEmpty(mids)){
				Arrays.asList(mids.split(",")).forEach(mid->{
					businessShengChanDingDanService.recoverMid(mid);
				});
			}else {
				businessShengChanDingDanService.recover(ids);
			}
			json.setSuccess(true);
			json.setMsg("????????????");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("????????????");
		}
		return json;
	}
	/**
	 * ????????????????????????
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanDingDanService.delete(businessShengChanDingDanService.get(id));
		}
		j.setMsg("????????????????????????");
		return j;
	}
	
	/**
	 * ??????excel??????
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanDingDan businessShengChanDingDan, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "????????????"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanDingDan> page = businessShengChanDingDanService.findPage(new Page<BusinessShengChanDingDan>(request, response, -1), businessShengChanDingDan);
    		new ExportExcel("????????????", BusinessShengChanDingDan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("????????????????????????????????????????????????"+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessShengChanDingDan detail(String id) {
		return businessShengChanDingDanService.get(id);
	}
	

	/**
	 * ??????Excel??????

	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessShengChanDingDan> list = ei.getDataList(BusinessShengChanDingDan.class);
			for (BusinessShengChanDingDan businessShengChanDingDan : list){
				try{
					businessShengChanDingDanService.save(businessShengChanDingDan);
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
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "??????????????????????????????.xlsx";
    		List<BusinessShengChanDingDan> list = Lists.newArrayList(); 
    		new ExportExcel("??????????????????", BusinessShengChanDingDan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "??????????????????????????????????????????"+e.getMessage());
		}
		return j;
    }
    @RequestMapping("treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "?????????");
		map.put("text", "?????????");
		map.put("parent", "#");
		Map<String, Object> state = Maps.newHashMap();
		state.put("opened", true);
		map.put("state", state);
		mapList.add(map);
		Map<String, Object> map0 = Maps.newHashMap();
		map0.put("id", "?????????");
		map0.put("text","?????????");
		map0.put("parent", "#");
		Map<String, Object> state0 = Maps.newHashMap();
		state0.put("opened", true);
		map0.put("state", state0);
		mapList.add(map0);
		Map<String, Object> map1 = Maps.newHashMap();
		map1.put("id", "?????????");
		map1.put("text","?????????");
		map1.put("parent", "#");
		Map<String, Object> state1 = Maps.newHashMap();
		state1.put("opened", true);
		map1.put("state", state1);
		mapList.add(map1);
		return mapList;
	}
}