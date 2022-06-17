/**
 *
 */
package com.jeeplus.modules.base.dept.time.web;

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
import com.jeeplus.modules.base.dept.time.entity.BaseDeptTime;
import com.jeeplus.modules.base.dept.time.service.BaseDeptTimeService;

/**
 * 部门时间Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/dept/time/baseDeptTime")
public class BaseDeptTimeController extends BaseController {

	@Autowired
	private BaseDeptTimeService baseDeptTimeService;
	
	@ModelAttribute
	public BaseDeptTime get(@RequestParam(required=false) String id) {
		BaseDeptTime entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseDeptTimeService.get(id);
		}
		if (entity == null){
			entity = new BaseDeptTime();
		}
		return entity;
	}
	
	/**
	 * 部门时间列表页面
	 */
	@RequiresPermissions("base:dept:time:baseDeptTime:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseDeptTime baseDeptTime, Model model) {
		model.addAttribute("baseDeptTime", baseDeptTime);
		return "modules/base/dept/time/baseDeptTimeList";
	}
	
		/**
	 * 部门时间列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:dept:time:baseDeptTime:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseDeptTime baseDeptTime, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseDeptTime> page = baseDeptTimeService.findPage(new Page<BaseDeptTime>(request, response), baseDeptTime); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑部门时间表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:dept:time:baseDeptTime:view","base:dept:time:baseDeptTime:add","base:dept:time:baseDeptTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseDeptTime baseDeptTime, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseDeptTime", baseDeptTime);
		return "modules/base/dept/time/baseDeptTimeForm";
	}

	/**
	 * 保存部门时间
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:dept:time:baseDeptTime:add","base:dept:time:baseDeptTime:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseDeptTime baseDeptTime, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseDeptTime);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseDeptTimeService.save(baseDeptTime);//保存
		j.setSuccess(true);
		j.setMsg("保存部门时间成功");
		return j;
	}

	
	/**
	 * 批量删除部门时间
	 */
	@ResponseBody
	@RequiresPermissions("base:dept:time:baseDeptTime:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseDeptTimeService.delete(baseDeptTimeService.get(id));
		}
		j.setMsg("删除部门时间成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:dept:time:baseDeptTime:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseDeptTime baseDeptTime, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "部门时间"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseDeptTime> page = baseDeptTimeService.findPage(new Page<BaseDeptTime>(request, response, -1), baseDeptTime);
    		new ExportExcel("部门时间", BaseDeptTime.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出部门时间记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:dept:time:baseDeptTime:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseDeptTime> list = ei.getDataList(BaseDeptTime.class);
			for (BaseDeptTime baseDeptTime : list){
				try{
					baseDeptTimeService.save(baseDeptTime);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条部门时间记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条部门时间记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入部门时间失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入部门时间数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:dept:time:baseDeptTime:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "部门时间数据导入模板.xlsx";
    		List<BaseDeptTime> list = Lists.newArrayList(); 
    		new ExportExcel("部门时间数据", BaseDeptTime.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}