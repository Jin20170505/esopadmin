/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.qiyewx.daka_day.web;

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
import com.jeeplus.modules.qiyewx.daka_day.entity.QiYewxDaKaDay;
import com.jeeplus.modules.qiyewx.daka_day.service.QiYewxDaKaDayService;

/**
 * 打卡日报Controller
 * @author Jin
 * @version 2021-11-25
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/daka_day/qiYewxDaKaDay")
public class QiYewxDaKaDayController extends BaseController {

	@Autowired
	private QiYewxDaKaDayService qiYewxDaKaDayService;
	
	@ModelAttribute
	public QiYewxDaKaDay get(@RequestParam(required=false) String id) {
		QiYewxDaKaDay entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYewxDaKaDayService.get(id);
		}
		if (entity == null){
			entity = new QiYewxDaKaDay();
		}
		return entity;
	}
	
	/**
	 * 打卡日报列表页面
	 */
	@RequiresPermissions("qiyewx:daka_day:qiYewxDaKaDay:list")
	@RequestMapping(value = {"list", ""})
	public String list(QiYewxDaKaDay qiYewxDaKaDay, Model model) {
		model.addAttribute("qiYewxDaKaDay", qiYewxDaKaDay);
		return "modules/qiyewx/daka_day/qiYewxDaKaDayList";
	}
	@RequestMapping(value = "goToSync")
	public String goToSync(Model model) {
		return "modules/qiyewx/daka_day/sync_daka";
	}
	/**
	 * 同步微信数据
	 */
	@ResponseBody
	@RequestMapping(value = "syncData")
	public AjaxJson syncData(String start,String end) {
		AjaxJson j = new AjaxJson();
		try {
			j.setSuccess(true);
			qiYewxDaKaDayService.syncDaKaRecord(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"));
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		j.setMsg("同步成功");
		return j;
	}
		/**
	 * 打卡日报列表数据
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka_day:qiYewxDaKaDay:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYewxDaKaDay qiYewxDaKaDay, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYewxDaKaDay> page = qiYewxDaKaDayService.findPage(new Page<QiYewxDaKaDay>(request, response), qiYewxDaKaDay); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑打卡日报表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:daka_day:qiYewxDaKaDay:view","qiyewx:daka_day:qiYewxDaKaDay:add","qiyewx:daka_day:qiYewxDaKaDay:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYewxDaKaDay qiYewxDaKaDay, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYewxDaKaDay", qiYewxDaKaDay);
		return "modules/qiyewx/daka_day/qiYewxDaKaDayForm";
	}

	/**
	 * 保存打卡日报
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:daka_day:qiYewxDaKaDay:add","qiyewx:daka_day:qiYewxDaKaDay:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYewxDaKaDay qiYewxDaKaDay, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYewxDaKaDay);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYewxDaKaDayService.save(qiYewxDaKaDay);//保存
		j.setSuccess(true);
		j.setMsg("保存打卡日报成功");
		return j;
	}

	
	/**
	 * 批量删除打卡日报
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka_day:qiYewxDaKaDay:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYewxDaKaDayService.delete(qiYewxDaKaDayService.get(id));
		}
		j.setMsg("删除打卡日报成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka_day:qiYewxDaKaDay:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYewxDaKaDay qiYewxDaKaDay, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "打卡日报"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYewxDaKaDay> page = qiYewxDaKaDayService.findPage(new Page<QiYewxDaKaDay>(request, response, -1), qiYewxDaKaDay);
    		new ExportExcel("打卡日报", QiYewxDaKaDay.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出打卡日报记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public QiYewxDaKaDay detail(String id) {
		return qiYewxDaKaDayService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka_day:qiYewxDaKaDay:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYewxDaKaDay> list = ei.getDataList(QiYewxDaKaDay.class);
			for (QiYewxDaKaDay qiYewxDaKaDay : list){
				try{
					qiYewxDaKaDayService.save(qiYewxDaKaDay);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条打卡日报记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条打卡日报记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入打卡日报失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入打卡日报数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka_day:qiYewxDaKaDay:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "打卡日报数据导入模板.xlsx";
    		List<QiYewxDaKaDay> list = Lists.newArrayList(); 
    		new ExportExcel("打卡日报数据", QiYewxDaKaDay.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}