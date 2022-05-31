/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ruku.ommo.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外入库明细Entity
 * @author Jin
 * @version 2022-05-31
 */
public class BusinessRukuOmmoMx extends DataEntity<BusinessRukuOmmoMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessRukuOmmo p;		// 父键 父类
	private Integer no;		// 序号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批号
	private String scdate;		// 生产日期
	private Double rukunum;		// 入库数量
	private String unit;		// 单位
	private String hw;		// 货位
	private String wdid;		// 委外到货ID
	private String wdhid;		// 委外到货行ID
	
	public BusinessRukuOmmoMx() {
		super();
	}

	public BusinessRukuOmmoMx(String id){
		super(id);
	}

	public BusinessRukuOmmoMx(BusinessRukuOmmo p){
		this.p = p;
	}

	public BusinessRukuOmmo getP() {
		return p;
	}

	public void setP(BusinessRukuOmmo p) {
		this.p = p;
	}
	
	@ExcelField(title="序号", align=2, sort=8)
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
	
	@ExcelField(title="规格型号", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批号", align=2, sort=12)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=13)
	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}
	
	@ExcelField(title="入库数量", align=2, sort=14)
	public Double getRukunum() {
		return rukunum;
	}

	public void setRukunum(Double rukunum) {
		this.rukunum = rukunum;
	}
	
	@ExcelField(title="单位", align=2, sort=15)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="货位", align=2, sort=16)
	public String getHw() {
		return hw;
	}

	public void setHw(String hw) {
		this.hw = hw;
	}
	
	@ExcelField(title="委外到货ID", align=2, sort=17)
	public String getWdid() {
		return wdid;
	}

	public void setWdid(String wdid) {
		this.wdid = wdid;
	}
	
	@ExcelField(title="委外到货行ID", align=2, sort=18)
	public String getWdhid() {
		return wdhid;
	}

	public void setWdhid(String wdhid) {
		this.wdhid = wdhid;
	}
	
}