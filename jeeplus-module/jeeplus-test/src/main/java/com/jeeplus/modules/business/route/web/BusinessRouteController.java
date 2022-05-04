/**
 *
 */
package com.jeeplus.modules.business.route.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
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
import com.jeeplus.modules.business.route.entity.BusinessRoute;
import com.jeeplus.modules.business.route.service.BusinessRouteService;

/**
 * 工艺路线Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/route/businessRoute")
public class BusinessRouteController extends BaseController {

	@Autowired
	private BusinessRouteService businessRouteService;
	
	@ModelAttribute
	public BusinessRoute get(@RequestParam(required=false) String id) {
		BusinessRoute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessRouteService.get(id);
		}
		if (entity == null){
			entity = new BusinessRoute();
		}
		return entity;
	}
	
	/**
	 * 工艺路线列表页面
	 */
	@RequiresPermissions("business:route:businessRoute:list")
	@RequestMapping(value = {"list", ""})
	public String list(BusinessRoute businessRoute, Model model) {
		model.addAttribute("businessRoute", businessRoute);
		return "modules/business/route/businessRouteList";
	}

	@ResponseBody
	@RequestMapping("findVersion")
	public AjaxJson findVersion(String productid){
		AjaxJson json = new AjaxJson();
		json.setMsg("success");
		json.setSuccess(true);
		json.put("versions",businessRouteService.findVersion(productid));
		return json;
	}

	@ResponseBody
	@RequestMapping("getRoutes")
	public AjaxJson getRoutes(String productId,String version){
		AjaxJson json = new AjaxJson();
		BusinessRoute route  = new BusinessRoute(new BusinessProduct(productId));
		route.setVersion(version);
		route.setStatus("0");
		json.put("routes",businessRouteService.findList(route));
		json.setSuccess(true);
		json.setMsg("success");
		return json;
	}

		/**
	 * 工艺路线列表数据
	 */
	@ResponseBody
	@RequiresPermissions("business:route:businessRoute:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BusinessRoute businessRoute, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BusinessRoute> page = businessRouteService.findPage(new Page<BusinessRoute>(request, response), businessRoute); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工艺路线表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"business:route:businessRoute:view","business:route:businessRoute:add","business:route:businessRoute:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessRoute businessRoute, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("businessRoute", businessRoute);
		return "modules/business/route/businessRouteForm";
	}

	/**
	 * 保存工艺路线
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:route:businessRoute:add","business:route:businessRoute:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessRoute businessRoute, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessRoute);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		businessRouteService.save(businessRoute);//保存
		j.setSuccess(true);
		j.setMsg("保存工艺路线成功");
		return j;
	}

	
	/**
	 * 批量删除工艺路线
	 */
	@ResponseBody
	@RequiresPermissions("business:route:businessRoute:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			businessRouteService.delete(businessRouteService.get(id));
		}
		j.setMsg("删除工艺路线成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("business:route:businessRoute:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BusinessRoute businessRoute, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工艺路线"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BusinessRoute> page = businessRouteService.findPage(new Page<BusinessRoute>(request, response, -1), businessRoute);
    		new ExportExcel("工艺路线", BusinessRoute.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工艺路线记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("business:route:businessRoute:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BusinessRoute> list = ei.getDataList(BusinessRoute.class);
			for (BusinessRoute businessRoute : list){
				try{
					businessRouteService.save(businessRoute);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工艺路线记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条工艺路线记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入工艺路线失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入工艺路线数据模板
	 */
	@ResponseBody
	@RequiresPermissions("business:route:businessRoute:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工艺路线数据导入模板.xlsx";
    		List<BusinessRoute> list = Lists.newArrayList(); 
    		new ExportExcel("工艺路线数据", BusinessRoute.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}