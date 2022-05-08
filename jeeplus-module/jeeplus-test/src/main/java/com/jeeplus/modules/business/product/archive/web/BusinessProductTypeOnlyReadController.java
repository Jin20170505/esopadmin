/**
 * 
 */
package com.jeeplus.modules.business.product.archive.web;

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
import com.jeeplus.modules.business.product.archive.entity.BusinessProductTypeOnlyRead;
import com.jeeplus.modules.business.product.archive.service.BusinessProductTypeOnlyReadService;

/**
 * 存货档案Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/product/archive/businessProductTypeOnlyRead")
public class BusinessProductTypeOnlyReadController extends BaseController {

	@Autowired
	private BusinessProductTypeOnlyReadService businessProductTypeOnlyReadService;
	
	@ModelAttribute
	public BusinessProductTypeOnlyRead get(@RequestParam(required=false) String id) {
		BusinessProductTypeOnlyRead entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessProductTypeOnlyReadService.get(id);
		}
		if (entity == null){
			entity = new BusinessProductTypeOnlyRead();
		}
		return entity;
	}
	
	/**
	 * 存货档案列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(BusinessProductTypeOnlyRead businessProductTypeOnlyRead,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/business/product/archive/businessProductTypeOnlyReadList";
	}

	/**
	 * 查看，增加，编辑存货档案表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessProductTypeOnlyRead businessProductTypeOnlyRead, Model model) {
		if (businessProductTypeOnlyRead.getParent()!=null && StringUtils.isNotBlank(businessProductTypeOnlyRead.getParent().getId())){
			businessProductTypeOnlyRead.setParent(businessProductTypeOnlyReadService.get(businessProductTypeOnlyRead.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(businessProductTypeOnlyRead.getId())){
				BusinessProductTypeOnlyRead businessProductTypeOnlyReadChild = new BusinessProductTypeOnlyRead();
				businessProductTypeOnlyReadChild.setParent(new BusinessProductTypeOnlyRead(businessProductTypeOnlyRead.getParent().getId()));
				List<BusinessProductTypeOnlyRead> list = businessProductTypeOnlyReadService.findList(businessProductTypeOnlyRead); 
				if (list.size() > 0){
					businessProductTypeOnlyRead.setSort(list.get(list.size()-1).getSort());
					if (businessProductTypeOnlyRead.getSort() != null){
						businessProductTypeOnlyRead.setSort(businessProductTypeOnlyRead.getSort() + 30);
					}
				}
			}
		}
		if (businessProductTypeOnlyRead.getSort() == null){
			businessProductTypeOnlyRead.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("businessProductTypeOnlyRead", businessProductTypeOnlyRead);
		return "modules/business/product/archive/businessProductTypeOnlyReadForm";
	}

	/**
	 * 保存存货档案
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessProductTypeOnlyRead businessProductTypeOnlyRead, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessProductTypeOnlyRead);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		businessProductTypeOnlyReadService.save(businessProductTypeOnlyRead);//保存
		j.setSuccess(true);
		j.put("businessProductTypeOnlyRead", businessProductTypeOnlyRead);
		j.setMsg("保存存货档案成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<BusinessProductTypeOnlyRead> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return businessProductTypeOnlyReadService.getChildren(parentId);
	}
	
	/**
	 * 删除存货档案
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(BusinessProductTypeOnlyRead businessProductTypeOnlyRead) {
		AjaxJson j = new AjaxJson();
		businessProductTypeOnlyReadService.delete(businessProductTypeOnlyRead);
		j.setSuccess(true);
		j.setMsg("删除存货档案成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BusinessProductTypeOnlyRead> list = businessProductTypeOnlyReadService.findList(new BusinessProductTypeOnlyRead());
		for (int i=0; i<list.size(); i++){
			BusinessProductTypeOnlyRead e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("text", e.getName()+"("+e.getCode()+")");
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