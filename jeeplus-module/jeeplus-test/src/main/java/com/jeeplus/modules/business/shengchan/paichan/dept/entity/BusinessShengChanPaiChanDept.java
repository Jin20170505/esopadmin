/**
 *
 */
package com.jeeplus.modules.business.shengchan.paichan.dept.entity;

import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 排产部门Entity
 * @author Jin
 */
public class BusinessShengChanPaiChanDept extends DataEntity<BusinessShengChanPaiChanDept> {
	
	private static final long serialVersionUID = 1L;
	private Integer no;		// 序号
	private Office dept;		// 排产部门
	
	public BusinessShengChanPaiChanDept() {
		super();
	}

	public BusinessShengChanPaiChanDept(String id){
		super(id);
	}

	@ExcelField(title="序号", align=2, sort=7)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="排产部门", fieldType=Office.class, value="dept.name", align=2, sort=8)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
}