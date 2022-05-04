/**
 * 
 */
package com.jeeplus.modules.esop.manger.web;
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
import com.jeeplus.modules.esop.manger.entity.EsopManger;
import com.jeeplus.modules.esop.manger.service.EsopMangerService;

/**
 * ESOP工单Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/esop/manger/esopManger")
public class EsopMangerController extends BaseController {

	@Autowired
	private EsopMangerService esopMangerService;
	
	@ModelAttribute
	public EsopManger get(@RequestParam(required=false) String id) {
		EsopManger entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = esopMangerService.get(id);
		}
		if (entity == null){
			entity = new EsopManger();
		}
		return entity;
	}
	
	/**
	 * ESOP工单列表页面
	 */
	@RequiresPermissions("esop:manger:esopManger:list")
	@RequestMapping(value = {"list", ""})
	public String list(EsopManger esopManger, Model model) {
		model.addAttribute("esopManger", esopManger);
		return "modules/esop/manger/esopMangerList";
	}
	
		/**
	 * ESOP工单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("esop:manger:esopManger:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(EsopManger esopManger, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EsopManger> page = esopMangerService.findPage(new Page<EsopManger>(request, response), esopManger); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑ESOP工单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"esop:manger:esopManger:view","esop:manger:esopManger:add","esop:manger:esopManger:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, EsopManger esopManger, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("esopManger", esopManger);
		return "modules/esop/manger/esopMangerForm";
	}

	/**
	 * 保存ESOP工单
	 */
	@ResponseBody
	@RequiresPermissions(value={"esop:manger:esopManger:add","esop:manger:esopManger:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(EsopManger esopManger, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(esopManger);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		esopMangerService.save(esopManger);//保存
		j.setSuccess(true);
		j.setMsg("保存ESOP工单成功");
		return j;
	}

	
	/**
	 * 批量删除ESOP工单
	 */
	@ResponseBody
	@RequiresPermissions("esop:manger:esopManger:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			esopMangerService.delete(esopMangerService.get(id));
		}
		j.setMsg("删除ESOP工单成功");
		return j;
	}


	@ResponseBody
	@RequestMapping(value = "xiafa")
	public AjaxJson xiafa(String ids) {
		AjaxJson j = new AjaxJson();
		esopMangerService.updateStatus(ids,"已下发");
		j.setSuccess(true);
		j.setMsg("操作成功");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "shouhui")
	public AjaxJson shouhui(String ids) {
		AjaxJson j = new AjaxJson();
		esopMangerService.updateStatus(ids,"未下发");
		j.setSuccess(true);
		j.setMsg("操作成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("esop:manger:esopManger:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(EsopManger esopManger, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ESOP工单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EsopManger> page = esopMangerService.findPage(new Page<EsopManger>(request, response, -1), esopManger);
    		new ExportExcel("ESOP工单", EsopManger.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出ESOP工单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public EsopManger detail(String id) {
		return esopMangerService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("esop:manger:esopManger:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EsopManger> list = ei.getDataList(EsopManger.class);
			for (EsopManger esopManger : list){
				try{
					esopMangerService.save(esopManger);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条ESOP工单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条ESOP工单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入ESOP工单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入ESOP工单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("esop:manger:esopManger:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ESOP工单数据导入模板.xlsx";
    		List<EsopManger> list = Lists.newArrayList(); 
    		new ExportExcel("ESOP工单数据", EsopManger.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}