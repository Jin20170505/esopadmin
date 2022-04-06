/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.web;

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
import com.jeeplus.modules.qiyewx.daka.month.entity.QiYeWxDaKaMonth;
import com.jeeplus.modules.qiyewx.daka.month.service.QiYeWxDaKaMonthService;

/**
 * 打卡月报Controller
 * @author Jin
 * @version 2021-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/daka/month/qiYeWxDaKaMonth")
public class QiYeWxDaKaMonthController extends BaseController {

	@Autowired
	private QiYeWxDaKaMonthService qiYeWxDaKaMonthService;
	
	@ModelAttribute
	public QiYeWxDaKaMonth get(@RequestParam(required=false) String id) {
		QiYeWxDaKaMonth entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxDaKaMonthService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxDaKaMonth();
		}
		return entity;
	}
	
	/**
	 * 打卡月报列表页面
	 */
	@RequiresPermissions("qiyewx:daka:month:qiYeWxDaKaMonth:list")
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxDaKaMonth qiYeWxDaKaMonth, Model model) {
		model.addAttribute("qiYeWxDaKaMonth", qiYeWxDaKaMonth);
		return "modules/qiyewx/daka/month/qiYeWxDaKaMonthList";
	}
	
		/**
	 * 打卡月报列表数据
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:month:qiYeWxDaKaMonth:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYeWxDaKaMonth qiYeWxDaKaMonth, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYeWxDaKaMonth> page = qiYeWxDaKaMonthService.findPage(new Page<QiYeWxDaKaMonth>(request, response), qiYeWxDaKaMonth); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑打卡月报表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:daka:month:qiYeWxDaKaMonth:view","qiyewx:daka:month:qiYeWxDaKaMonth:add","qiyewx:daka:month:qiYeWxDaKaMonth:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxDaKaMonth qiYeWxDaKaMonth, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxDaKaMonth", qiYeWxDaKaMonth);
		return "modules/qiyewx/daka/month/qiYeWxDaKaMonthForm";
	}
	@RequestMapping(value = "goToSync")
	public String goToSync(Model model) {
		return "modules/qiyewx/daka/month/year_month_select";
	}
	/**
	 * 同步微信数据
	 */
	@ResponseBody
	@RequestMapping(value = "syncData")
	public AjaxJson syncData(String start,String end,String ym) {
		AjaxJson j = new AjaxJson();
		try {
			j.setSuccess(true);
			qiYeWxDaKaMonthService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),ym);
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		j.setMsg("同步成功");
		return j;
	}
	/**
	 * 保存打卡月报
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:daka:month:qiYeWxDaKaMonth:add","qiyewx:daka:month:qiYeWxDaKaMonth:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxDaKaMonth qiYeWxDaKaMonth, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxDaKaMonth);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYeWxDaKaMonthService.save(qiYeWxDaKaMonth);//保存
		j.setSuccess(true);
		j.setMsg("保存打卡月报成功");
		return j;
	}

	
	/**
	 * 批量删除打卡月报
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:month:qiYeWxDaKaMonth:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYeWxDaKaMonthService.delete(qiYeWxDaKaMonthService.get(id));
		}
		j.setMsg("删除打卡月报成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:month:qiYeWxDaKaMonth:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYeWxDaKaMonth qiYeWxDaKaMonth, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "打卡月报"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYeWxDaKaMonth> page = qiYeWxDaKaMonthService.findPage(new Page<QiYeWxDaKaMonth>(request, response, -1), qiYeWxDaKaMonth);
    		new ExportExcel("打卡月报", QiYeWxDaKaMonth.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出打卡月报记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public QiYeWxDaKaMonth detail(String id) {
		return qiYeWxDaKaMonthService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:month:qiYeWxDaKaMonth:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYeWxDaKaMonth> list = ei.getDataList(QiYeWxDaKaMonth.class);
			for (QiYeWxDaKaMonth qiYeWxDaKaMonth : list){
				try{
					qiYeWxDaKaMonthService.save(qiYeWxDaKaMonth);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条打卡月报记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条打卡月报记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入打卡月报失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入打卡月报数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:daka:month:qiYeWxDaKaMonth:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "打卡月报数据导入模板.xlsx";
    		List<QiYeWxDaKaMonth> list = Lists.newArrayList(); 
    		new ExportExcel("打卡月报数据", QiYeWxDaKaMonth.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}