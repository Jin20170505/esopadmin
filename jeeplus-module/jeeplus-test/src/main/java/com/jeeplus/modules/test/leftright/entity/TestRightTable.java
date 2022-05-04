/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.test.leftright.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 右表Entity
 * @author Jin
 * @version 2022-05-04
 */
public class TestRightTable extends DataEntity<TestRightTable> {
	
	private static final long serialVersionUID = 1L;
	private TestLeftTree p;		// 左树ID 父类
	private String code;		// 编码
	private String name;		// 名称
	
	public TestRightTable() {
		super();
	}

	public TestRightTable(String id){
		super(id);
	}

	public TestRightTable(TestLeftTree p){
		this.p = p;
	}

	public TestLeftTree getP() {
		return p;
	}

	public void setP(TestLeftTree p) {
		this.p = p;
	}
	
	@ExcelField(title="编码", align=2, sort=8)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="名称", align=2, sort=9)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}