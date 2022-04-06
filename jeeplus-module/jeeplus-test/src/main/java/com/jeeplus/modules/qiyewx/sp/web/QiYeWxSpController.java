/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.web;

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
import com.jeeplus.modules.qiyewx.sp.entity.QiYeWxSp;
import com.jeeplus.modules.qiyewx.sp.service.QiYeWxSpService;

/**
 * 申请审批Controller
 * @author Jin
 * @version 2021-08-31
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/sp/qiYeWxSp")
public class QiYeWxSpController extends BaseController {

	@Autowired
	private QiYeWxSpService qiYeWxSpService;
	
	@ModelAttribute
	public QiYeWxSp get(@RequestParam(required=false) String id) {
		QiYeWxSp entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxSpService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxSp();
		}
		return entity;
	}
	
	/**
	 * 申请审批列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/qiYeWxSpList";
	}
	@RequestMapping(value = {"qingjialist"})
	public String qingjialist(QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/qingjia/qiYeWxSpList";
	}
	@RequestMapping(value = {"dakabukalist"})
	public String dakabukalist(QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/dakabuka/qiYeWxSpList";
	}
	@RequestMapping(value = {"chuchailist"})
	public String chuchailist(QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/chuchai/qiYeWxSpList";
	}
	@RequestMapping(value = {"waichulist"})
	public String waichulist(QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/waichu/qiYeWxSpList";
	}
	@RequestMapping(value = {"jiabanlist"})
	public String jiabanlist(QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/jiaban/qiYeWxSpList";
	}

	@RequestMapping(value = "goToSyncByType")
	public String goToSyncByType(Model model,String type) {
		model.addAttribute("type",type);
		return "modules/qiyewx/sp/year_month_select_type";
	}
	@RequestMapping(value = "goToSync")
	public String goToSync(Model model) {
		return "modules/qiyewx/sp/year_month_select";
	}
	/**
	 * 同步微信数据
	 */
	@ResponseBody
	@RequestMapping(value = "syncData")
	public AjaxJson syncData(String start,String end,String ym,String type) {
		AjaxJson j = new AjaxJson();
		try {
			j.setSuccess(true);
			if(StringUtils.isEmpty(type)){
				qiYeWxSpService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),"1",ym);
				qiYeWxSpService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),"2",ym);
				qiYeWxSpService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),"3",ym);
				qiYeWxSpService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),"4",ym);
				qiYeWxSpService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),"5",ym);
			}else {
				qiYeWxSpService.syncData(DateUtils.parseDate(start,"yyyyy-MM-dd HH:mm:ss"),DateUtils.parseDate(end,"yyyyy-MM-dd HH:mm:ss"),type,ym);
			}

		}catch (Exception e){
			e.printStackTrace();
			j.setMsg(e.getMessage());
			j.setSuccess(false);
		}
		j.setMsg("同步成功");
		return j;
	}
		/**
	 * 申请审批列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYeWxSp qiYeWxSp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYeWxSp> page = qiYeWxSpService.findPage(new Page<QiYeWxSp>(request, response), qiYeWxSp); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑申请审批表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:sp:qiYeWxSp:view","qiyewx:sp:qiYeWxSp:add","qiyewx:sp:qiYeWxSp:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxSp qiYeWxSp, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxSp", qiYeWxSp);
		return "modules/qiyewx/sp/qiYeWxSpForm";
	}

	/**
	 * 保存申请审批
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:sp:qiYeWxSp:add","qiyewx:sp:qiYeWxSp:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxSp qiYeWxSp, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxSp);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYeWxSpService.save(qiYeWxSp);//保存
		j.setSuccess(true);
		j.setMsg("保存申请审批成功");
		return j;
	}

	
	/**
	 * 批量删除申请审批
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:sp:qiYeWxSp:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYeWxSpService.delete(qiYeWxSpService.get(id));
		}
		j.setMsg("删除申请审批成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:sp:qiYeWxSp:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYeWxSp qiYeWxSp, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "申请审批"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYeWxSp> page = qiYeWxSpService.findPage(new Page<QiYeWxSp>(request, response, -1), qiYeWxSp);
    		new ExportExcel("申请审批", QiYeWxSp.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出申请审批记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public QiYeWxSp detail(String id) {
		return qiYeWxSpService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:sp:qiYeWxSp:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYeWxSp> list = ei.getDataList(QiYeWxSp.class);
			for (QiYeWxSp qiYeWxSp : list){
				try{
					qiYeWxSpService.save(qiYeWxSp);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条申请审批记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条申请审批记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入申请审批失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入申请审批数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:sp:qiYeWxSp:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "申请审批数据导入模板.xlsx";
    		List<QiYeWxSp> list = Lists.newArrayList(); 
    		new ExportExcel("申请审批数据", QiYeWxSp.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

    @ResponseBody
    @RequestMapping("updateStatus")
	public AjaxJson updateStatus(){
		AjaxJson json = new AjaxJson();
		try {
			qiYeWxSpService.updateStatus();
			json.setSuccess(true);
			json.setMsg("操作成功");
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("操作失败");
		}
		return json;
	}
}