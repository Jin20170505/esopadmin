/**
 *
 */
package com.jeeplus.modules.base.erp.updatetime.web;

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
import com.jeeplus.modules.base.erp.updatetime.entity.BaseU8UpdateTime;
import com.jeeplus.modules.base.erp.updatetime.service.BaseU8UpdateTimeService;

/**
 * ERP更新时间Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/erp/updatetime/baseU8UpdateTime")
public class BaseU8UpdateTimeController extends BaseController {

	@Autowired
	private BaseU8UpdateTimeService baseU8UpdateTimeService;
	
	@ModelAttribute
	public BaseU8UpdateTime get(@RequestParam(required=false) String id) {
		BaseU8UpdateTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseU8UpdateTimeService.get(id);
		}
		if (entity == null){
			entity = new BaseU8UpdateTime();
		}
		return entity;
	}
	
	/**
	 * ERP更新时间列表页面
	 */
	@RequiresPermissions("base:erp:updatetime:baseU8UpdateTime:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseU8UpdateTime baseU8UpdateTime, Model model) {
		model.addAttribute("baseU8UpdateTime", baseU8UpdateTime);
		return "modules/base/erp/updatetime/baseU8UpdateTimeList";
	}
	
		/**
	 * ERP更新时间列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:erp:updatetime:baseU8UpdateTime:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseU8UpdateTime baseU8UpdateTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseU8UpdateTime> page = baseU8UpdateTimeService.findPage(new Page<BaseU8UpdateTime>(request, response), baseU8UpdateTime); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑ERP更新时间表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:erp:updatetime:baseU8UpdateTime:view","base:erp:updatetime:baseU8UpdateTime:add","base:erp:updatetime:baseU8UpdateTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseU8UpdateTime baseU8UpdateTime, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseU8UpdateTime", baseU8UpdateTime);
		return "modules/base/erp/updatetime/baseU8UpdateTimeForm";
	}

	/**
	 * 保存ERP更新时间
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:erp:updatetime:baseU8UpdateTime:add","base:erp:updatetime:baseU8UpdateTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseU8UpdateTime baseU8UpdateTime, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseU8UpdateTime);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseU8UpdateTimeService.save(baseU8UpdateTime);//保存
		j.setSuccess(true);
		j.setMsg("保存ERP更新时间成功");
		return j;
	}

	
	/**
	 * 批量删除ERP更新时间
	 */
	@ResponseBody
	@RequiresPermissions("base:erp:updatetime:baseU8UpdateTime:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseU8UpdateTimeService.delete(baseU8UpdateTimeService.get(id));
		}
		j.setMsg("删除ERP更新时间成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:erp:updatetime:baseU8UpdateTime:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseU8UpdateTime baseU8UpdateTime, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ERP更新时间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseU8UpdateTime> page = baseU8UpdateTimeService.findPage(new Page<BaseU8UpdateTime>(request, response, -1), baseU8UpdateTime);
    		new ExportExcel("ERP更新时间", BaseU8UpdateTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出ERP更新时间记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:erp:updatetime:baseU8UpdateTime:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseU8UpdateTime> list = ei.getDataList(BaseU8UpdateTime.class);
			for (BaseU8UpdateTime baseU8UpdateTime : list){
				try{
					baseU8UpdateTimeService.save(baseU8UpdateTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条ERP更新时间记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条ERP更新时间记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入ERP更新时间失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入ERP更新时间数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:erp:updatetime:baseU8UpdateTime:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ERP更新时间数据导入模板.xlsx";
    		List<BaseU8UpdateTime> list = Lists.newArrayList(); 
    		new ExportExcel("ERP更新时间数据", BaseU8UpdateTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}