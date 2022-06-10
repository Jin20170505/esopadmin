/**
 * 
 */
package com.jeeplus.modules.business.jihuadingdan.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;
import com.jeeplus.modules.business.jihuadingdan.service.BusinessJiHuaGongDanService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 计划工单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/jihuadingdan/businessJiHuaGongDan")
public class BusinessJiHuaGongDanController extends BaseController {

	@Autowired
	private BusinessJiHuaGongDanService businessJiHuaGongDanService;
	
	@ModelAttribute
	public BusinessJiHuaGongDan get(@RequestParam(required=false) String mid) {
		BusinessJiHuaGongDan entity = null;
		if (StringUtils.isNotBlank(mid)){
			entity = businessJiHuaGongDanService.get(mid);
		}
		if (entity == null){
			entity = new BusinessJiHuaGongDan();
		}
		return entity;
	}
	
	/**
	 * 计划工单列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BusinessJiHuaGongDan businessJiHuaGongDan, Model model) {
		model.addAttribute("businessJiHuaGongDan", businessJiHuaGongDan);
		return "modules/business/jihuadingdan/businessJiHuaGongDanList";
	}

	@RequestMapping(value = "xiafalist")
	public String xialist(BusinessJiHuaGongDan businessJiHuaGongDan, Model model) {
		model.addAttribute("businessJiHuaGongDan", businessJiHuaGongDan);
		return "modules/business/jihuadingdan/xiafa/businessJiHuaGongDanList";
	}

	@ResponseBody
	@RequestMapping("getSyGdNum")
	public AjaxJson getSyGdNum(String scddlineid){
		AjaxJson json = new AjaxJson();
		json.setSuccess(true);
		json.put("num",businessJiHuaGongDanService.getGdNum(scddlineid));
		return json;
	}

	@ResponseBody
	@RequestMapping("xiafa")
	public AjaxJson xiafa(String ids){
		AjaxJson json = new AjaxJson();
		try {
			List<String> idList = Arrays.asList(ids.split(","));
			StringBuilder sb = new StringBuilder();
			for (String id:idList){
				try{
					businessJiHuaGongDanService.xiafa(id);
				}catch (Exception e){
					sb.append(e.getMessage());
				}
			}
			if(sb.length()>0){
				sb.insert(0,"失败原因：");
			}
			json.setMsg("操作成功,"+sb.toString());
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败,原因:"+e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("chehui")
	public AjaxJson chehui(String ids){
		AjaxJson json = new AjaxJson();
		try {
			List<String> idList = Arrays.asList(ids.split(","));
			StringBuilder sb = new StringBuilder();
			for (String id:idList){
				try{
					businessJiHuaGongDanService.chehui(id);
				}catch (Exception e){
					sb.append(e.getMessage());
				}
			}
			if(sb.length()>0){
				sb.insert(0,"失败原因：");
			}
			json.setMsg("操作成功,"+sb.toString());
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败,原因:"+e.getMessage());
		}
		return json;
	}
	// 生成报工单
	@ResponseBody
	@RequestMapping("shengchengbaogongdan")
	public AjaxJson shengchengbaogongdan(String rids){
		AjaxJson json = new AjaxJson();
		try {
			List<String> idArray = Arrays.asList(rids.split(","));
			for(String id:idArray){
				businessJiHuaGongDanService.shengchengbaogongdan(id);
			}
			json.setSuccess(true);
			json.setMsg("操作成功.");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败,原因:"+e.getMessage());
		}
		return json;
	}

		/**
	 * 计划工单列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessJiHuaGongDan businessJiHuaGongDan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessJiHuaGongDan> page = businessJiHuaGongDanService.findPage(new Page<BusinessJiHuaGongDan>(request, response), businessJiHuaGongDan); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑计划工单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessJiHuaGongDan businessJiHuaGongDan, Model model) {
		model.addAttribute("mode", mode);
		if(StringUtils.isNotEmpty(businessJiHuaGongDan.getId())){
			businessJiHuaGongDan = businessJiHuaGongDanService.get(businessJiHuaGongDan.getId());
		}
		model.addAttribute("businessJiHuaGongDan", businessJiHuaGongDan);
		return "modules/business/jihuadingdan/businessJiHuaGongDanForm";
	}

	/**
	 * 保存计划工单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:jihuadingdan:businessJiHuaGongDan:add","business:jihuadingdan:businessJiHuaGongDan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessJiHuaGongDan businessJiHuaGongDan, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessJiHuaGongDan);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		if(StringUtils.isNotEmpty(businessJiHuaGongDan.getId())){
			businessJiHuaGongDanService.deleteMx(businessJiHuaGongDan.getId());
		}
		//新增或编辑表单保存
		businessJiHuaGongDanService.save(businessJiHuaGongDan);//保存
		j.setSuccess(true);
		j.setMsg("保存计划工单成功");
		return j;
	}

	
	/**
	 * 批量删除计划工单
	 */
	@ResponseBody
	@RequiresPermissions("business:jihuadingdan:businessJiHuaGongDan:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessJiHuaGongDanService.delete(businessJiHuaGongDanService.get(id));
		}
		j.setMsg("删除计划工单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:jihuadingdan:businessJiHuaGongDan:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessJiHuaGongDan businessJiHuaGongDan, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "计划工单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessJiHuaGongDan> page = businessJiHuaGongDanService.findPage(new Page<BusinessJiHuaGongDan>(request, response, -1), businessJiHuaGongDan);
    		new ExportExcel("计划工单", BusinessJiHuaGongDan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出计划工单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessJiHuaGongDan detail(String id) {
		return businessJiHuaGongDanService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:jihuadingdan:businessJiHuaGongDan:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessJiHuaGongDan> list = ei.getDataList(BusinessJiHuaGongDan.class);
			for (BusinessJiHuaGongDan businessJiHuaGongDan : list){
				try{
					businessJiHuaGongDanService.save(businessJiHuaGongDan);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条计划工单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条计划工单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入计划工单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入计划工单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:jihuadingdan:businessJiHuaGongDan:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "计划工单数据导入模板.xlsx";
    		List<BusinessJiHuaGongDan> list = Lists.newArrayList(); 
    		new ExportExcel("计划工单数据", BusinessJiHuaGongDan.class, 1).setDataList(list).write(response, fileName).dispose();
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
		map.put("id", "未生成");
		map.put("text", "未生成");
		map.put("parent", "#");
		Map<String, Object> state = Maps.newHashMap();
		state.put("opened", true);
		map.put("state", state);
		mapList.add(map);
		Map<String, Object> map1 = Maps.newHashMap();
		map1.put("id", "已生成");
		map1.put("text","已生成");
		map1.put("parent", "#");
		Map<String, Object> state1 = Maps.newHashMap();
		state1.put("opened", true);
		map1.put("state", state1);
		mapList.add(map1);
		return mapList;
	}

}