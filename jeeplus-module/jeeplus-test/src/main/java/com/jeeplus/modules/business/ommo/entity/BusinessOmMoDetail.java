/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommo.entity;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

import java.util.Date;

/**
 * 委外明细Entity
 * @author Jin
 * @version 2022-05-28
 */
public class  BusinessOmMoDetail extends DataEntity<BusinessOmMoDetail> {
	
	private static final long serialVersionUID = 1L;
	private BusinessOmMoMain mo;		// 父ID 父类
	private Integer no;		// 行号
	private Date startdate;		// 计划下达日期
	private Date arrivedate;		// 计划到货日期
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 存货规格
	private Double num;		// 数量
	private String unit;		// 单位
	private String memo;		// 备注
	private Double donenum; // 已拆数量
	private Double nonum;	// 未拆数量
	private String printstatus;		// 打印状态
	private String ischaidan; // 是否拆单

	public BusinessOmMoDetail() {
		super();
	}

	public BusinessOmMoDetail(String id){
		super(id);
	}

	public BusinessOmMoDetail(BusinessOmMoMain mo){
		this.mo = mo;
	}

	public BusinessOmMoMain getMo() {
		return mo;
	}

	public void setMo(BusinessOmMoMain mo) {
		this.mo = mo;
	}
	
	@NotNull(message="行号不能为空")
	@ExcelField(title="行号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="存货编码", align=2, sort=9)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=10)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="存货规格", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=12)
	public Double getNum() {
		if(num==null){
			num = 0.0;
		}
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=13)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="备注", align=2, sort=14)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@ExcelField(title="打印状态", align=2, sort=14)
	public String getPrintstatus() {
		return printstatus;
	}

	public void setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="计划下达日期", align=2, sort=11)
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="计划到货日期", align=2, sort=12)
	public Date getArrivedate() {
		return arrivedate;
	}

	public void setArrivedate(Date arrivedate) {
		this.arrivedate = arrivedate;
	}

	public String getIschaidan() {
		return ischaidan;
	}

	public BusinessOmMoDetail setIschaidan(String ischaidan) {
		this.ischaidan = ischaidan;
		return this;
	}

	public Double getDonenum() {
		return donenum;
	}

	public BusinessOmMoDetail setDonenum(Double donenum) {
		this.donenum = donenum;
		return this;
	}

	public Double getNonum() {
		return nonum;
	}

	public BusinessOmMoDetail setNonum(Double nonum) {
		this.nonum = nonum;
		return this;
	}
}