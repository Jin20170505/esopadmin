/**
 *
 */
package com.jeeplus.modules.business.ommo.chaidan.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外拆单Entity
 * @author Jin
 */
public class BusinessOmmoChaiDan extends DataEntity<BusinessOmmoChaiDan> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 单号
	private String wwcode;		// 委外单号
	private Integer wwline;		// 委外行号
	private String vendor; // 供应商id
	private String vendorname; // 供应商名称
	private String ddrq;	// 订单日期
	private Date startdate;		// 计划下达日期
	private Date arrivedate;		// 预计到货日期
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;		// 单位
	private String memo;		// 备注
	private String printstatus; // 打印状态
	private String wwid;		// 委外ID
	private String wwhid;		// 委外行ID
	private Date beginStartdate;		// 开始 计划下达日期
	private Date endStartdate;		// 结束 计划下达日期
	private Date beginArrivedate;		// 开始 预计到货日期
	private Date endArrivedate;		// 结束 预计到货日期
	private List<BusinessOmmoChaiDanMx> businessOmmoChaiDanMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessOmmoChaiDan() {
		super();
	}

	public BusinessOmmoChaiDan(String id){
		super(id);
	}

	@ExcelField(title="单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="委外单号", align=2, sort=8)
	public String getWwcode() {
		return wwcode;
	}

	public void setWwcode(String wwcode) {
		this.wwcode = wwcode;
	}
	
	@ExcelField(title="委外行号", align=2, sort=9)
	public Integer getWwline() {
		return wwline;
	}

	public void setWwline(Integer wwline) {
		this.wwline = wwline;
	}

	public String getVendor() {
		return vendor;
	}

	public BusinessOmmoChaiDan setVendor(String vendor) {
		this.vendor = vendor;
		return this;
	}

	public String getDdrq() {
		return ddrq;
	}

	public BusinessOmmoChaiDan setDdrq(String ddrq) {
		this.ddrq = ddrq;
		return this;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="计划下达日期", align=2, sort=10)
	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="预计到货日期", align=2, sort=11)
	public Date getArrivedate() {
		return arrivedate;
	}

	public void setArrivedate(Date arrivedate) {
		this.arrivedate = arrivedate;
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
	
	@ExcelField(title="数量", align=2, sort=15)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=16)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="备注", align=2, sort=17)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPrintstatus() {
		return printstatus;
	}

	public BusinessOmmoChaiDan setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
		return this;
	}

	public String getWwid() {
		return wwid;
	}

	public void setWwid(String wwid) {
		this.wwid = wwid;
	}

	public String getWwhid() {
		return wwhid;
	}

	public void setWwhid(String wwhid) {
		this.wwhid = wwhid;
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
		
	public List<BusinessOmmoChaiDanMx> getBusinessOmmoChaiDanMxList() {
		return businessOmmoChaiDanMxList;
	}

	public void setBusinessOmmoChaiDanMxList(List<BusinessOmmoChaiDanMx> businessOmmoChaiDanMxList) {
		this.businessOmmoChaiDanMxList = businessOmmoChaiDanMxList;
	}

	public String getVendorname() {
		return vendorname;
	}

	public BusinessOmmoChaiDan setVendorname(String vendorname) {
		this.vendorname = vendorname;
		return this;
	}
}