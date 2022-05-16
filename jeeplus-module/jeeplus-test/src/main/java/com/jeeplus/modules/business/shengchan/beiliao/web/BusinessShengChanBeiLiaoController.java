/**
 * 
 */
package com.jeeplus.modules.business.shengchan.beiliao.web;

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
import com.jeeplus.modules.business.shengchan.beiliao.entity.BusinessShengChanBeiLiao;
import com.jeeplus.modules.business.shengchan.beiliao.service.BusinessShengChanBeiLiaoService;

/**
 * 生产备料Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/shengchan/beiliao/businessShengChanBeiLiao")
public class BusinessShengChanBeiLiaoController extends BaseController {

	@Autowired
	private BusinessShengChanBeiLiaoService businessShengChanBeiLiaoService;
	
	@ModelAttribute
	public BusinessShengChanBeiLiao get(@RequestParam(required=false) String id) {
		BusinessShengChanBeiLiao entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessShengChanBeiLiaoService.get(id);
		}
		if (entity == null){
			entity = new BusinessShengChanBeiLiao();
		}
		return entity;
	}
	
	/**
	 * 生产备料列表页面
	 */
	@RequiresPermissions("business:shengchan:beiliao:businessShengChanBeiLiao:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessShengChanBeiLiao businessShengChanBeiLiao, Model model) {
		model.addAttribute("businessShengChanBeiLiao", businessShengChanBeiLiao);
		return "modules/business/shengchan/beiliao/businessShengChanBeiLiaoList";
	}
	
		/**
	 * 生产备料列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:businessShengChanBeiLiao:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessShengChanBeiLiao businessShengChanBeiLiao, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessShengChanBeiLiao> page = businessShengChanBeiLiaoService.findPage(new Page<BusinessShengChanBeiLiao>(request, response), businessShengChanBeiLiao); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑生产备料表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:shengchan:beiliao:businessShengChanBeiLiao:view","business:shengchan:beiliao:businessShengChanBeiLiao:add","business:shengchan:beiliao:businessShengChanBeiLiao:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessShengChanBeiLiao businessShengChanBeiLiao, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessShengChanBeiLiao", businessShengChanBeiLiao);
		return "modules/business/shengchan/beiliao/businessShengChanBeiLiaoForm";
	}

	/**
	 * 保存生产备料
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:shengchan:beiliao:businessShengChanBeiLiao:add","business:shengchan:beiliao:businessShengChanBeiLiao:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessShengChanBeiLiao businessShengChanBeiLiao, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessShengChanBeiLiao);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessShengChanBeiLiaoService.save(businessShengChanBeiLiao);//保存
		j.setSuccess(true);
		j.setMsg("保存生产备料成功");
		return j;
	}

	
	/**
	 * 批量删除生产备料
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:businessShengChanBeiLiao:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessShengChanBeiLiaoService.delete(businessShengChanBeiLiaoService.get(id));
		}
		j.setMsg("删除生产备料成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:businessShengChanBeiLiao:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessShengChanBeiLiao businessShengChanBeiLiao, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产备料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessShengChanBeiLiao> page = businessShengChanBeiLiaoService.findPage(new Page<BusinessShengChanBeiLiao>(request, response, -1), businessShengChanBeiLiao);
    		new ExportExcel("生产备料", BusinessShengChanBeiLiao.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出生产备料记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessShengChanBeiLiao detail(String id) {
		return businessShengChanBeiLiaoService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:businessShengChanBeiLiao:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessShengChanBeiLiao> list = ei.getDataList(BusinessShengChanBeiLiao.class);
			for (BusinessShengChanBeiLiao businessShengChanBeiLiao : list){
				try{
					businessShengChanBeiLiaoService.save(businessShengChanBeiLiao);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条生产备料记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条生产备料记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入生产备料失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入生产备料数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:shengchan:beiliao:businessShengChanBeiLiao:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "生产备料数据导入模板.xlsx";
    		List<BusinessShengChanBeiLiao> list = Lists.newArrayList(); 
    		new ExportExcel("生产备料数据", BusinessShengChanBeiLiao.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}