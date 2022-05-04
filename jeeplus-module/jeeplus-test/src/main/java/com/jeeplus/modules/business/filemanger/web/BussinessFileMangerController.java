/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.filemanger.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.sys.utils.FileKit;
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
import com.jeeplus.modules.business.filemanger.entity.BussinessFileManger;
import com.jeeplus.modules.business.filemanger.service.BussinessFileMangerService;

/**
 * 文件档案Controller
 * @author Jin
 * @version 2022-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/business/filemanger/bussinessFileManger")
public class BussinessFileMangerController extends BaseController {

	@Autowired
	private BussinessFileMangerService bussinessFileMangerService;
	
	@ModelAttribute
	public BussinessFileManger get(@RequestParam(required=false) String id) {
		BussinessFileManger entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = bussinessFileMangerService.get(id);
		}
		if (entity == null){
			entity = new BussinessFileManger();
		}
		return entity;
	}
	
	/**
	 * 文件档案列表页面
	 */
	@RequiresPermissions("business:filemanger:bussinessFileManger:list")
	@RequestMapping(value = {"list", ""})
	public String list(BussinessFileManger bussinessFileManger, Model model) {
		model.addAttribute("bussinessFileManger", bussinessFileManger);
		return "modules/business/filemanger/bussinessFileMangerList";
	}
	
		/**
	 * 文件档案列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:filemanger:bussinessFileManger:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BussinessFileManger bussinessFileManger, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BussinessFileManger> page = bussinessFileMangerService.findPage(new Page<BussinessFileManger>(request, response), bussinessFileManger); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑文件档案表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:filemanger:bussinessFileManger:view","business:filemanger:bussinessFileManger:add","business:filemanger:bussinessFileManger:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BussinessFileManger bussinessFileManger, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("bussinessFileManger", bussinessFileManger);
		return "modules/business/filemanger/bussinessFileMangerForm";
	}

	/**
	 * 保存文件档案
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:filemanger:bussinessFileManger:add","business:filemanger:bussinessFileManger:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BussinessFileManger bussinessFileManger, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(bussinessFileManger);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		// 文件地址
		String url = bussinessFileManger.getUrl();
		String filename = url.substring(url.lastIndexOf("/")+1);
		bussinessFileManger.setFilename(filename);
		bussinessFileManger.setPath(FileKit.getAttachmentDir()+url.substring(url.indexOf("param/")+6));
		//新增或编辑表单保存
		bussinessFileMangerService.save(bussinessFileManger);//保存
		j.setSuccess(true);
		j.setMsg("保存文件档案成功");
		return j;
	}

	
	/**
	 * 批量删除文件档案
	 */
	@ResponseBody
	@RequiresPermissions("business:filemanger:bussinessFileManger:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			bussinessFileMangerService.delete(bussinessFileMangerService.get(id));
		}
		j.setMsg("删除文件档案成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:filemanger:bussinessFileManger:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BussinessFileManger bussinessFileManger, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文件档案"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BussinessFileManger> page = bussinessFileMangerService.findPage(new Page<BussinessFileManger>(request, response, -1), bussinessFileManger);
    		new ExportExcel("文件档案", BussinessFileManger.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出文件档案记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:filemanger:bussinessFileManger:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BussinessFileManger> list = ei.getDataList(BussinessFileManger.class);
			for (BussinessFileManger bussinessFileManger : list){
				try{
					bussinessFileMangerService.save(bussinessFileManger);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文件档案记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条文件档案记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入文件档案失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入文件档案数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:filemanger:bussinessFileManger:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "文件档案数据导入模板.xlsx";
    		List<BussinessFileManger> list = Lists.newArrayList(); 
    		new ExportExcel("文件档案数据", BussinessFileManger.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}