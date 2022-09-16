/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.web;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Maps;
import com.jeeplus.common.utils.QRCodeUtil;
import com.jeeplus.modules.sys.utils.FileKit;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
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
	@ResponseBody
	@RequestMapping("baogongchongzhi")
	public AjaxJson baogongchongzhi(String rid){
		AjaxJson json=new AjaxJson();
		try {
			businessBaoGongOrderService.baogongchongzhi(rid);
			json.setSuccess(true);
			json.setMsg("重置成功!");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("失败，原因："+e.getMessage());
		}
		return json;
	}
	@RequestMapping("editremarks")
	@ResponseBody
	public AjaxJson editremarks(String rid,String remarks){
		AjaxJson json = new AjaxJson();
		try{
			businessBaoGongOrderService.editremarks(rid, remarks);
			json.setSuccess(true);
			json.setMsg("修改成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("修改失败");
		}
		return json;
	}
	@RequestMapping("goToPrint")
	public String goToPrint(String rid,Model model){
		BusinessBaoGongOrder order = businessBaoGongOrderService.get(rid);
		model.addAttribute("order",order);
		return "modules/business/baogong/order/printbaogongdan";
	}

	@RequestMapping("goToPlPrint")
	public String goToPlPrint(String rids,Model model){
		List<BusinessBaoGongOrder> list = Lists.newArrayList();
		Arrays.asList(rids.split(",")).forEach(id->{
			list.add(businessBaoGongOrderService.get(id));
		});
		model.addAttribute("list",list);
		model.addAttribute("rids",rids);
		return "modules/business/baogong/order/plprintbaogongdan";
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
	@Autowired
	private U8MoallocateService u8MoallocateService;
	@Autowired
	private U8MorderService u8MorderService;
	/**
	 * 领料用量不足 处理
	 * @param rid 报工ID
	 * @return
	 */
	@RequestMapping("lingliaodealwith")
	@ResponseBody
	public AjaxJson lingliaodealwith(String rid){
		AjaxJson json = new AjaxJson();
		try{
			String schid = businessBaoGongOrderService.getSchidByOrderid(rid);
			List<U8Moallocate> moallocates = u8MoallocateService.findBomIdAndSyNum(schid);
			String rs = businessBaoGongOrderService.lingliaodealwith(rid,schid,moallocates);
			if(StringUtils.isNotEmpty(rs)){
				json.setSuccess(false);
				json.setMsg(rs);
				return json;
			}
			json.setMsg("操作成功，请重新尝试领料");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("处理失败，原因："+e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("dealwith")
	public AjaxJson dealwith(String rid){
		AjaxJson json = new AjaxJson();
		try{
			String schid = businessBaoGongOrderService.getSchidByOrderid(rid);
			String planid = businessBaoGongOrderService.getPlanidByOrderid(rid);
			List<U8Moallocate> moallocates = u8MoallocateService.findBomIdAndSyNum(schid);
			// U8 工单数量
			Double num = u8MorderService.getNewGdNumBySchid(schid);
			if(num==null){
				json.setSuccess(false);
				json.setMsg("未获取到U8对应工单到数量。");
				return json;
			}
			businessBaoGongOrderService.dealwith(rid,schid,planid,moallocates,num);
			json.setSuccess(true);
			json.setMsg("处理成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("处理失败，原因："+e.getMessage());
		}
		return json;
	}
	/**
	 * 报工单列表页面
	 */
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
		j.setMsg("成功");
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
	@RequestMapping("treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		map.put("id", "未打印");
		map.put("text", "未打印");
		map.put("parent", "#");
		Map<String, Object> state = Maps.newHashMap();
		state.put("opened", true);
		map.put("state", state);
		mapList.add(map);
		Map<String, Object> map1 = Maps.newHashMap();
		map1.put("id", "已打印");
		map1.put("text","已打印");
		map1.put("parent", "#");
		Map<String, Object> state1 = Maps.newHashMap();
		state1.put("opened", true);
		map1.put("state", state1);
		mapList.add(map1);
		return mapList;
	}
}