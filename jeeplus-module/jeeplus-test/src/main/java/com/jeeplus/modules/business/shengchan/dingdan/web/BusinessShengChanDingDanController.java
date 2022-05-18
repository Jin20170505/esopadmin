/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.bom.service.BusinessShengChanDingdanMxService;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import com.jeeplus.modules.u8data.morder.service.U8MoallocateService;
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
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.business.shengchan.dingdan.service.BusinessShengChanDingDanService;

/**
 * 生产订单Controller
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
	public AjaxJson sychU8(String start,String end){
		AjaxJson json = new AjaxJson();
		try{
			U8Morder order = new U8Morder();
			order.setStatus("3");
			if(StringUtils.isNotEmpty(start)&&StringUtils.isNotEmpty(end)){
				order.setStart(DateUtils.parseDate(start)).setEnd(DateUtils.parseDate(end));
			}
			List<U8Morder> data = u8MorderService.findList(order);
			if(data==null){
				json.setMsg("同步成功(u8数据空)");
				json.setSuccess(true);
				return json;
			}
			List<String> schids =businessShengChanDingDanService.sychu8(data);
			schids.forEach(schid->sychU8bom(schid));
			json.setSuccess(true);
			json.setMsg("同步成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("同步失败,原因："+e.getMessage());
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
				json.setMsg("同步成功(u8数据空)");
				json.setSuccess(true);
				return json;
			}
			businessShengChanDingdanMxService.deleteBom(schid);
			businessShengChanDingdanMxService.sychU8bom(data);
		}catch (Exception e){
			e.printStackTrace();
			json.setMsg("同步失败,原因："+e.getMessage());
		}
		return json;
	}

	@RequestMapping("goToDateSelect")
	public String goToDateSelect(){
		return "modules/business/shengchan/dingdan/sync_daka";
	}
	/**
	 * 生产订单列表页面
	 */
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanDingDan businessShengChanDingDan, Model model) {
		model.addAttribute("businessShengChanDingDan", businessShengChanDingDan);
		return "modules/business/shengchan/dingdan/businessShengChanDingDanList";
	}
	@RequestMapping("goToList")
	public String goToList(){
		return "modules/business/shengchan/dingdan/list/list";
	}

	@Autowired
	private BusinessShengChanDingdanMxService businessShengChanDingdanMxService;

	@RequestMapping("goToBeiLiaoPrint")
	public String goToBeiLiaoPrint(String rid,Model model){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanService.getMxId(rid);
		model.addAttribute("hmx",mingXi);
		List<BusinessShengChanBom> boms = businessShengChanDingdanMxService.findBomList(rid);
		model.addAttribute("boms",boms);
		return "modules/business/shengchan/dingdan/list/beiliaoprint";
	}

		/**
	 * 生产订单列表数据
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

	/**
	 * 查看，增加，编辑生产订单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:shengchan:dingdan:businessShengChanDingDan:view","business:shengchan:dingdan:businessShengChanDingDan:add","business:shengchan:dingdan:businessShengChanDingDan:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessShengChanDingDan businessShengChanDingDan, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessShengChanDingDan", businessShengChanDingDan);
		return "modules/business/shengchan/dingdan/businessShengChanDingDanForm";
	}
	@ResponseBody
	@RequestMapping("chaidan")
	public AjaxJson chaidan(String rid,int num){
		AjaxJson json = new AjaxJson();
		try {
			businessShengChanDingDanService.chaidan(rid,num);
			json.setMsg("生成成功");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("生成失败,原因："+e.getMessage());
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
				json.setMsg("生成失败的有："+sb.toString());
				json.setSuccess(false);
				return json;
			}
			json.setMsg("生成成功");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("生成失败,原因："+e.getMessage());
		}
		return json;
	}

	/**
	 * 保存生产订单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:shengchan:dingdan:businessShengChanDingDan:add","business:shengchan:dingdan:businessShengChanDingDan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanDingDan businessShengChanDingDan, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessShengChanDingDan);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessShengChanDingDanService.save(businessShengChanDingDan);//保存
		j.setSuccess(true);
		j.setMsg("保存生产订单成功");
		return j;
	}

	@ResponseBody
	@RequestMapping("shenhe")
	public AjaxJson shenhe(String ids){
		AjaxJson json = new AjaxJson();
		try {
			businessShengChanDingDanService.shenhe(ids);
			json.setSuccess(true);
			json.setMsg("操作成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败");
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
			json.setMsg("操作成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败");
		}
		return json;
	}
	/**
	 * 批量删除生产订单
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
		j.setMsg("删除生产订单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanDingDan businessShengChanDingDan, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产订单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanDingDan> page = businessShengChanDingDanService.findPage(new Page<BusinessShengChanDingDan>(request, response, -1), businessShengChanDingDan);
    		new ExportExcel("生产订单", BusinessShengChanDingDan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出生产订单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessShengChanDingDan detail(String id) {
		return businessShengChanDingDanService.get(id);
	}
	

	/**
	 * 导入Excel数据

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
				failureMsg.insert(0, "，失败 "+failureNum+" 条生产订单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条生产订单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入生产订单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入生产订单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:dingdan:businessShengChanDingDan:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产订单数据导入模板.xlsx";
    		List<BusinessShengChanDingDan> list = Lists.newArrayList(); 
    		new ExportExcel("生产订单数据", BusinessShengChanDingDan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}