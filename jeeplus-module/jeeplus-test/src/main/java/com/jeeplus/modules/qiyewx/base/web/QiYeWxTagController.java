/**
 *
 */
package com.jeeplus.modules.qiyewx.base.web;

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
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxTag;
import com.jeeplus.modules.qiyewx.base.service.QiYeWxTagService;

/**
 * 微信标签Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/base/qiYeWxTag")
public class QiYeWxTagController extends BaseController {

	@Autowired
	private QiYeWxTagService qiYeWxTagService;
	
	@ModelAttribute
	public QiYeWxTag get(@RequestParam(required=false) String id) {
		QiYeWxTag entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxTagService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxTag();
		}
		return entity;
	}
	
	/**
	 * 微信标签列表页面
	 */
	@RequiresPermissions("qiyewx:base:qiYeWxTag:list")
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxTag qiYeWxTag, Model model) {
		model.addAttribute("qiYeWxTag", qiYeWxTag);
		return "modules/qiyewx/base/qiYeWxTagList";
	}

	@RequestMapping("sych")
	@ResponseBody
	public AjaxJson sych(){
		AjaxJson j = new AjaxJson();
		try{
			qiYeWxTagService.sychData();
			j.setSuccess(true);
			j.setMsg("同步成功");
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg("同步失败");
			j.setSuccess(false);
		}
		return j;
	}

	@RequestMapping("sychTagUser")
	@ResponseBody
	public AjaxJson sychTagUser(){
		AjaxJson j = new AjaxJson();
		try{
			qiYeWxTagService.sychTagUser();
			j.setSuccess(true);
			j.setMsg("同步成功");
		}catch (Exception e){
			e.printStackTrace();
			j.setMsg("同步失败");
			j.setSuccess(false);
		}
		return j;
	}
		/**
	 * 微信标签列表数据
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxTag:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(QiYeWxTag qiYeWxTag, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<QiYeWxTag> page = qiYeWxTagService.findPage(new Page<QiYeWxTag>(request, response), qiYeWxTag); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑微信标签表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"qiyewx:base:qiYeWxTag:view","qiyewx:base:qiYeWxTag:add","qiyewx:base:qiYeWxTag:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxTag qiYeWxTag, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxTag", qiYeWxTag);
		return "modules/qiyewx/base/qiYeWxTagForm";
	}

	/**
	 * 保存微信标签
	 */
	@ResponseBody
	@RequiresPermissions(value={"qiyewx:base:qiYeWxTag:add","qiyewx:base:qiYeWxTag:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxTag qiYeWxTag, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxTag);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		qiYeWxTagService.save(qiYeWxTag);//保存
		j.setSuccess(true);
		j.setMsg("保存微信标签成功");
		return j;
	}

	
	/**
	 * 批量删除微信标签
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxTag:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			qiYeWxTagService.delete(qiYeWxTagService.get(id));
		}
		j.setMsg("删除微信标签成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxTag:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(QiYeWxTag qiYeWxTag, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "微信标签"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<QiYeWxTag> page = qiYeWxTagService.findPage(new Page<QiYeWxTag>(request, response, -1), qiYeWxTag);
    		new ExportExcel("微信标签", QiYeWxTag.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出微信标签记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxTag:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<QiYeWxTag> list = ei.getDataList(QiYeWxTag.class);
			for (QiYeWxTag qiYeWxTag : list){
				try{
					qiYeWxTagService.save(qiYeWxTag);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条微信标签记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条微信标签记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入微信标签失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入微信标签数据模板
	 */
	@ResponseBody
	@RequiresPermissions("qiyewx:base:qiYeWxTag:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "微信标签数据导入模板.xlsx";
    		List<QiYeWxTag> list = Lists.newArrayList(); 
    		new ExportExcel("微信标签数据", QiYeWxTag.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}