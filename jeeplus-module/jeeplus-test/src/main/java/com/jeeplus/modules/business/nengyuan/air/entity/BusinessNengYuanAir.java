/**
 *
 */
package com.jeeplus.modules.business.nengyuan.air.entity;

import com.jeeplus.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 气能管理Entity
 * @author Jin
 */
public class BusinessNengYuanAir extends DataEntity<BusinessNengYuanAir> {
	
	private static final long serialVersionUID = 1L;
	private String sbcode;		// 设备号
	private String sbname;		// 设备名称
	private Office dept;		// 所属部门
	private Date startdate;		// 开始日期
	private Date enddate;		// 结束日期
	private Double beishu;		// 倍数
	private Double num;		// 数量
	
	public BusinessNengYuanAir() {
		super();
	}

	public BusinessNengYuanAir(String id){
		super(id);
	}

	@ExcelField(title="设备号", align=2, sort=7)
	public String getSbcode() {
		return sbcode;
	}

	public void setSbcode(String sbcode) {
		this.sbcode = sbcode;
	}
	
	@ExcelField(title="设备名称", align=2, sort=8)
	public String getSbname() {
		return sbname;
	}

	public void setSbname(String sbname) {
		this.sbname = sbname;
	}
	
	@NotNull(message="所属部门不能为空")
	@ExcelField(title="所属部门", fieldType=Office.class, value="dept.name", align=2, sort=9)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="开始日期不能为空")
	@ExcelField(title="开始日期", align=2, sort=10)
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="结束日期不能为空")
	@ExcelField(title="结束日期", align=2, sort=11)
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
	@ExcelField(title="倍数", align=2, sort=12)
	public Double getBeishu() {
		return beishu;
	}

	public void setBeishu(Double beishu) {
		this.beishu = beishu;
	}
	
	@ExcelField(title="数量", align=2, sort=13)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
}