/**
 *
 */
package com.jeeplus.modules.business.filemanger.type.web;

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
import com.jeeplus.modules.business.filemanger.type.entity.BusinessFileTypeWrite;
import com.jeeplus.modules.business.filemanger.type.service.BusinessFileTypeWriteService;

/**
 * 文件类型Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/business/filemanger/type/businessFileTypeWrite")
public class BusinessFileTypeWriteController extends BaseController {

	@Autowired
	private BusinessFileTypeWriteService businessFileTypeWriteService;
	
	@ModelAttribute
	public BusinessFileTypeWrite get(@RequestParam(required=false) String id) {
		BusinessFileTypeWrite entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = businessFileTypeWriteService.get(id);
		}
		if (entity == null){
			entity = new BusinessFileTypeWrite();
		}
		return entity;
	}
	
	/**
	 * 文件类型树表页面
	 */
	@RequiresPermissions("business:filemanger:type:businessFileTypeWrite:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "modules/business/filemanger/type/businessFileTypeWriteList";
	}

	/**
	 * 文件类型树表数据
	 */
	@RequiresPermissions("business:filemanger:type:businessFileTypeWrite:list")
	@ResponseBody
	@RequestMapping(value = "data")
	public List<BusinessFileTypeWrite> data(BusinessFileTypeWrite businessFileTypeWrite) {
		return businessFileTypeWriteService.findList(businessFileTypeWrite);
	}

	/**
	 * 查看，增加，编辑文件类型表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequiresPermissions(value={"business:filemanger:type:businessFileTypeWrite:view","business:filemanger:type:businessFileTypeWrite:add","business:filemanger:type:businessFileTypeWrite:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, BusinessFileTypeWrite businessFileTypeWrite, Model model) {
		if (businessFileTypeWrite.getParent()!=null && StringUtils.isNotBlank(businessFileTypeWrite.getParent().getId())){
			businessFileTypeWrite.setParent(businessFileTypeWriteService.get(businessFileTypeWrite.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(businessFileTypeWrite.getId())){
				BusinessFileTypeWrite businessFileTypeWriteChild = new BusinessFileTypeWrite();
				businessFileTypeWriteChild.setParent(new BusinessFileTypeWrite(businessFileTypeWrite.getParent().getId()));
				List<BusinessFileTypeWrite> list = businessFileTypeWriteService.findList(businessFileTypeWrite); 
				if (list.size() > 0){
					businessFileTypeWrite.setSort(list.get(list.size()-1).getSort());
					if (businessFileTypeWrite.getSort() != null){
						businessFileTypeWrite.setSort(businessFileTypeWrite.getSort() + 30);
					}
				}
			}
		}
		if (businessFileTypeWrite.getSort() == null){
			businessFileTypeWrite.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("businessFileTypeWrite", businessFileTypeWrite);
		return "modules/business/filemanger/type/businessFileTypeWriteForm";
	}

	/**
	 * 保存文件类型
	 */
	@ResponseBody
	@RequiresPermissions(value={"business:filemanger:type:businessFileTypeWrite:add","business:filemanger:type:businessFileTypeWrite:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BusinessFileTypeWrite businessFileTypeWrite, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(businessFileTypeWrite);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		businessFileTypeWriteService.save(businessFileTypeWrite);//保存
		j.setSuccess(true);
		j.put("businessFileTypeWrite", businessFileTypeWrite);
		j.setMsg("保存文件类型成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<BusinessFileTypeWrite> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return businessFileTypeWriteService.getChildren(parentId);
	}
	
	/**
	 * 删除文件类型
	 */
	@ResponseBody
	@RequiresPermissions("business:filemanger:type:businessFileTypeWrite:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BusinessFileTypeWrite businessFileTypeWrite) {
		AjaxJson j = new AjaxJson();
		businessFileTypeWriteService.delete(businessFileTypeWrite);
		j.setSuccess(true);
		j.setMsg("删除文件类型成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<BusinessFileTypeWrite> list = businessFileTypeWriteService.findList(new BusinessFileTypeWrite());
		for (int i=0; i<list.size(); i++){
			BusinessFileTypeWrite e = list.get(i);
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