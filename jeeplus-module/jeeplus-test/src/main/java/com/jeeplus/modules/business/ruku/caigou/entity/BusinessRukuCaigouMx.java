/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ruku.caigou.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 采购入库明细Entity
 * @author Jin
 * @version 2022-05-24
 */
public class BusinessRukuCaigouMx extends DataEntity<BusinessRukuCaigouMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessRukuCaiGou p;		// 父键 父类
	private Integer no;		// 行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;		// 单位
	private String batchno;		// 批号
	private String scdate;		// 生产日期
	private String cgid; // 采购出货ID
	private String cghid; // 采购出货行ID
	
	public BusinessRukuCaigouMx() {
		super();
	}

	public BusinessRukuCaigouMx(String id){
		super(id);
	}

	public BusinessRukuCaigouMx(BusinessRukuCaiGou p){
		this.p = p;
	}

	public BusinessRukuCaiGou getP() {
		return p;
	}

	public void setP(BusinessRukuCaiGou p) {
		this.p = p;
	}
	
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
	
	@ExcelField(title="规格型号", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="数量", align=2, sort=12)
	public Double getNum() {
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
	
	@ExcelField(title="批号", align=2, sort=14)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=15)
	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}

	public String getCgid() {
		return cgid;
	}

	public BusinessRukuCaigouMx setCgid(String cgid) {
		this.cgid = cgid;
		return this;
	}

	public String getCghid() {
		return cghid;
	}

	public BusinessRukuCaigouMx setCghid(String cghid) {
		this.cghid = cghid;
		return this;
	}
}