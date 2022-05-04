/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.leftright.web;

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
import com.jeeplus.modules.test.leftright.entity.TestRightTable;
import com.jeeplus.modules.test.leftright.service.TestRightTableService;

/**
 * 右表Controller
 * @author Jin
 * @version 2022-05-04
 */
@Controller
@RequestMapping(value = "${adminPath}/test/leftright/testRightTable")
public class TestRightTableController extends BaseController {

	@Autowired
	private TestRightTableService testRightTableService;
	
	@ModelAttribute
	public TestRightTable get(@RequestParam(required=false) String id) {
		TestRightTable entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = testRightTableService.get(id);
		}
		if (entity == null){
			entity = new TestRightTable();
		}
		return entity;
	}
	
	/**
	 * 右表列表页面
	 */
	@RequiresPermissions("test:leftright:testRightTable:list")
	@RequestMapping(value = {"list", ""})
	public String list(TestRightTable testRightTable, Model model) {
		model.addAttribute("testRightTable", testRightTable);
		return "modules/test/leftright/testRightTableList";
	}
	
		/**
	 * 右表列表数据
	 */
	@ResponseBody
	@RequiresPermissions("test:leftright:testRightTable:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(TestRightTable testRightTable, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<TestRightTable> page = testRightTableService.findPage(new Page<TestRightTable>(request, response), testRightTable); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑右表表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"test:leftright:testRightTable:view","test:leftright:testRightTable:add","test:leftright:testRightTable:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, TestRightTable testRightTable, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("testRightTable", testRightTable);
		return "modules/test/leftright/testRightTableForm";
	}

	/**
	 * 保存右表
	 */
	@ResponseBody
	@RequiresPermissions(value={"test:leftright:testRightTable:add","test:leftright:testRightTable:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(TestRightTable testRightTable, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(testRightTable);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		testRightTableService.save(testRightTable);//保存
		j.setSuccess(true);
		j.setMsg("保存右表成功");
		return j;
	}

	
	/**
	 * 批量删除右表
	 */
	@ResponseBody
	@RequiresPermissions("test:leftright:testRightTable:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			testRightTableService.delete(testRightTableService.get(id));
		}
		j.setMsg("删除右表成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("test:leftright:testRightTable:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(TestRightTable testRightTable, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "右表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<TestRightTable> page = testRightTableService.findPage(new Page<TestRightTable>(request, response, -1), testRightTable);
    		new ExportExcel("右表", TestRightTable.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出右表记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("test:leftright:testRightTable:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<TestRightTable> list = ei.getDataList(TestRightTable.class);
			for (TestRightTable testRightTable : list){
				try{
					testRightTableService.save(testRightTable);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条右表记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条右表记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入右表失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入右表数据模板
	 */
	@ResponseBody
	@RequiresPermissions("test:leftright:testRightTable:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "右表数据导入模板.xlsx";
    		List<TestRightTable> list = Lists.newArrayList(); 
    		new ExportExcel("右表数据", TestRightTable.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}