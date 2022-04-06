/**
 *
 */
package com.jeeplus.modules.qiyewx.date_type.web;

import java.util.Date;
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
import com.jeeplus.modules.qiyewx.date_type.entity.QiYeWxDateType;
import com.jeeplus.modules.qiyewx.date_type.service.QiYeWxDateTypeService;

/**
 * 日期所属Controller
 * @author Jin
 * @version 2021-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/date_type/qiYeWxDateType")
public class QiYeWxDateTypeController extends BaseController {

	@Autowired
	private QiYeWxDateTypeService qiYeWxDateTypeService;
	
	@ModelAttribute
	public QiYeWxDateType get(@RequestParam(required=false) String id) {
		QiYeWxDateType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxDateTypeService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxDateType();
		}
		return entity;
	}
	@RequestMapping(value = {"goToComputer"})
	public String goToComputer(QiYeWxDateType qiYeWxDateType, Model model) {
		model.addAttribute("qiYeWxDateType", qiYeWxDateType);
		return "modules/qiyewx/date_type/year_month_select";
	}
	@ResponseBody
	@RequestMapping("js")
	public AjaxJson js(String ym,Date start, Date end){
		AjaxJson json = new AjaxJson();
		try {
			qiYeWxDateTypeService.js(ym,start,end);
			json.setSuccess(true);
			json.setMsg("计算成功!");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("pledit")
	public AjaxJson pledit(String rid,String type){
		AjaxJson json = new AjaxJson();
		try {
			qiYeWxDateTypeService.pledit(rid,type);
			json.setSuccess(true);
			json.setMsg("操作成功!");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg(e.getMessage());
		}
		return json;
	}
	@RequestMapping(value = {"goToEdit"})
	public String goToEdit(String rid, Model model) {
		model.addAttribute("rid", rid);
		return "modules/qiyewx/date_type/edit";
	}
	/**
	 * 日期所属列表页面
	 */
	@RequiresPermissions("qiyewx:date_type:qiYeWxDateType:list")
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxDateType qiYeWxDateType, Model model) {
		model.addAttribute("qiYeWxDateType", qiYeWxDateType);
		return "modules/qiyewx/date_type/qiYeWxDateTypeList";
	}
	
		/**
	 * 日期所属列表数据
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:date_type:qiYeWxDateType:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYeWxDateType qiYeWxDateType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYeWxDateType> page = qiYeWxDateTypeService.findPage(new Page<QiYeWxDateType>(request, response), qiYeWxDateType); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑日期所属表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:date_type:qiYeWxDateType:view","qiyewx:date_type:qiYeWxDateType:add","qiyewx:date_type:qiYeWxDateType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxDateType qiYeWxDateType, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxDateType", qiYeWxDateType);
		return "modules/qiyewx/date_type/qiYeWxDateTypeForm";
	}

	/**
	 * 保存日期所属
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:date_type:qiYeWxDateType:add","qiyewx:date_type:qiYeWxDateType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxDateType qiYeWxDateType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxDateType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYeWxDateTypeService.save(qiYeWxDateType);//保存
		j.setSuccess(true);
		j.setMsg("保存日期所属成功");
		return j;
	}

	
	/**
	 * 批量删除日期所属
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:date_type:qiYeWxDateType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYeWxDateTypeService.delete(qiYeWxDateTypeService.get(id));
		}
		j.setMsg("删除日期所属成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:date_type:qiYeWxDateType:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYeWxDateType qiYeWxDateType, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "日期所属"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYeWxDateType> page = qiYeWxDateTypeService.findPage(new Page<QiYeWxDateType>(request, response, -1), qiYeWxDateType);
    		new ExportExcel("日期所属", QiYeWxDateType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出日期所属记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:date_type:qiYeWxDateType:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYeWxDateType> list = ei.getDataList(QiYeWxDateType.class);
			for (QiYeWxDateType qiYeWxDateType : list){
				try{
					qiYeWxDateTypeService.save(qiYeWxDateType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条日期所属记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条日期所属记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入日期所属失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入日期所属数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:date_type:qiYeWxDateType:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "日期所属数据导入模板.xlsx";
    		List<QiYeWxDateType> list = Lists.newArrayList(); 
    		new ExportExcel("日期所属数据", QiYeWxDateType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}