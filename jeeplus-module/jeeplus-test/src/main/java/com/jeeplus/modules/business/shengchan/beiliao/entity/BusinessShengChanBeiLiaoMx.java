/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.beiliao.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 备料明细Entity
 * @author Jin
 * @version 2022-05-15
 */
public class BusinessShengChanBeiLiaoMx extends DataEntity<BusinessShengChanBeiLiaoMx> {
	
	private static final long serialVersionUID = 1L;
	private Integer no;		// 序号
	private String hw;// 货位
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 存货规格
	private Double num;		// 数量
	private String unit;		// 单位
	private BusinessShengChanBeiLiao p;		// 父键 父类
	
	public BusinessShengChanBeiLiaoMx() {
		super();
	}

	public BusinessShengChanBeiLiaoMx(String id){
		super(id);
	}

	public BusinessShengChanBeiLiaoMx(BusinessShengChanBeiLiao p){
		this.p = p;
	}

	@ExcelField(title="序号", align=2, sort=7)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="存货编码", align=2, sort=8)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=9)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="存货规格", align=2, sort=10)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="数量", align=2, sort=11)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getHw() {
		return hw;
	}

	public BusinessShengChanBeiLiaoMx setHw(String hw) {
		this.hw = hw;
		return this;
	}

	public BusinessShengChanBeiLiao getP() {
		return p;
	}

	public void setP(BusinessShengChanBeiLiao p) {
		this.p = p;
	}
	
}