/**
 * 
 */
package com.jeeplus.modules.business.ruku.caigou.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaiGou;
import com.jeeplus.modules.business.ruku.caigou.service.BusinessRukuCaiGouService;

/**
 * 采购入库Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/ruku/caigou/businessRukuCaiGou")
public class BusinessRukuCaiGouController extends BaseController {

	@Autowired
	private BusinessRukuCaiGouService businessRukuCaiGouService;
	
	@ModelAttribute
	public BusinessRukuCaiGou get(@RequestParam(required=false) String id) {
		BusinessRukuCaiGou entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessRukuCaiGouService.get(id);
		}
		if (entity == null){
			entity = new BusinessRukuCaiGou();
		}
		return entity;
	}
	@RequestMapping("u8in")
	@ResponseBody
	public AjaxJson u8ruku(String rids,String codes){
		AjaxJson json = new AjaxJson();
		List<String> ids = Arrays.asList(rids.split(","));
		String[] codearr = codes.split(",");
		StringBuilder sb = new StringBuilder();
		int i =0;
		for (String id:ids){
			try{
				businessRukuCaiGouService.u8in(id);
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
	 * 采购入库列表页面
	 */
	@RequiresPermissions("business:ruku:caigou:businessRukuCaiGou:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessRukuCaiGou businessRukuCaiGou, Model model) {
		model.addAttribute("businessRukuCaiGou", businessRukuCaiGou);
		return "modules/business/ruku/caigou/businessRukuCaiGouList";
	}
	
		/**
	 * 采购入库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:caigou:businessRukuCaiGou:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessRukuCaiGou businessRukuCaiGou, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessRukuCaiGou> page = businessRukuCaiGouService.findPage(new Page<BusinessRukuCaiGou>(request, response), businessRukuCaiGou); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑采购入库表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:ruku:caigou:businessRukuCaiGou:view","business:ruku:caigou:businessRukuCaiGou:add","business:ruku:caigou:businessRukuCaiGou:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessRukuCaiGou businessRukuCaiGou, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessRukuCaiGou", businessRukuCaiGou);
		return "modules/business/ruku/caigou/businessRukuCaiGouForm";
	}

	/**
	 * 保存采购入库
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:ruku:caigou:businessRukuCaiGou:add","business:ruku:caigou:businessRukuCaiGou:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessRukuCaiGou businessRukuCaiGou, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessRukuCaiGou);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessRukuCaiGouService.save(businessRukuCaiGou);//保存
		j.setSuccess(true);
		j.setMsg("保存采购入库成功");
		return j;
	}

	
	/**
	 * 批量删除采购入库
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:caigou:businessRukuCaiGou:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessRukuCaiGouService.delete(businessRukuCaiGouService.get(id));
		}
		j.setMsg("删除采购入库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:caigou:businessRukuCaiGou:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessRukuCaiGou businessRukuCaiGou, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购入库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessRukuCaiGou> page = businessRukuCaiGouService.findPage(new Page<BusinessRukuCaiGou>(request, response, -1), businessRukuCaiGou);
    		new ExportExcel("采购入库", BusinessRukuCaiGou.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出采购入库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessRukuCaiGou detail(String id) {
		return businessRukuCaiGouService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:caigou:businessRukuCaiGou:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessRukuCaiGou> list = ei.getDataList(BusinessRukuCaiGou.class);
			for (BusinessRukuCaiGou businessRukuCaiGou : list){
				try{
					businessRukuCaiGouService.save(businessRukuCaiGou);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条采购入库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条采购入库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入采购入库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入采购入库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:ruku:caigou:businessRukuCaiGou:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "采购入库数据导入模板.xlsx";
    		List<BusinessRukuCaiGou> list = Lists.newArrayList(); 
    		new ExportExcel("采购入库数据", BusinessRukuCaiGou.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}