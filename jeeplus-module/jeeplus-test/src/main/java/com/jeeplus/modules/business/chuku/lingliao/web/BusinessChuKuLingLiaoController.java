/**
 * 
 */
package com.jeeplus.modules.business.chuku.lingliao.web;

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
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiao;
import com.jeeplus.modules.business.chuku.lingliao.service.BusinessChuKuLingLiaoService;

/**
 * 领料单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/chuku/lingliao/businessChuKuLingLiao")
public class BusinessChuKuLingLiaoController extends BaseController {

	@Autowired
	private BusinessChuKuLingLiaoService businessChuKuLingLiaoService;
	
	@ModelAttribute
	public BusinessChuKuLingLiao get(@RequestParam(required=false) String id) {
		BusinessChuKuLingLiao entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessChuKuLingLiaoService.get(id);
		}
		if (entity == null){
			entity = new BusinessChuKuLingLiao();
		}
		return entity;
	}
	
	/**
	 * 领料单列表页面
	 */
	@RequiresPermissions("business:chuku:lingliao:businessChuKuLingLiao:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessChuKuLingLiao businessChuKuLingLiao, Model model) {
		model.addAttribute("businessChuKuLingLiao", businessChuKuLingLiao);
		return "modules/business/chuku/lingliao/businessChuKuLingLiaoList";
	}
	
		/**
	 * 领料单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:lingliao:businessChuKuLingLiao:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessChuKuLingLiao businessChuKuLingLiao, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessChuKuLingLiao> page = businessChuKuLingLiaoService.findPage(new Page<BusinessChuKuLingLiao>(request, response), businessChuKuLingLiao); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑领料单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:chuku:lingliao:businessChuKuLingLiao:view","business:chuku:lingliao:businessChuKuLingLiao:add","business:chuku:lingliao:businessChuKuLingLiao:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessChuKuLingLiao businessChuKuLingLiao, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessChuKuLingLiao", businessChuKuLingLiao);
		return "modules/business/chuku/lingliao/businessChuKuLingLiaoForm";
	}

	/**
	 * 保存领料单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:chuku:lingliao:businessChuKuLingLiao:add","business:chuku:lingliao:businessChuKuLingLiao:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessChuKuLingLiao businessChuKuLingLiao, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessChuKuLingLiao);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessChuKuLingLiaoService.save(businessChuKuLingLiao);//保存
		j.setSuccess(true);
		j.setMsg("保存领料单成功");
		return j;
	}

	
	/**
	 * 批量删除领料单
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:lingliao:businessChuKuLingLiao:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessChuKuLingLiaoService.delete(businessChuKuLingLiaoService.get(id));
		}
		j.setMsg("删除领料单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:lingliao:businessChuKuLingLiao:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessChuKuLingLiao businessChuKuLingLiao, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "领料单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessChuKuLingLiao> page = businessChuKuLingLiaoService.findPage(new Page<BusinessChuKuLingLiao>(request, response, -1), businessChuKuLingLiao);
    		new ExportExcel("领料单", BusinessChuKuLingLiao.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出领料单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessChuKuLingLiao detail(String id) {
		return businessChuKuLingLiaoService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:lingliao:businessChuKuLingLiao:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessChuKuLingLiao> list = ei.getDataList(BusinessChuKuLingLiao.class);
			for (BusinessChuKuLingLiao businessChuKuLingLiao : list){
				try{
					businessChuKuLingLiaoService.save(businessChuKuLingLiao);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条领料单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条领料单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入领料单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入领料单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:chuku:lingliao:businessChuKuLingLiao:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "领料单数据导入模板.xlsx";
    		List<BusinessChuKuLingLiao> list = Lists.newArrayList(); 
    		new ExportExcel("领料单数据", BusinessChuKuLingLiao.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}