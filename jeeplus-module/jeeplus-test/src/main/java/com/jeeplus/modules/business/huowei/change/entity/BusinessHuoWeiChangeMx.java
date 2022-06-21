/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.huowei.change.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 货位调整明细Entity
 * @author Jin
 * @version 2022-06-21
 */
public class BusinessHuoWeiChangeMx extends DataEntity<BusinessHuoWeiChangeMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessHuoWeiChange p;		// 父键 父类
	private Integer no;		// 序号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批号
	private String scdate;		// 生产日期
	private String hwbefore;		// 调整前货位
	private String hwafter;		// 调整后货位
	private Double num;		// 数量
	private String unit; // 单位
	
	public BusinessHuoWeiChangeMx() {
		super();
	}

	public BusinessHuoWeiChangeMx(String id){
		super(id);
	}

	public BusinessHuoWeiChangeMx(BusinessHuoWeiChange p){
		this.p = p;
	}

	public BusinessHuoWeiChange getP() {
		return p;
	}

	public void setP(BusinessHuoWeiChange p) {
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
	
	@ExcelField(title="调整前货位", align=2, sort=14)
	public String getHwbefore() {
		return hwbefore;
	}

	public void setHwbefore(String hwbefore) {
		this.hwbefore = hwbefore;
	}
	
	@ExcelField(title="调整后货位", align=2, sort=15)
	public String getHwafter() {
		return hwafter;
	}

	public void setHwafter(String hwafter) {
		this.hwafter = hwafter;
	}
	
	@ExcelField(title="数量", align=2, sort=16)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public String getUnit() {
		return unit;
	}

	public BusinessHuoWeiChangeMx setUnit(String unit) {
		this.unit = unit;
		return this;
	}
}