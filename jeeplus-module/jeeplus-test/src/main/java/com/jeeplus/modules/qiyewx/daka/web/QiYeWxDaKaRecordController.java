/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.web;

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
import com.jeeplus.modules.qiyewx.daka.entity.QiYeWxDaKaRecord;
import com.jeeplus.modules.qiyewx.daka.service.QiYeWxDaKaRecordService;

/**
 * 打卡记录Controller
 * @author Jin
 * @version 2021-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/daka/qiYeWxDaKaRecord")
public class QiYeWxDaKaRecordController extends BaseController {

	@Autowired
	private QiYeWxDaKaRecordService qiYeWxDaKaRecordService;
	
	@ModelAttribute
	public QiYeWxDaKaRecord get(@RequestParam(required=false) String id) {
		QiYeWxDaKaRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxDaKaRecordService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxDaKaRecord();
		}
		return entity;
	}
	
	/**
	 * 打卡记录列表页面
	 */
	@RequiresPermissions("qiyewx:daka:qiYeWxDaKaRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxDaKaRecord qiYeWxDaKaRecord, Model model) {
		model.addAttribute("qiYeWxDaKaRecord", qiYeWxDaKaRecord);
		return "modules/qiyewx/daka/qiYeWxDaKaRecordList";
	}
	
		/**
	 * 打卡记录列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYeWxDaKaRecord qiYeWxDaKaRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYeWxDaKaRecord> page = qiYeWxDaKaRecordService.findPage(new Page<QiYeWxDaKaRecord>(request, response), qiYeWxDaKaRecord); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑打卡记录表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:daka:qiYeWxDaKaRecord:view","qiyewx:daka:qiYeWxDaKaRecord:add","qiyewx:daka:qiYeWxDaKaRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxDaKaRecord qiYeWxDaKaRecord, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxDaKaRecord", qiYeWxDaKaRecord);
		return "modules/qiyewx/daka/qiYeWxDaKaRecordForm";
	}
	@RequestMapping(value = "goToSync")
	public String goToSync(Model model) {
		return "modules/qiyewx/daka/sync_daka";
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
			qiYeWxDaKaRecordService.syncDaKaRecord(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"));
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		j.setMsg("同步成功");
		return j;
	}
	/**
	 * 保存打卡记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:daka:qiYeWxDaKaRecord:add","qiyewx:daka:qiYeWxDaKaRecord:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxDaKaRecord qiYeWxDaKaRecord, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxDaKaRecord);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYeWxDaKaRecordService.save(qiYeWxDaKaRecord);//保存
		j.setSuccess(true);
		j.setMsg("保存打卡记录成功");
		return j;
	}

	
	/**
	 * 批量删除打卡记录
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:qiYeWxDaKaRecord:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYeWxDaKaRecordService.delete(qiYeWxDaKaRecordService.get(id));
		}
		j.setMsg("删除打卡记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:qiYeWxDaKaRecord:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYeWxDaKaRecord qiYeWxDaKaRecord, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "打卡记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYeWxDaKaRecord> page = qiYeWxDaKaRecordService.findPage(new Page<QiYeWxDaKaRecord>(request, response, -1), qiYeWxDaKaRecord);
    		new ExportExcel("打卡记录", QiYeWxDaKaRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出打卡记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:qiYeWxDaKaRecord:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYeWxDaKaRecord> list = ei.getDataList(QiYeWxDaKaRecord.class);
			for (QiYeWxDaKaRecord qiYeWxDaKaRecord : list){
				try{
					qiYeWxDaKaRecordService.save(qiYeWxDaKaRecord);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条打卡记录记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条打卡记录记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入打卡记录失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入打卡记录数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:qiYeWxDaKaRecord:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "打卡记录数据导入模板.xlsx";
    		List<QiYeWxDaKaRecord> list = Lists.newArrayList(); 
    		new ExportExcel("打卡记录数据", QiYeWxDaKaRecord.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}