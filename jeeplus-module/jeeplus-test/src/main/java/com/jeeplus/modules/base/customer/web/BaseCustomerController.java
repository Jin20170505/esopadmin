/**
 *
 */
package com.jeeplus.modules.base.customer.web;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;
import com.jeeplus.modules.base.customer.service.BaseCustomerService;
import com.jeeplus.modules.base.erp.updatetime.entity.BaseU8UpdateTime;
import com.jeeplus.modules.base.erp.updatetime.entity.U8SynchType;
import com.jeeplus.modules.base.erp.updatetime.service.BaseU8UpdateTimeService;
import com.jeeplus.modules.u8data.customer.entity.U8Customer;
import com.jeeplus.modules.u8data.customer.service.U8CustomerService;
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
 * 客户档案Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/customer/baseCustomer")
public class BaseCustomerController extends BaseController {

	@Autowired
	private BaseCustomerService baseCustomerService;
	
	@ModelAttribute
	public BaseCustomer get(@RequestParam(required=false) String id) {
		BaseCustomer entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseCustomerService.get(id);
		}
		if (entity == null){
			entity = new BaseCustomer();
		}
		return entity;
	}
	@Autowired
	private U8CustomerService u8CustomerService;
	@Autowired
	private BaseU8UpdateTimeService baseU8UpdateTimeService;
	@ResponseBody
	@RequestMapping("sychu8")
	public AjaxJson sychU8(){
		AjaxJson json = new AjaxJson();
		try{
			U8Customer customer = new U8Customer();
			BaseU8UpdateTime time = baseU8UpdateTimeService.getByCode(U8SynchType.KEHU.getCode());
			Date now = new Date();
			if(time==null){
				time = new BaseU8UpdateTime();
				time.setCode(U8SynchType.ROUTE.getCode());
				time.setName(U8SynchType.ROUTE.getName());
				time.setLastTime(DateUtil.addDays(now,-30));
				customer.setModifyTime(null);
			}else {
				customer.setNowTime(now).setModifyTime(time.getLastTime());
			}
			List<U8Customer> data = u8CustomerService.findList(customer);
			if(data==null){
				time.setLastTime(now);
				baseU8UpdateTimeService.save(time);
				json.setMsg("同步成功(ERP数据空)");
				json.setSuccess(true);
				return json;
			}
			baseCustomerService.sychU8(data);
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
	 * 客户档案列表页面
	 */
	@RequiresPermissions("base:customer:baseCustomer:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseCustomer baseCustomer, Model model) {
		model.addAttribute("baseCustomer", baseCustomer);
		return "modules/base/customer/baseCustomerList";
	}
	
		/**
	 * 客户档案列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:customer:baseCustomer:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseCustomer baseCustomer, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseCustomer> page = baseCustomerService.findPage(new Page<BaseCustomer>(request, response), baseCustomer); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑客户档案表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:customer:baseCustomer:view","base:customer:baseCustomer:add","base:customer:baseCustomer:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseCustomer baseCustomer, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseCustomer", baseCustomer);
		return "modules/base/customer/baseCustomerForm";
	}

	/**
	 * 保存客户档案
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:customer:baseCustomer:add","base:customer:baseCustomer:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseCustomer baseCustomer, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseCustomer);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseCustomerService.save(baseCustomer);//保存
		j.setSuccess(true);
		j.setMsg("保存客户档案成功");
		return j;
	}

	
	/**
	 * 批量删除客户档案
	 */
	@ResponseBody
	@RequiresPermissions("base:customer:baseCustomer:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseCustomerService.delete(baseCustomerService.get(id));
		}
		j.setMsg("删除客户档案成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:customer:baseCustomer:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseCustomer baseCustomer, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户档案"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseCustomer> page = baseCustomerService.findPage(new Page<BaseCustomer>(request, response, -1), baseCustomer);
    		new ExportExcel("客户档案", BaseCustomer.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出客户档案记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:customer:baseCustomer:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseCustomer> list = ei.getDataList(BaseCustomer.class);
			for (BaseCustomer baseCustomer : list){
				try{
					baseCustomerService.save(baseCustomer);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条客户档案记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条客户档案记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入客户档案失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入客户档案数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:customer:baseCustomer:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "客户档案数据导入模板.xlsx";
    		List<BaseCustomer> list = Lists.newArrayList(); 
    		new ExportExcel("客户档案数据", BaseCustomer.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}