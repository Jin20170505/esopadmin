/**
 *
 */
package com.jeeplus.modules.base.cangku.web;

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
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.service.BaseCangKuService;

/**
 * 仓库档案Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/cangku/baseCangKu")
public class BaseCangKuController extends BaseController {

	@Autowired
	private BaseCangKuService baseCangKuService;
	
	@ModelAttribute
	public BaseCangKu get(@RequestParam(required=false) String id) {
		BaseCangKu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseCangKuService.get(id);
		}
		if (entity == null){
			entity = new BaseCangKu();
		}
		return entity;
	}
	
	/**
	 * 仓库列表页面
	 */
	@RequiresPermissions("base:cangku:baseCangKu:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseCangKu baseCangKu, Model model) {
		model.addAttribute("baseCangKu", baseCangKu);
		return "modules/base/cangku/baseCangKuList";
	}
	
		/**
	 * 仓库列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCangKu:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseCangKu baseCangKu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseCangKu> page = baseCangKuService.findPage(new Page<BaseCangKu>(request, response), baseCangKu); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑仓库表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:cangku:baseCangKu:view","base:cangku:baseCangKu:add","base:cangku:baseCangKu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseCangKu baseCangKu, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseCangKu", baseCangKu);
		return "modules/base/cangku/baseCangKuForm";
	}

	/**
	 * 保存仓库
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:cangku:baseCangKu:add","base:cangku:baseCangKu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseCangKu baseCangKu, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseCangKu);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseCangKuService.save(baseCangKu);//保存
		j.setSuccess(true);
		j.setMsg("保存仓库成功");
		return j;
	}

	
	/**
	 * 批量删除仓库
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCangKu:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseCangKuService.delete(baseCangKuService.get(id));
		}
		j.setMsg("删除仓库成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCangKu:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseCangKu baseCangKu, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "仓库"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseCangKu> page = baseCangKuService.findPage(new Page<BaseCangKu>(request, response, -1), baseCangKu);
    		new ExportExcel("仓库", BaseCangKu.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出仓库记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCangKu:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseCangKu> list = ei.getDataList(BaseCangKu.class);
			for (BaseCangKu baseCangKu : list){
				try{
					baseCangKuService.save(baseCangKu);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条仓库记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条仓库记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入仓库失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入仓库数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCangKu:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "仓库数据导入模板.xlsx";
    		List<BaseCangKu> list = Lists.newArrayList(); 
    		new ExportExcel("仓库数据", BaseCangKu.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}