/**
 *
 */
package com.jeeplus.modules.base.vendor.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.base.erp.updatetime.entity.BaseU8UpdateTime;
import com.jeeplus.modules.base.erp.updatetime.entity.U8SynchType;
import com.jeeplus.modules.base.erp.updatetime.service.BaseU8UpdateTimeService;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.base.vendor.service.BaseVendorService;
import com.jeeplus.modules.u8data.vendor.entity.U8Vendor;
import com.jeeplus.modules.u8data.vendor.service.U8VendorService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 供应商档案Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/vendor/baseVendor")
public class BaseVendorController extends BaseController {

	@Autowired
	private BaseVendorService baseVendorService;
	
	@ModelAttribute
	public BaseVendor get(@RequestParam(required=false) String id) {
		BaseVendor entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseVendorService.get(id);
		}
		if (entity == null){
			entity = new BaseVendor();
		}
		return entity;
	}
	@Autowired
	private U8VendorService u8VendorService;
	@Autowired
	private BaseU8UpdateTimeService baseU8UpdateTimeService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(){
		AjaxJson json = new AjaxJson();
		try{
			U8Vendor vendor = new U8Vendor();
			BaseU8UpdateTime time = baseU8UpdateTimeService.getByCode(U8SynchType.GONGYINGSHANG.getCode());
			Date now = new Date();
			if(time==null){
				time = new BaseU8UpdateTime();
				time.setCode(U8SynchType.GONGYINGSHANG.getCode());
				time.setName(U8SynchType.GONGYINGSHANG.getName());
				time.setLastTime(DateUtil.addDays(now,-30));
				vendor.setModifyTime(null);
			}else {
				vendor.setNowTime(now).setModifyTime(time.getLastTime());
			}
			List<U8Vendor> data = u8VendorService.findList(vendor);
			if(data==null){
				time.setLastTime(now);
				baseU8UpdateTimeService.save(time);
				json.setMsg("同步成功(ERP数据空)");
				json.setSuccess(true);
				return json;
			}
			baseVendorService.sychU8(data);
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
	 * 供应商档案列表页面
	 */
	@RequiresPermissions("base:vendor:baseVendor:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseVendor baseVendor, Model model) {
		model.addAttribute("baseVendor", baseVendor);
		return "modules/base/vendor/baseVendorList";
	}
	
		/**
	 * 供应商档案列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:vendor:baseVendor:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseVendor baseVendor, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseVendor> page = baseVendorService.findPage(new Page<BaseVendor>(request, response), baseVendor); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑供应商档案表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:vendor:baseVendor:view","base:vendor:baseVendor:add","base:vendor:baseVendor:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseVendor baseVendor, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseVendor", baseVendor);
		return "modules/base/vendor/baseVendorForm";
	}

	/**
	 * 保存供应商档案
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:vendor:baseVendor:add","base:vendor:baseVendor:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseVendor baseVendor, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseVendor);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseVendorService.save(baseVendor);//保存
		j.setSuccess(true);
		j.setMsg("保存供应商档案成功");
		return j;
	}

	
	/**
	 * 批量删除供应商档案
	 */
	@ResponseBody
	@RequiresPermissions("base:vendor:baseVendor:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseVendorService.delete(baseVendorService.get(id));
		}
		j.setMsg("删除供应商档案成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:vendor:baseVendor:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseVendor baseVendor, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供应商档案"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseVendor> page = baseVendorService.findPage(new Page<BaseVendor>(request, response, -1), baseVendor);
    		new ExportExcel("供应商档案", BaseVendor.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出供应商档案记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:vendor:baseVendor:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseVendor> list = ei.getDataList(BaseVendor.class);
			for (BaseVendor baseVendor : list){
				try{
					baseVendorService.save(baseVendor);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供应商档案记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条供应商档案记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入供应商档案失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入供应商档案数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:vendor:baseVendor:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供应商档案数据导入模板.xlsx";
    		List<BaseVendor> list = Lists.newArrayList(); 
    		new ExportExcel("供应商档案数据", BaseVendor.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}