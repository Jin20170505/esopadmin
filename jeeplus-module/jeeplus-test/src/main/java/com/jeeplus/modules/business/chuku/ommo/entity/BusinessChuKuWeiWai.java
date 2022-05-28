/**
 *
 */
package com.jeeplus.modules.business.chuku.ommo.entity;

import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外发料Entity
 * @author Jin
 */
public class BusinessChuKuWeiWai extends DataEntity<BusinessChuKuWeiWai> {
	
	private static final long serialVersionUID = 1L;
	private String mocode;		// 委外单号
	private Integer mono;		// 委外行号
	private BaseVendor vendor;		// 供应商
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 订单数量
	private String unit;		// 单位
	private Date startdate;		// 计划下达日期
	private Date arrivedate;		// 计划到货日期
	private String moid;		// 订单主ID
	private String mohid;		// 订单行ID
	private Date beginStartdate;		// 开始 计划下达日期
	private Date endStartdate;		// 结束 计划下达日期
	private Date beginArrivedate;		// 开始 计划到货日期
	private Date endArrivedate;		// 结束 计划到货日期
	private List<BusinessChuKuWeiWaiMx> businessChuKuWeiWaiMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessChuKuWeiWai() {
		super();
	}

	public BusinessChuKuWeiWai(String id){
		super(id);
	}

	@ExcelField(title="委外单号", align=2, sort=7)
	public String getMocode() {
		return mocode;
	}

	public void setMocode(String mocode) {
		this.mocode = mocode;
	}
	
	@ExcelField(title="委外行号", align=2, sort=8)
	public Integer getMono() {
		return mono;
	}

	public void setMono(Integer mono) {
		this.mono = mono;
	}
	
	@ExcelField(title="供应商", fieldType=BaseVendor.class, value="vendor.name", align=2, sort=9)
	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}
	
	@ExcelField(title="存货编码", align=2, sort=10)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=11)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=12)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="订单数量", align=2, sort=13)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=14)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="计划下达日期", align=2, sort=15)
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="计划到货日期", align=2, sort=16)
	public Date getArrivedate() {
		return arrivedate;
	}

	public void setArrivedate(Date arrivedate) {
		this.arrivedate = arrivedate;
	}
	
	@ExcelField(title="订单主ID", align=2, sort=17)
	public String getMoid() {
		return moid;
	}

	public void setMoid(String moid) {
		this.moid = moid;
	}
	
	@ExcelField(title="订单行ID", align=2, sort=18)
	public String getMohid() {
		return mohid;
	}

	public void setMohid(String mohid) {
		this.mohid = mohid;
	}
	
	public Date getBeginStartdate() {
		return beginStartdate;
	}

	public void setBeginStartdate(Date beginStartdate) {
		this.beginStartdate = beginStartdate;
	}
	
	public Date getEndStartdate() {
		return endStartdate;
	}

	public void setEndStartdate(Date endStartdate) {
		this.endStartdate = endStartdate;
	}
		
	public Date getBeginArrivedate() {
		return beginArrivedate;
	}

	public void setBeginArrivedate(Date beginArrivedate) {
		this.beginArrivedate = beginArrivedate;
	}
	
	public Date getEndArrivedate() {
		return endArrivedate;
	}

	public void setEndArrivedate(Date endArrivedate) {
		this.endArrivedate = endArrivedate;
	}
		
	public List<BusinessChuKuWeiWaiMx> getBusinessChuKuWeiWaiMxList() {
		return businessChuKuWeiWaiMxList;
	}

	public void setBusinessChuKuWeiWaiMxList(List<BusinessChuKuWeiWaiMx> businessChuKuWeiWaiMxList) {
		this.businessChuKuWeiWaiMxList = businessChuKuWeiWaiMxList;
	}
}