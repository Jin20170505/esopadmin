/**
 *
 */
package com.jeeplus.modules.qiyewx.base.web;

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
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxDept;
import com.jeeplus.modules.qiyewx.base.service.QiYeWxDeptService;

/**
 * 部门员工Controller
 * @author Jin
 * @version 2021-08-25
 */
@Controller
@RequestMapping(value = "${adminPath}/qiyewx/base/qiYeWxDept")
public class QiYeWxDeptController extends BaseController {

	@Autowired
	private QiYeWxDeptService qiYeWxDeptService;
	
	@ModelAttribute
	public QiYeWxDept get(@RequestParam(required=false) String id) {
		QiYeWxDept entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = qiYeWxDeptService.get(id);
		}
		if (entity == null){
			entity = new QiYeWxDept();
		}
		return entity;
	}
	
	/**
	 * 部门员工列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(QiYeWxDept qiYeWxDept,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/qiyewx/base/qiYeWxDeptList";
	}

	/**
	 * 查看，增加，编辑部门员工表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, QiYeWxDept qiYeWxDept, Model model) {
		if (qiYeWxDept.getParent()!=null && StringUtils.isNotBlank(qiYeWxDept.getParent().getId())){
			qiYeWxDept.setParent(qiYeWxDeptService.get(qiYeWxDept.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(qiYeWxDept.getId())){
				QiYeWxDept qiYeWxDeptChild = new QiYeWxDept();
				qiYeWxDeptChild.setParent(new QiYeWxDept(qiYeWxDept.getParent().getId()));
				List<QiYeWxDept> list = qiYeWxDeptService.findList(qiYeWxDept); 
				if (list.size() > 0){
					qiYeWxDept.setSort(list.get(list.size()-1).getSort());
					if (qiYeWxDept.getSort() != null){
						qiYeWxDept.setSort(qiYeWxDept.getSort() + 30);
					}
				}
			}
		}
		if (qiYeWxDept.getSort() == null){
			qiYeWxDept.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("qiYeWxDept", qiYeWxDept);
		return "modules/qiyewx/base/qiYeWxDeptForm";
	}

	/**
	 * 保存部门员工
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(QiYeWxDept qiYeWxDept, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(qiYeWxDept);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		qiYeWxDeptService.save(qiYeWxDept);//保存
		j.setSuccess(true);
		j.put("qiYeWxDept", qiYeWxDept);
		j.setMsg("保存部门员工成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<QiYeWxDept> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return qiYeWxDeptService.getChildren(parentId);
	}
	
	/**
	 * 删除部门员工
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(QiYeWxDept qiYeWxDept) {
		AjaxJson j = new AjaxJson();
		qiYeWxDeptService.delete(qiYeWxDept);
		j.setSuccess(true);
		j.setMsg("删除部门员工成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<QiYeWxDept> list = qiYeWxDeptService.findList(new QiYeWxDept());
		for (int i=0; i<list.size(); i++){
			QiYeWxDept e = list.get(i);
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