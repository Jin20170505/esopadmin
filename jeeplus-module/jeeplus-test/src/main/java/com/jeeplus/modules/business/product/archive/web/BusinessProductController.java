/**
 * 
 */
package com.jeeplus.modules.business.product.archive.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.business.product.archive.service.BusinessProductService;

/**
 * 产品档案Controller
 * @author Jin
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
	
	/**
	 * 产品列表页面
	 */
	@RequiresPermissions("business:product:archive:businessProduct:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessProduct businessProduct, Model model) {
		model.addAttribute("businessProduct", businessProduct);
		return "modules/business/product/archive/businessProductList";
	}
	
		/**
	 * 产品列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:product:archive:businessProduct:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessProduct businessProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessProduct> page = businessProductService.findPage(new Page<BusinessProduct>(request, response), businessProduct); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑产品表单页面
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
	 * 保存产品
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
		j.setMsg("保存产品成功");
		return j;
	}

	
	/**
	 * 批量删除产品
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
		j.setMsg("删除产品成功");
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
            String fileName = "产品"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessProduct> page = businessProductService.findPage(new Page<BusinessProduct>(request, response, -1), businessProduct);
    		new ExportExcel("产品", BusinessProduct.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出产品记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessProduct detail(String id) {
		return businessProductService.get(id);
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
				failureMsg.insert(0, "，失败 "+failureNum+" 条产品记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条产品记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入产品失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入产品数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:product:archive:businessProduct:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产品数据导入模板.xlsx";
    		List<BusinessProduct> list = Lists.newArrayList(); 
    		new ExportExcel("产品数据", BusinessProduct.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}