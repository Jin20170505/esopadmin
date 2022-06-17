/**
 *
 */
package com.jeeplus.modules.base.dept.time.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 部门时间Entity
 * @author Jin
 */
public class BaseDeptTime extends DataEntity<BaseDeptTime> {
	
	private static final long serialVersionUID = 1L;
	private String deptcode;		// 部门编码
	private String deptname;		// 部门名称
	private Double worktime;		// 工作时间
	
	public BaseDeptTime() {
		super();
	}

	public BaseDeptTime(String id){
		super(id);
	}

	@ExcelField(title="部门编码", align=2, sort=7)
	public String getDeptcode() {
		return deptcode;
	}

	public void setDeptcode(String deptcode) {
		this.deptcode = deptcode;
	}
	
	@ExcelField(title="部门名称", fieldType=String.class, value="", align=2, sort=8)
	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	@NotNull(message="工作时间不能为空")
	@ExcelField(title="工作时间", align=2, sort=9)
	public Double getWorktime() {
		return worktime;
	}

	public void setWorktime(Double worktime) {
		this.worktime = worktime;
	}
	
}