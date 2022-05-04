/**
 * 
 */
package com.jeeplus.modules.test.leftright.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 左树右表Entity
 * @author Jin
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class TestLeftTree extends TreeEntity<TestLeftTree> {
	
	private static final long serialVersionUID = 1L;
	
	private List<TestRightTable> testRightTableList = Lists.newArrayList();		// 子表列表
	
	public TestLeftTree() {
		super();
	}

	public TestLeftTree(String id){
		super(id);
	}

	public  TestLeftTree getParent() {
			return parent;
	}
	
	@Override
	public void setParent(TestLeftTree parent) {
		this.parent = parent;
		
	}
	
	public List<TestRightTable> getTestRightTableList() {
		return testRightTableList;
	}

	public void setTestRightTableList(List<TestRightTable> testRightTableList) {
		this.testRightTableList = testRightTableList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}