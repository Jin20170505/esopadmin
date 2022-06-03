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

import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
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
import com.jeeplus.modules.base.cangku.entity.BaseCkUser;
import com.jeeplus.modules.base.cangku.service.BaseCkUserService;

/**
 * 仓库可见人Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/base/cangku/baseCkUser")
public class BaseCkUserController extends BaseController {

	@Autowired
	private BaseCkUserService baseCkUserService;
	
	@ModelAttribute
	public BaseCkUser get(@RequestParam(required=false) String id) {
		BaseCkUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseCkUserService.get(id);
		}
		if (entity == null){
			entity = new BaseCkUser();
		}
		return entity;
	}
	
	/**
	 * 仓库可见人列表页面
	 */
	@RequiresPermissions("base:cangku:baseCkUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(BaseCkUser baseCkUser, Model model) {
		model.addAttribute("baseCkUser", baseCkUser);
		return "modules/base/cangku/baseCkUserList";
	}
	
		/**
	 * 仓库可见人列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCkUser:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseCkUser baseCkUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseCkUser> page = baseCkUserService.findPage(new Page<BaseCkUser>(request, response), baseCkUser); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑仓库可见人表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"base:cangku:baseCkUser:view","base:cangku:baseCkUser:add","base:cangku:baseCkUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BaseCkUser baseCkUser, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("baseCkUser", baseCkUser);
		return "modules/base/cangku/baseCkUserForm";
	}

	/**
	 * 保存仓库可见人
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:cangku:baseCkUser:add","base:cangku:baseCkUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseCkUser baseCkUser, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(baseCkUser);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		baseCkUserService.save(baseCkUser);//保存
		j.setSuccess(true);
		j.setMsg("保存仓库可见人成功");
		return j;
	}

	
	/**
	 * 批量删除仓库可见人
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCkUser:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseCkUserService.delete(baseCkUserService.get(id));
		}
		j.setMsg("删除仓库可见人成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCkUser:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(BaseCkUser baseCkUser, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "仓库可见人"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseCkUser> page = baseCkUserService.findPage(new Page<BaseCkUser>(request, response, -1), baseCkUser);
    		new ExportExcel("仓库可见人", BaseCkUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出仓库可见人记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	@Autowired
	private BaseCangKuMapper cangKuMapper;
	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCkUser:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseCkUser> list = ei.getDataList(BaseCkUser.class);
			for (BaseCkUser baseCkUser : list){
				try{
					baseCkUser.setCk(new BaseCangKu(cangKuMapper.getIdByCode(baseCkUser.getCkcode())));
					baseCkUserService.save(baseCkUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条仓库可见人记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条仓库可见人记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入仓库可见人失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入仓库可见人数据模板
	 */
	@ResponseBody
	@RequiresPermissions("base:cangku:baseCkUser:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "仓库可见人数据导入模板.xlsx";
    		List<BaseCkUser> list = Lists.newArrayList(); 
    		new ExportExcel("仓库可见人数据", BaseCkUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}