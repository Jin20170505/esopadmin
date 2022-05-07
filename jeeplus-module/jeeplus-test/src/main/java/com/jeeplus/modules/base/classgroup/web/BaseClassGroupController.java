/**
 *
 */
package com.jeeplus.modules.base.classgroup.web;

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
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroup;
import com.jeeplus.modules.base.classgroup.service.BaseClassGroupService;

/**
 * 班组管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/classgroup/baseClassGroup")
public class BaseClassGroupController extends BaseController {

	@Autowired
	private BaseClassGroupService baseClassGroupService;
	
	@ModelAttribute
	public BaseClassGroup get(@RequestParam(required=false) String id) {
		BaseClassGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseClassGroupService.get(id);
		}
		if (entity == null){
			entity = new BaseClassGroup();
		}
		return entity;
	}
	
	/**
	 * 班组列表页面
	 */
	@RequiresPermissions("base:classgroup:baseClassGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseClassGroup baseClassGroup, Model model) {
		model.addAttribute("baseClassGroup", baseClassGroup);
		return "modules/base/classgroup/baseClassGroupList";
	}
	
		/**
	 * 班组列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseClassGroup baseClassGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseClassGroup> page = baseClassGroupService.findPage(new Page<BaseClassGroup>(request, response), baseClassGroup); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑班组表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:classgroup:baseClassGroup:view","base:classgroup:baseClassGroup:add","base:classgroup:baseClassGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseClassGroup baseClassGroup, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseClassGroup", baseClassGroup);
		return "modules/base/classgroup/baseClassGroupForm";
	}

	/**
	 * 保存班组
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:classgroup:baseClassGroup:add","base:classgroup:baseClassGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseClassGroup baseClassGroup, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseClassGroup);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseClassGroupService.save(baseClassGroup);//保存
		j.setSuccess(true);
		j.setMsg("保存班组成功");
		return j;
	}

	
	/**
	 * 批量删除班组
	 */
	@ResponseBody
	@RequiresPermissions("base:classgroup:baseClassGroup:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseClassGroupService.delete(baseClassGroupService.get(id));
		}
		j.setMsg("删除班组成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:classgroup:baseClassGroup:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseClassGroup baseClassGroup, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "班组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseClassGroup> page = baseClassGroupService.findPage(new Page<BaseClassGroup>(request, response, -1), baseClassGroup);
    		new ExportExcel("班组", BaseClassGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出班组记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:classgroup:baseClassGroup:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseClassGroup> list = ei.getDataList(BaseClassGroup.class);
			for (BaseClassGroup baseClassGroup : list){
				try{
					baseClassGroupService.save(baseClassGroup);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条班组记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条班组记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入班组失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入班组数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:classgroup:baseClassGroup:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "班组数据导入模板.xlsx";
    		List<BaseClassGroup> list = Lists.newArrayList(); 
    		new ExportExcel("班组数据", BaseClassGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}