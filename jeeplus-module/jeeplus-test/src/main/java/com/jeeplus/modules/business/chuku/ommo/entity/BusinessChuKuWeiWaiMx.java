/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.chuku.ommo.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外发料明细Entity
 * @author Jin
 * @version 2022-05-29
 */
public class BusinessChuKuWeiWaiMx extends DataEntity<BusinessChuKuWeiWaiMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessChuKuWeiWai p;		// 父键 父类
	private Integer no;		// 行号
	private String cinvcode;		// 子件编码
	private String cinvname;		// 子件名称
	private String cinvstd;		// 规格型号
	private Double num;		// 发货数量
	private String unit;		// 子件单位
	private String batchno;		// 批号
	private String hw;		// 货位
	private String moid;		// 订单ID
	private String mohid;		// 订单行ID
	
	public BusinessChuKuWeiWaiMx() {
		super();
	}

	public BusinessChuKuWeiWaiMx(String id){
		super(id);
	}

	public BusinessChuKuWeiWaiMx(BusinessChuKuWeiWai p){
		this.p = p;
	}

	public BusinessChuKuWeiWai getP() {
		return p;
	}

	public void setP(BusinessChuKuWeiWai p) {
		this.p = p;
	}
	
	@ExcelField(title="行号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="子件编码", align=2, sort=9)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="子件名称", align=2, sort=10)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="发货数量", align=2, sort=12)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="子件单位", align=2, sort=13)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="批号", align=2, sort=14)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="货位", align=2, sort=15)
	public String getHw() {
		return hw;
	}

	public void setHw(String hw) {
		this.hw = hw;
	}
	
	@ExcelField(title="订单ID", align=2, sort=16)
	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}
	
	@ExcelField(title="订单行ID", align=2, sort=17)
	public String getMohid() {
		return mohid;
	}

	public void setMohid(String mohid) {
		this.mohid = mohid;
	}
	
}