/**
 *
 */
package com.jeeplus.modules.base.productionline.web;

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
import com.jeeplus.modules.base.productionline.entity.BaseProductionLine;
import com.jeeplus.modules.base.productionline.service.BaseProductionLineService;

/**
 * 产线管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/productionline/baseProductionLine")
public class BaseProductionLineController extends BaseController {

	@Autowired
	private BaseProductionLineService baseProductionLineService;
	
	@ModelAttribute
	public BaseProductionLine get(@RequestParam(required=false) String id) {
		BaseProductionLine entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseProductionLineService.get(id);
		}
		if (entity == null){
			entity = new BaseProductionLine();
		}
		return entity;
	}
	
	/**
	 * 产线列表页面
	 */
	@RequiresPermissions("base:productionline:baseProductionLine:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseProductionLine baseProductionLine, Model model) {
		model.addAttribute("baseProductionLine", baseProductionLine);
		return "modules/base/productionline/baseProductionLineList";
	}
	
		/**
	 * 产线列表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseProductionLine baseProductionLine, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseProductionLine> page = baseProductionLineService.findPage(new Page<BaseProductionLine>(request, response), baseProductionLine); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑产线表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:productionline:baseProductionLine:view","base:productionline:baseProductionLine:add","base:productionline:baseProductionLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseProductionLine baseProductionLine, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseProductionLine", baseProductionLine);
		return "modules/base/productionline/baseProductionLineForm";
	}

	/**
	 * 保存产线
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:productionline:baseProductionLine:add","base:productionline:baseProductionLine:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseProductionLine baseProductionLine, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseProductionLine);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseProductionLineService.save(baseProductionLine);//保存
		j.setSuccess(true);
		j.setMsg("保存产线成功");
		return j;
	}

	
	/**
	 * 批量删除产线
	 */
	@ResponseBody
	@RequiresPermissions("base:productionline:baseProductionLine:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseProductionLineService.delete(baseProductionLineService.get(id));
		}
		j.setMsg("删除产线成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:productionline:baseProductionLine:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseProductionLine baseProductionLine, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产线"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseProductionLine> page = baseProductionLineService.findPage(new Page<BaseProductionLine>(request, response, -1), baseProductionLine);
    		new ExportExcel("产线", BaseProductionLine.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出产线记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:productionline:baseProductionLine:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseProductionLine> list = ei.getDataList(BaseProductionLine.class);
			for (BaseProductionLine baseProductionLine : list){
				try{
					baseProductionLineService.save(baseProductionLine);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条产线记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条产线记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入产线失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入产线数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:productionline:baseProductionLine:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "产线数据导入模板.xlsx";
    		List<BaseProductionLine> list = Lists.newArrayList(); 
    		new ExportExcel("产线数据", BaseProductionLine.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}