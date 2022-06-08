/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.beiliao.apply.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 备料明细Entity
 * @author Jin
 * @version 2022-06-08
 */
public class BusinessShengchanBeiliaoApplyMx extends DataEntity<BusinessShengchanBeiliaoApplyMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessShengChanBeiLiaoApply p;		// 父键 父类
	private Integer no;		// 序号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;		// 单位
	private String hw;// 货位
	
	public BusinessShengchanBeiliaoApplyMx() {
		super();
	}

	public BusinessShengchanBeiliaoApplyMx(String id){
		super(id);
	}

	public BusinessShengchanBeiliaoApplyMx(BusinessShengChanBeiLiaoApply p){
		this.p = p;
	}

	public BusinessShengChanBeiLiaoApply getP() {
		return p;
	}

	public void setP(BusinessShengChanBeiLiaoApply p) {
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
		if(cinvstd==null){
			cinvstd = "";
		}
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

	public String getHw() {
		if(hw==null){
			hw= "";
		}
		return hw;
	}

	public BusinessShengchanBeiliaoApplyMx setHw(String hw) {
		this.hw = hw;
		return this;
	}
}