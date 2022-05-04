/**
 * 
 */
package com.jeeplus.modules.test.leftright.web;

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
import com.jeeplus.modules.test.leftright.entity.TestLeftTree;
import com.jeeplus.modules.test.leftright.service.TestLeftTreeService;

/**
 * 左树右表Controller
 * @author Jin
 */
@Controller
@RequestMapping(value = "${adminPath}/test/leftright/testLeftTree")
public class TestLeftTreeController extends BaseController {

	@Autowired
	private TestLeftTreeService testLeftTreeService;
	
	@ModelAttribute
	public TestLeftTree get(@RequestParam(required=false) String id) {
		TestLeftTree entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testLeftTreeService.get(id);
		}
		if (entity == null){
			entity = new TestLeftTree();
		}
		return entity;
	}
	
	/**
	 * 左树右表列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(TestLeftTree testLeftTree,  HttpServletRequest request, HttpServletResponse response, Model model) {
		
		return "modules/test/leftright/testLeftTreeList";
	}

	/**
	 * 查看，增加，编辑左树右表表单页面
	 * params:
	 * 	mode: add, edit, view,addChild 代表四种种模式的页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestLeftTree testLeftTree, Model model) {
		if (testLeftTree.getParent()!=null && StringUtils.isNotBlank(testLeftTree.getParent().getId())){
			testLeftTree.setParent(testLeftTreeService.get(testLeftTree.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(testLeftTree.getId())){
				TestLeftTree testLeftTreeChild = new TestLeftTree();
				testLeftTreeChild.setParent(new TestLeftTree(testLeftTree.getParent().getId()));
				List<TestLeftTree> list = testLeftTreeService.findList(testLeftTree); 
				if (list.size() > 0){
					testLeftTree.setSort(list.get(list.size()-1).getSort());
					if (testLeftTree.getSort() != null){
						testLeftTree.setSort(testLeftTree.getSort() + 30);
					}
				}
			}
		}
		if (testLeftTree.getSort() == null){
			testLeftTree.setSort(30);
		}
		model.addAttribute("mode", mode);
		model.addAttribute("testLeftTree", testLeftTree);
		return "modules/test/leftright/testLeftTreeForm";
	}

	/**
	 * 保存左树右表
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(TestLeftTree testLeftTree, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testLeftTree);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}

		//新增或编辑表单保存
		testLeftTreeService.save(testLeftTree);//保存
		j.setSuccess(true);
		j.put("testLeftTree", testLeftTree);
		j.setMsg("保存左树右表成功");
		return j;
	}
	
	@ResponseBody
	@RequestMapping(value = "getChildren")
	public List<TestLeftTree> getChildren(String parentId){
		if("-1".equals(parentId)){//如果是-1，没指定任何父节点，就从根节点开始查找
			parentId = "0";
		}
		return testLeftTreeService.getChildren(parentId);
	}
	
	/**
	 * 删除左树右表
	 */
	@ResponseBody
	@RequestMapping(value = "delete")
	public AjaxJson delete(TestLeftTree testLeftTree) {
		AjaxJson j = new AjaxJson();
		testLeftTreeService.delete(testLeftTree);
		j.setSuccess(true);
		j.setMsg("删除左树右表成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<TestLeftTree> list = testLeftTreeService.findList(new TestLeftTree());
		for (int i=0; i<list.size(); i++){
			TestLeftTree e = list.get(i);
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