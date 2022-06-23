/**
 * 
 */
package com.jeeplus.modules.business.faliao.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
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
import com.jeeplus.modules.business.faliao.entity.BusinessFaLiao;
import com.jeeplus.modules.business.faliao.service.BusinessFaLiaoService;

/**
 * 调拨单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/faliao/businessFaLiao")
public class BusinessFaLiaoController extends BaseController {

	@Autowired
	private BusinessFaLiaoService businessFaLiaoService;
	
	@ModelAttribute
	public BusinessFaLiao get(@RequestParam(required=false) String id) {
		BusinessFaLiao entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessFaLiaoService.get(id);
		}
		if (entity == null){
			entity = new BusinessFaLiao();
		}
		return entity;
	}
	@RequestMapping("u8in")
	@ResponseBody
	public AjaxJson u8ruku(String rids,String codes){
		AjaxJson json = new AjaxJson();
		List<String> ids = Arrays.asList(rids.split(","));
		String[] codearr = codes.split(",");
		StringBuilder sb = new StringBuilder();
		int i =0;
		for (String id:ids){
			try{
				businessFaLiaoService.u8in(id);
			}catch (Exception e){
				e.printStackTrace();
				sb.append(codearr[i]).append(":").append(e.getMessage()).append("\n");
			}
			i++;
		}
		if(sb.length()>0){
			json.setMsg("同步未成功单号及原因："+sb.toString());
			json.setSuccess(false);
		}else {
			json.setMsg("同步成功");
			json.setSuccess(true);
		}
		return json;
	}
	/**
	 * 调拨单列表页面
	 */
	@RequiresPermissions("business:faliao:businessFaLiao:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessFaLiao businessFaLiao, Model model) {
		model.addAttribute("businessFaLiao", businessFaLiao);
		return "modules/business/faliao/businessFaLiaoList";
	}
	
		/**
	 * 调拨单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:faliao:businessFaLiao:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessFaLiao businessFaLiao, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessFaLiao> page = businessFaLiaoService.findPage(new Page<BusinessFaLiao>(request, response), businessFaLiao); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑调拨单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:faliao:businessFaLiao:view","business:faliao:businessFaLiao:add","business:faliao:businessFaLiao:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessFaLiao businessFaLiao, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessFaLiao", businessFaLiao);
		return "modules/business/faliao/businessFaLiaoForm";
	}

	/**
	 * 保存调拨单
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:faliao:businessFaLiao:add","business:faliao:businessFaLiao:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessFaLiao businessFaLiao, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessFaLiao);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessFaLiaoService.save(businessFaLiao);//保存
		j.setSuccess(true);
		j.setMsg("保存调拨单成功");
		return j;
	}

	
	/**
	 * 批量删除调拨单
	 */
	@ResponseBody
	@RequiresPermissions("business:faliao:businessFaLiao:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessFaLiaoService.delete(businessFaLiaoService.get(id));
		}
		j.setMsg("删除调拨单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:faliao:businessFaLiao:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessFaLiao businessFaLiao, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "调拨单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessFaLiao> page = businessFaLiaoService.findPage(new Page<BusinessFaLiao>(request, response, -1), businessFaLiao);
    		new ExportExcel("调拨单", BusinessFaLiao.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出调拨单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BusinessFaLiao detail(String id) {
		return businessFaLiaoService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:faliao:businessFaLiao:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessFaLiao> list = ei.getDataList(BusinessFaLiao.class);
			for (BusinessFaLiao businessFaLiao : list){
				try{
					businessFaLiaoService.save(businessFaLiao);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条调拨单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条调拨单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入调拨单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入调拨单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:faliao:businessFaLiao:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "调拨单数据导入模板.xlsx";
    		List<BusinessFaLiao> list = Lists.newArrayList(); 
    		new ExportExcel("调拨单数据", BusinessFaLiao.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}