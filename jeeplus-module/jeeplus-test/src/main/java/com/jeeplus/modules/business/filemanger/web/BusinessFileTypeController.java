/**
 * 
 */
package com.jeeplus.modules.business.filemanger.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.filemanger.entity.BusinessFileType;
import com.jeeplus.modules.business.filemanger.service.BusinessFileTypeService;

/**
 * 文件管理Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/filemanger/businessFileType")
public class BusinessFileTypeController extends BaseController {

	@Autowired
	private BusinessFileTypeService businessFileTypeService;
	
	@ModelAttribute
	public BusinessFileType get(@RequestParam(required=false) String id) {
		BusinessFileType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessFileTypeService.get(id);
		}
		if (entity == null){
			entity = new BusinessFileType();
		}
		return entity;
	}
	
	/**
	 * 文件列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BusinessFileType businessFileType,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/business/filemanger/businessFileTypeList";
	}

	/**
	 * 查看，增加，编辑文件表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessFileType businessFileType, Model model) {
		if (businessFileType.getParent()!=null && StringUtils.isNotBlank(businessFileType.getParent().getId())){
			businessFileType.setParent(businessFileTypeService.get(businessFileType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(businessFileType.getId())){
				BusinessFileType businessFileTypeChild = new BusinessFileType();
				businessFileTypeChild.setParent(new BusinessFileType(businessFileType.getParent().getId()));
				List<BusinessFileType> list = businessFileTypeService.findList(businessFileType); 
				if (list.size() > 0){
					businessFileType.setSort(list.get(list.size()-1).getSort());
					if (businessFileType.getSort() != null){
						businessFileType.setSort(businessFileType.getSort() + 30);
					}
				}
			}
		}
		if (businessFileType.getSort() == null){
			businessFileType.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("businessFileType", businessFileType);
		return "modules/business/filemanger/businessFileTypeForm";
	}

	/**
	 * 保存文件
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessFileType businessFileType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessFileType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		businessFileTypeService.save(businessFileType);//保存
		j.setSuccess(true);
		j.put("businessFileType", businessFileType);
		j.setMsg("保存文件成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<BusinessFileType> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return businessFileTypeService.getChildren(parentId);
	}
	
	/**
	 * 删除文件
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(BusinessFileType businessFileType) {
		AjaxJson j = new AjaxJson();
		businessFileTypeService.delete(businessFileType);
		j.setSuccess(true);
		j.setMsg("删除文件成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BusinessFileType> list = businessFileTypeService.findList(new BusinessFileType());
		for (int i=0; i<list.size(); i++){
			BusinessFileType e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName());
				if(StringUtils.isBlank(e.getParentId()) || "0".equals(e.getParentId())){
					map.put("parent", "#");
					Map<String, Object> state = Maps.newHashMap();
					state.put("opened", true);
					map.put("state", state);
				}else{
					map.put("parent", e.getParentId());
				}
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}