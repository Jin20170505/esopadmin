/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.modules.base.erp.updatetime.entity.BaseU8UpdateTime;
import com.jeeplus.modules.base.erp.updatetime.entity.U8SynchType;
import com.jeeplus.modules.base.erp.updatetime.service.BaseU8UpdateTimeService;
import com.jeeplus.modules.u8data.inventory.entity.U8Inventory;
import com.jeeplus.modules.u8data.inventory.service.U8InventoryService;
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
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.business.product.archive.service.BusinessProductService;

/**
 * 存货档案Controller
 * @author Jin
 * @version 2022-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/business/product/archive/businessProduct")
public class BusinessProductController extends BaseController {

	@Autowired
	private BusinessProductService businessProductService;
	
	@ModelAttribute
	public BusinessProduct get(@RequestParam(required=false) String id) {
		BusinessProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessProductService.get(id);
		}
		if (entity == null){
			entity = new BusinessProduct();
		}
		return entity;
	}
	@Autowired
	private U8InventoryService u8InventoryService;
	@Autowired
	private BaseU8UpdateTimeService baseU8UpdateTimeService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(){
		AjaxJson json = new AjaxJson();
		try{
			BaseU8UpdateTime time = baseU8UpdateTimeService.getByCode(U8SynchType.CUNHUO.getCode());
			Date now = new Date();
			if(time==null){
				time = new BaseU8UpdateTime();
				time.setCode(U8SynchType.CUNHUO.getCode());
				time.setName(U8SynchType.CUNHUO.getName());
				time.setLastTime(DateUtil.addDays(now,-30));
			}
			U8Inventory u8Inventory = new U8Inventory();
			u8Inventory.setNowTime(now).setModifyTime(time.getLastTime());
			List<U8Inventory> data = u8InventoryService.findList(u8Inventory);
			if(data==null){
				time.setLastTime(now);
				baseU8UpdateTimeService.save(time);
				json.setMsg("同步成功(ERP数据空)");
				json.setSuccess(true);
				return json;
			}
			businessProductService.sychu8(data);
			time.setLastTime(now);
			baseU8UpdateTimeService.save(time);
			json.setMsg("同步成功");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("同步失败,原因："+e.getMessage());
		}
		return json;
	}
	/**
	 * 存货档案列表页面
	 */
	@RequiresPermissions("business:product:archive:businessProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessProduct businessProduct, Model model) {
		model.addAttribute("businessProduct", businessProduct);
		return "modules/business/product/archive/businessProductList";
	}
	
		/**
	 * 存货档案列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessProduct businessProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessProduct> page = businessProductService.findPage(new Page<BusinessProduct>(request, response), businessProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑存货档案表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:product:archive:businessProduct:view","business:product:archive:businessProduct:add","business:product:archive:businessProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessProduct businessProduct, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessProduct", businessProduct);
		return "modules/business/product/archive/businessProductForm";
	}

	/**
	 * 保存存货档案
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:product:archive:businessProduct:add","business:product:archive:businessProduct:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessProduct businessProduct, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessProduct);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessProductService.save(businessProduct);//保存
		j.setSuccess(true);
		j.setMsg("保存存货档案成功");
		return j;
	}

	
	/**
	 * 批量删除存货档案
	 */
	@ResponseBody
	@RequiresPermissions("business:product:archive:businessProduct:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessProductService.delete(businessProductService.get(id));
		}
		j.setMsg("删除存货档案成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:product:archive:businessProduct:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessProduct businessProduct, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "存货档案"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessProduct> page = businessProductService.findPage(new Page<BusinessProduct>(request, response, -1), businessProduct);
    		new ExportExcel("存货档案", BusinessProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出存货档案记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:product:archive:businessProduct:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessProduct> list = ei.getDataList(BusinessProduct.class);
			for (BusinessProduct businessProduct : list){
				try{
					businessProductService.save(businessProduct);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条存货档案记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条存货档案记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入存货档案失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入存货档案数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:product:archive:businessProduct:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "存货档案数据导入模板.xlsx";
    		List<BusinessProduct> list = Lists.newArrayList(); 
    		new ExportExcel("存货档案数据", BusinessProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}