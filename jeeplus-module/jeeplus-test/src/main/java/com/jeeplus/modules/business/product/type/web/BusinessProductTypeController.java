/**
 *
 */
package com.jeeplus.modules.business.product.type.web;

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
import com.jeeplus.modules.business.product.type.entity.BusinessProductType;
import com.jeeplus.modules.business.product.type.service.BusinessProductTypeService;

/**
 * 存货分类Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/product/type/businessProductType")
public class BusinessProductTypeController extends BaseController {

	@Autowired
	private BusinessProductTypeService businessProductTypeService;
	
	@ModelAttribute
	public BusinessProductType get(@RequestParam(required=false) String id) {
		BusinessProductType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessProductTypeService.get(id);
		}
		if (entity == null){
			entity = new BusinessProductType();
		}
		return entity;
	}
	
	/**
	 * 存货分类树表页面
	 */
	@RequiresPermissions("business:product:type:businessProductType:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/business/product/type/businessProductTypeList";
	}

	/**
	 * 存货分类树表数据
	 */
	@ResponseBody
	@RequestMapping(value = "data")
	public List<BusinessProductType> data(BusinessProductType businessProductType) {
		return businessProductTypeService.findList(businessProductType);
	}

	/**
	 * 查看，增加，编辑存货分类表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequiresPermissions(value={"business:product:type:businessProductType:view","business:product:type:businessProductType:add","business:product:type:businessProductType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessProductType businessProductType, Model model) {
		if (businessProductType.getParent()!=null && StringUtils.isNotBlank(businessProductType.getParent().getId())){
			businessProductType.setParent(businessProductTypeService.get(businessProductType.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(businessProductType.getId())){
				BusinessProductType businessProductTypeChild = new BusinessProductType();
				businessProductTypeChild.setParent(new BusinessProductType(businessProductType.getParent().getId()));
				List<BusinessProductType> list = businessProductTypeService.findList(businessProductType); 
				if (list.size() > 0){
					businessProductType.setSort(list.get(list.size()-1).getSort());
					if (businessProductType.getSort() != null){
						businessProductType.setSort(businessProductType.getSort() + 30);
					}
				}
			}
		}
		if (businessProductType.getSort() == null){
			businessProductType.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("businessProductType", businessProductType);
		return "modules/business/product/type/businessProductTypeForm";
	}

	/**
	 * 保存存货分类
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:product:type:businessProductType:add","business:product:type:businessProductType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessProductType businessProductType, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessProductType);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		businessProductTypeService.save(businessProductType);//保存
		j.setSuccess(true);
		j.put("businessProductType", businessProductType);
		j.setMsg("保存存货分类成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<BusinessProductType> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return businessProductTypeService.getChildren(parentId);
	}
	
	/**
	 * 删除存货分类
	 */
	@ResponseBody
	@RequiresPermissions("business:product:type:businessProductType:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BusinessProductType businessProductType) {
		AjaxJson j = new AjaxJson();
		businessProductTypeService.delete(businessProductType);
		j.setSuccess(true);
		j.setMsg("删除存货分类成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BusinessProductType> list = businessProductTypeService.findList(new BusinessProductType());
		for (int i=0; i<list.size(); i++){
			BusinessProductType e = list.get(i);
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