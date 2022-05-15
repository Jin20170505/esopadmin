/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.bom.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 生产订单子件Entity
 * @author Jin
 * @version 2022-05-13
 */
public class BusinessShengChanBom extends DataEntity<BusinessShengChanBom> {
	
	private static final long serialVersionUID = 1L;
	private String schid;		// 生产行ID 父类
	private String scline;		// 生产行号
	private String no;		// 子件行号
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
	
	public BusinessShengChanBom() {
		super();
	}

	public BusinessShengChanBom(String id){
		super(id);
	}


	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	@ExcelField(title="生产行号", align=2, sort=8)
	public String getScline() {
		return scline;
	}

	public void setScline(String scline) {
		this.scline = scline;
	}
	
	@ExcelField(title="子件行号", align=2, sort=9)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@ExcelField(title="数量", align=2, sort=10)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="已领数量", align=2, sort=11)
	public Double getDonenum() {
		return donenum;
	}

	public void setDonenum(Double donenum) {
		this.donenum = donenum;
	}
	
	@ExcelField(title="存货编码", align=2, sort=12)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=13)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=14)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="产出类型", align=2, sort=15)
	public String getProducttype() {
		return producttype;
	}

	public void setProducttype(String producttype) {
		this.producttype = producttype;
	}
	
	@ExcelField(title="单位编码", align=2, sort=16)
	public String getUnitcode() {
		return unitcode;
	}

	public void setUnitcode(String unitcode) {
		this.unitcode = unitcode;
	}
	
	@ExcelField(title="单位名称", align=2, sort=17)
	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}
	
	@ExcelField(title="换算率", align=2, sort=18)
	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}
	
	@ExcelField(title="基本用量_分子", align=2, sort=19)
	public Double getBaseqtyn() {
		return baseqtyn;
	}

	public void setBaseqtyn(Double baseqtyn) {
		this.baseqtyn = baseqtyn;
	}
	
	@ExcelField(title="基本用量_分母", align=2, sort=20)
	public Double getBaseqtyd() {
		return baseqtyd;
	}

	public void setBaseqtyd(Double baseqtyd) {
		this.baseqtyd = baseqtyd;
	}
	
	@ExcelField(title="辅助基本用量", align=2, sort=21)
	public Double getAuxbaseqtyn() {
		return auxbaseqtyn;
	}

	public void setAuxbaseqtyn(Double auxbaseqtyn) {
		this.auxbaseqtyn = auxbaseqtyn;
	}

	public String getIsdaochong() {
		return isdaochong;
	}

	public BusinessShengChanBom setIsdaochong(String isdaochong) {
		this.isdaochong = isdaochong;
		return this;
	}
}