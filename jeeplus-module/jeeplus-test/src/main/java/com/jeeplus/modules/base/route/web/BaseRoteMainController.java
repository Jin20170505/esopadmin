/**
 * 
 */
package com.jeeplus.modules.base.route.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.jeeplus.modules.base.route.entity.BaseRoute;
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
import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.base.route.service.BaseRoteMainService;

/**
 * 工艺路线Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/route/baseRoteMain")
public class BaseRoteMainController extends BaseController {

	@Autowired
	private BaseRoteMainService baseRoteMainService;
	
	@ModelAttribute
	public BaseRoteMain get(@RequestParam(required=false) String id) {
		BaseRoteMain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseRoteMainService.get(id);
		}
		if (entity == null){
			entity = new BaseRoteMain();
		}
		return entity;
	}
	
	/**
	 * 工艺路线列表页面
	 */
	@RequiresPermissions("base:route:baseRoteMain:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseRoteMain baseRoteMain, Model model) {
		model.addAttribute("baseRoteMain", baseRoteMain);
		return "modules/base/route/baseRoteMainList";
	}

	@ResponseBody
	@RequestMapping("findVersion")
	public AjaxJson findVersion(String productid){
		AjaxJson json = new AjaxJson();
		try {
			json.put("versions",baseRoteMainService.findVersion(productid));
			json.setMsg("success");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("查询失败");
		}
		return json;
	}


	@ResponseBody
	@RequestMapping("getRouteVersionByCinvCode")
	public AjaxJson getRouteVersionByCinvCode(String cinvcode){
		AjaxJson json = new AjaxJson();
		try {
			json.put("version",baseRoteMainService.getRouteVersionByCinvCode(cinvcode));
			json.setMsg("success");
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
			json.setMsg("查询失败");
		}
		return json;
	}
		/**
	 * 工艺路线列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseRoteMain baseRoteMain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseRoteMain> page = baseRoteMainService.findPage(new Page<BaseRoteMain>(request, response), baseRoteMain); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工艺路线表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:route:baseRoteMain:view","base:route:baseRoteMain:add","base:route:baseRoteMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseRoteMain baseRoteMain, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseRoteMain", baseRoteMain);
		return "modules/base/route/baseRoteMainForm";
	}

	/**
	 * 保存工艺路线
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:route:baseRoteMain:add","base:route:baseRoteMain:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseRoteMain baseRoteMain, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseRoteMain);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseRoteMainService.save(baseRoteMain);//保存
		j.setSuccess(true);
		j.setMsg("保存工艺路线成功");
		return j;
	}

	@ResponseBody
	@RequestMapping("getRoutes")
	public AjaxJson getRoutes(String rid){
		AjaxJson json = new AjaxJson();
		try {
			json.put("routes",baseRoteMainService.getRoutes(rid));
			json.setSuccess(true);
		}catch (Exception e){
			e.printStackTrace();
			json.setSuccess(false);
		}
		return json;
	}
	
	/**
	 * 批量删除工艺路线
	 */
	@ResponseBody
	@RequiresPermissions("base:route:baseRoteMain:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseRoteMainService.delete(baseRoteMainService.get(id));
		}
		j.setMsg("删除工艺路线成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:route:baseRoteMain:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseRoteMain baseRoteMain, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工艺路线"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseRoteMain> page = baseRoteMainService.findPage(new Page<BaseRoteMain>(request, response, -1), baseRoteMain);
    		new ExportExcel("工艺路线", BaseRoteMain.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工艺路线记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public BaseRoteMain detail(String id) {
		return baseRoteMainService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:route:baseRoteMain:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseRoteMain> list = ei.getDataList(BaseRoteMain.class);
			for (BaseRoteMain baseRoteMain : list){
				try{
					baseRoteMainService.save(baseRoteMain);
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
	@RequiresPermissions("base:route:baseRoteMain:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工艺路线数据导入模板.xlsx";
    		List<BaseRoteMain> list = Lists.newArrayList(); 
    		new ExportExcel("工艺路线数据", BaseRoteMain.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}