/**
 * 
 */
package com.jeeplus.modules.business.chuku.ommo.web;

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
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWai;
import com.jeeplus.modules.business.chuku.ommo.service.BusinessChuKuWeiWaiService;

/**
 * 委外发料Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/chuku/ommo/businessChuKuWeiWai")
public class BusinessChuKuWeiWaiController extends BaseController {

	@Autowired
	private BusinessChuKuWeiWaiService businessChuKuWeiWaiService;
	
	@ModelAttribute
	public BusinessChuKuWeiWai get(@RequestParam(required=false) String id) {
		BusinessChuKuWeiWai entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessChuKuWeiWaiService.get(id);
		}
		if (entity == null){
			entity = new BusinessChuKuWeiWai();
		}
		return entity;
	}
	
	/**
	 * 委外发料列表页面
	 */
	@RequiresPermissions("business:chuku:ommo:businessChuKuWeiWai:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessChuKuWeiWai businessChuKuWeiWai, Model model) {
		model.addAttribute("businessChuKuWeiWai", businessChuKuWeiWai);
		return "modules/business/chuku/ommo/businessChuKuWeiWaiList";
	}
	
		/**
	 * 委外发料列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:ommo:businessChuKuWeiWai:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessChuKuWeiWai businessChuKuWeiWai, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessChuKuWeiWai> page = businessChuKuWeiWaiService.findPage(new Page<BusinessChuKuWeiWai>(request, response), businessChuKuWeiWai); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑委外发料表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:chuku:ommo:businessChuKuWeiWai:view","business:chuku:ommo:businessChuKuWeiWai:add","business:chuku:ommo:businessChuKuWeiWai:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessChuKuWeiWai businessChuKuWeiWai, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessChuKuWeiWai", businessChuKuWeiWai);
		return "modules/business/chuku/ommo/businessChuKuWeiWaiForm";
	}

	/**
	 * 保存委外发料
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:chuku:ommo:businessChuKuWeiWai:add","business:chuku:ommo:businessChuKuWeiWai:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessChuKuWeiWai businessChuKuWeiWai, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessChuKuWeiWai);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessChuKuWeiWaiService.save(businessChuKuWeiWai);//保存
		j.setSuccess(true);
		j.setMsg("保存委外发料成功");
		return j;
	}

	
	/**
	 * 批量删除委外发料
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:ommo:businessChuKuWeiWai:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessChuKuWeiWaiService.delete(businessChuKuWeiWaiService.get(id));
		}
		j.setMsg("删除委外发料成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:ommo:businessChuKuWeiWai:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessChuKuWeiWai businessChuKuWeiWai, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外发料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessChuKuWeiWai> page = businessChuKuWeiWaiService.findPage(new Page<BusinessChuKuWeiWai>(request, response, -1), businessChuKuWeiWai);
    		new ExportExcel("委外发料", BusinessChuKuWeiWai.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出委外发料记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessChuKuWeiWai detail(String id) {
		return businessChuKuWeiWaiService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:ommo:businessChuKuWeiWai:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessChuKuWeiWai> list = ei.getDataList(BusinessChuKuWeiWai.class);
			for (BusinessChuKuWeiWai businessChuKuWeiWai : list){
				try{
					businessChuKuWeiWaiService.save(businessChuKuWeiWai);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条委外发料记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条委外发料记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入委外发料失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入委外发料数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:ommo:businessChuKuWeiWai:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "委外发料数据导入模板.xlsx";
    		List<BusinessChuKuWeiWai> list = Lists.newArrayList(); 
    		new ExportExcel("委外发料数据", BusinessChuKuWeiWai.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}