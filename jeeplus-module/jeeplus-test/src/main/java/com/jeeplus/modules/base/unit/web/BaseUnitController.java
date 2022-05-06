/**
 *
 */
package com.jeeplus.modules.base.unit.web;

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
import com.jeeplus.modules.base.unit.entity.BaseUnit;
import com.jeeplus.modules.base.unit.service.BaseUnitService;

/**
 * 计量单位Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/unit/baseUnit")
public class BaseUnitController extends BaseController {

	@Autowired
	private BaseUnitService baseUnitService;
	
	@ModelAttribute
	public BaseUnit get(@RequestParam(required=false) String id) {
		BaseUnit entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseUnitService.get(id);
		}
		if (entity == null){
			entity = new BaseUnit();
		}
		return entity;
	}
	
	/**
	 * 计量单位列表页面
	 */
	@RequiresPermissions("base:unit:baseUnit:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseUnit baseUnit, Model model) {
		model.addAttribute("baseUnit", baseUnit);
		return "modules/base/unit/baseUnitList";
	}
	
		/**
	 * 计量单位列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:unit:baseUnit:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseUnit baseUnit, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseUnit> page = baseUnitService.findPage(new Page<BaseUnit>(request, response), baseUnit); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑计量单位表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:unit:baseUnit:view","base:unit:baseUnit:add","base:unit:baseUnit:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseUnit baseUnit, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseUnit", baseUnit);
		return "modules/base/unit/baseUnitForm";
	}

	/**
	 * 保存计量单位
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:unit:baseUnit:add","base:unit:baseUnit:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseUnit baseUnit, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseUnit);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseUnitService.save(baseUnit);//保存
		j.setSuccess(true);
		j.setMsg("保存计量单位成功");
		return j;
	}

	
	/**
	 * 批量删除计量单位
	 */
	@ResponseBody
	@RequiresPermissions("base:unit:baseUnit:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseUnitService.delete(baseUnitService.get(id));
		}
		j.setMsg("删除计量单位成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:unit:baseUnit:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseUnit baseUnit, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "计量单位"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseUnit> page = baseUnitService.findPage(new Page<BaseUnit>(request, response, -1), baseUnit);
    		new ExportExcel("计量单位", BaseUnit.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出计量单位记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:unit:baseUnit:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseUnit> list = ei.getDataList(BaseUnit.class);
			for (BaseUnit baseUnit : list){
				try{
					baseUnitService.save(baseUnit);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条计量单位记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条计量单位记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入计量单位失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入计量单位数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:unit:baseUnit:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "计量单位数据导入模板.xlsx";
    		List<BaseUnit> list = Lists.newArrayList(); 
    		new ExportExcel("计量单位数据", BaseUnit.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}