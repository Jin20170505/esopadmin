/**
 *
 */
package com.jeeplus.modules.base.site.hegelv.web;

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
import com.jeeplus.modules.base.site.hegelv.entity.BaseSiteHegelv;
import com.jeeplus.modules.base.site.hegelv.service.BaseSiteHegelvService;

/**
 * 工序合格率Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/site/hegelv/baseSiteHegelv")
public class BaseSiteHegelvController extends BaseController {

	@Autowired
	private BaseSiteHegelvService baseSiteHegelvService;
	
	@ModelAttribute
	public BaseSiteHegelv get(@RequestParam(required=false) String id) {
		BaseSiteHegelv entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseSiteHegelvService.get(id);
		}
		if (entity == null){
			entity = new BaseSiteHegelv();
		}
		return entity;
	}
	
	/**
	 * 工序合格率列表页面
	 */
	@RequiresPermissions("base:site:hegelv:baseSiteHegelv:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseSiteHegelv baseSiteHegelv, Model model) {
		model.addAttribute("baseSiteHegelv", baseSiteHegelv);
		return "modules/base/site/hegelv/baseSiteHegelvList";
	}
	
		/**
	 * 工序合格率列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:site:hegelv:baseSiteHegelv:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseSiteHegelv baseSiteHegelv, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseSiteHegelv> page = baseSiteHegelvService.findPage(new Page<BaseSiteHegelv>(request, response), baseSiteHegelv); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑工序合格率表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:site:hegelv:baseSiteHegelv:view","base:site:hegelv:baseSiteHegelv:add","base:site:hegelv:baseSiteHegelv:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseSiteHegelv baseSiteHegelv, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseSiteHegelv", baseSiteHegelv);
		return "modules/base/site/hegelv/baseSiteHegelvForm";
	}

	/**
	 * 保存工序合格率
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:site:hegelv:baseSiteHegelv:add","base:site:hegelv:baseSiteHegelv:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseSiteHegelv baseSiteHegelv, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseSiteHegelv);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseSiteHegelvService.save(baseSiteHegelv);//保存
		j.setSuccess(true);
		j.setMsg("保存工序合格率成功");
		return j;
	}

	
	/**
	 * 批量删除工序合格率
	 */
	@ResponseBody
	@RequiresPermissions("base:site:hegelv:baseSiteHegelv:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseSiteHegelvService.delete(baseSiteHegelvService.get(id));
		}
		j.setMsg("删除工序合格率成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:site:hegelv:baseSiteHegelv:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseSiteHegelv baseSiteHegelv, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工序合格率"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseSiteHegelv> page = baseSiteHegelvService.findPage(new Page<BaseSiteHegelv>(request, response, -1), baseSiteHegelv);
    		new ExportExcel("工序合格率", BaseSiteHegelv.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出工序合格率记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:site:hegelv:baseSiteHegelv:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseSiteHegelv> list = ei.getDataList(BaseSiteHegelv.class);
			for (BaseSiteHegelv baseSiteHegelv : list){
				try{
					baseSiteHegelvService.save(baseSiteHegelv);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条工序合格率记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条工序合格率记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入工序合格率失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入工序合格率数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:site:hegelv:baseSiteHegelv:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "工序合格率数据导入模板.xlsx";
    		List<BaseSiteHegelv> list = Lists.newArrayList(); 
    		new ExportExcel("工序合格率数据", BaseSiteHegelv.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}