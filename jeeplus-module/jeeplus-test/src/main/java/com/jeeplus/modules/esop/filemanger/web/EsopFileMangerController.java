/**
 *
 */
package com.jeeplus.modules.esop.filemanger.web;

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
import com.jeeplus.modules.esop.filemanger.entity.EsopFileManger;
import com.jeeplus.modules.esop.filemanger.service.EsopFileMangerService;

/**
 * ESOP文件管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/esop/filemanger/esopFileManger")
public class EsopFileMangerController extends BaseController {

	@Autowired
	private EsopFileMangerService esopFileMangerService;
	
	@ModelAttribute
	public EsopFileManger get(@RequestParam(required=false) String id) {
		EsopFileManger entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = esopFileMangerService.get(id);
		}
		if (entity == null){
			entity = new EsopFileManger();
		}
		return entity;
	}
	
	/**
	 * ESOP文件列表页面
	 */
	@RequiresPermissions("esop:filemanger:esopFileManger:list")
	@RequestMapping(value = {"list", ""})
	public String list(EsopFileManger esopFileManger, Model model) {
		model.addAttribute("esopFileManger", esopFileManger);
		return "modules/esop/filemanger/esopFileMangerList";
	}
	
		/**
	 * ESOP文件列表数据
	 */
	@ResponseBody
	@RequiresPermissions("esop:filemanger:esopFileManger:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(EsopFileManger esopFileManger, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EsopFileManger> page = esopFileMangerService.findPage(new Page<EsopFileManger>(request, response), esopFileManger); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑ESOP文件表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"esop:filemanger:esopFileManger:view","esop:filemanger:esopFileManger:add","esop:filemanger:esopFileManger:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, EsopFileManger esopFileManger, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("esopFileManger", esopFileManger);
		return "modules/esop/filemanger/esopFileMangerForm";
	}

	/**
	 * 保存ESOP文件
	 */
	@ResponseBody
	@RequiresPermissions(value={"esop:filemanger:esopFileManger:add","esop:filemanger:esopFileManger:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(EsopFileManger esopFileManger, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(esopFileManger);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		esopFileMangerService.save(esopFileManger);//保存
		j.setSuccess(true);
		j.setMsg("保存ESOP文件成功");
		return j;
	}

	
	/**
	 * 批量删除ESOP文件
	 */
	@ResponseBody
	@RequiresPermissions("esop:filemanger:esopFileManger:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			esopFileMangerService.delete(esopFileMangerService.get(id));
		}
		j.setMsg("删除ESOP文件成功");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "huishou")
	public AjaxJson huishou(String ids) {
		AjaxJson j = new AjaxJson();
		esopFileMangerService.xiafaOrhuishou(ids,"已保存");
		j.setMsg("操作成功");
		return j;
	}

	@ResponseBody
	@RequestMapping(value = "xiafa")
	public AjaxJson xiafa(String ids) {
		AjaxJson j = new AjaxJson();
		esopFileMangerService.xiafaOrhuishou(ids,"下发");
		j.setMsg("操作成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("esop:filemanger:esopFileManger:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(EsopFileManger esopFileManger, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ESOP文件"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EsopFileManger> page = esopFileMangerService.findPage(new Page<EsopFileManger>(request, response, -1), esopFileManger);
    		new ExportExcel("ESOP文件", EsopFileManger.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出ESOP文件记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("esop:filemanger:esopFileManger:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EsopFileManger> list = ei.getDataList(EsopFileManger.class);
			for (EsopFileManger esopFileManger : list){
				try{
					esopFileMangerService.save(esopFileManger);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条ESOP文件记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条ESOP文件记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入ESOP文件失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入ESOP文件数据模板
	 */
	@ResponseBody
	@RequiresPermissions("esop:filemanger:esopFileManger:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "ESOP文件数据导入模板.xlsx";
    		List<EsopFileManger> list = Lists.newArrayList(); 
    		new ExportExcel("ESOP文件数据", EsopFileManger.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}