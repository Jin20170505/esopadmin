/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.jihuadingdan.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 计划工单明细Entity
 * @author Jin
 * @version 2022-05-06
 */
public class BusinessJiHuaGongDanBom extends DataEntity<BusinessJiHuaGongDanBom> {

	private static final long serialVersionUID = 1L;
	private BusinessJiHuaGongDan p;		// 计划工单 父类
	private String scyid; // 生产子件ID
	private Integer no;		// 子件行号
	private Double num;		// 数量
	private Double donenum;		// 已领数量
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String producttype;		// 产出类型
	private String unitcode;		// 单位编码
	private String unitname;		// 单位名称
	private Double rate;		// 换算率
	private Double baseqtyn;		// 基本用量_分子
	private Double baseqtyd;		// 基本用量_分母
	private Double auxbaseqtyn;		// 辅助基本用量
	private String isdaochong;	// 是否倒冲

	public BusinessJiHuaGongDanBom() {
		super();
	}

	public BusinessJiHuaGongDanBom(String id){
		super(id);
	}

	public BusinessJiHuaGongDanBom(BusinessJiHuaGongDan p){
		this.p = p;
	}

	public BusinessJiHuaGongDan getP() {
		return p;
	}

	public void setP(BusinessJiHuaGongDan p) {
		this.p = p;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getScyid() {
		return scyid;
	}

	public BusinessJiHuaGongDanBom setScyid(String scyid) {
		this.scyid = scyid;
		return this;
	}

	public Integer getNo() {
		return no;
	}

	public BusinessJiHuaGongDanBom setNo(Integer no) {
		this.no = no;
		return this;
	}

	public Double getNum() {
		return num;
	}

	public BusinessJiHuaGongDanBom setNum(Double num) {
		this.num = num;
		return this;
	}

	public Double getDonenum() {
		return donenum;
	}

	public BusinessJiHuaGongDanBom setDonenum(Double donenum) {
		this.donenum = donenum;
		return this;
	}

	public String getCinvcode() {
		return cinvcode;
	}

	public BusinessJiHuaGongDanBom setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
		return this;
	}

	public String getCinvname() {
		return cinvname;
	}

	public BusinessJiHuaGongDanBom setCinvname(String cinvname) {
		this.cinvname = cinvname;
		return this;
	}

	public String getCinvstd() {
		return cinvstd;
	}

	public BusinessJiHuaGongDanBom setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
		return this;
	}

	public String getProducttype() {
		return producttype;
	}

	public BusinessJiHuaGongDanBom setProducttype(String producttype) {
		this.producttype = producttype;
		return this;
	}

	public String getUnitcode() {
		return unitcode;
	}

	public BusinessJiHuaGongDanBom setUnitcode(String unitcode) {
		this.unitcode = unitcode;
		return this;
	}

	public String getUnitname() {
		return unitname;
	}

	public BusinessJiHuaGongDanBom setUnitname(String unitname) {
		this.unitname = unitname;
		return this;
	}

	public Double getRate() {
		return rate;
	}

	public BusinessJiHuaGongDanBom setRate(Double rate) {
		this.rate = rate;
		return this;
	}

	public Double getBaseqtyn() {
		return baseqtyn;
	}

	public BusinessJiHuaGongDanBom setBaseqtyn(Double baseqtyn) {
		this.baseqtyn = baseqtyn;
		return this;
	}

	public Double getBaseqtyd() {
		return baseqtyd;
	}

	public BusinessJiHuaGongDanBom setBaseqtyd(Double baseqtyd) {
		this.baseqtyd = baseqtyd;
		return this;
	}

	public Double getAuxbaseqtyn() {
		return auxbaseqtyn;
	}

	public BusinessJiHuaGongDanBom setAuxbaseqtyn(Double auxbaseqtyn) {
		this.auxbaseqtyn = auxbaseqtyn;
		return this;
	}

	public String getIsdaochong() {
		return isdaochong;
	}

	public BusinessJiHuaGongDanBom setIsdaochong(String isdaochong) {
		this.isdaochong = isdaochong;
		return this;
	}
}