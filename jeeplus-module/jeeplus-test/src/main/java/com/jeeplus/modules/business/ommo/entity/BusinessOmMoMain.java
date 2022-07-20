/**
 *
 */
package com.jeeplus.modules.business.ommo.entity;

import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外订单Entity
 * @author Jin
 */
public class BusinessOmMoMain extends DataEntity<BusinessOmMoMain> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 订单编号
	private String vendorname; // 供应商名称
	private BaseVendor vendor;		// 供应商
	private Date dcreatedate;		// 制单日期
	private Date ddate;		// 订单日期
	private String memo;		// 备注
	private String status;		// 状态
	private String u8code;		// u8编号
	private Date beginDcreatedate;		// 开始 制单日期
	private Date endDcreatedate;		// 结束 制单日期
	private Date beginDdate;		// 开始 订单日期
	private Date endDdate;		// 结束 订单日期
	private Date beginArrivedate;		// 开始 计划到货日期
	private Date endArrivedate;		// 结束 计划到货日期
	private List<BusinessOmMoDetail> businessOmMoDetailList = Lists.newArrayList();		// 子表列表
	
	public BusinessOmMoMain() {
		super();
	}

	public BusinessOmMoMain(String id){
		super(id);
	}

	@ExcelField(title="订单编号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", align=2, sort=8)
	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}

	public String getVendorname() {
		return vendorname;
	}

	public BusinessOmMoMain setVendorname(String vendorname) {
		this.vendorname = vendorname;
		return this;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="制单日期不能为空")
	@ExcelField(title="制单日期", align=2, sort=9)
	public Date getDcreatedate() {
		return dcreatedate;
	}

	public void setDcreatedate(Date dcreatedate) {
		this.dcreatedate = dcreatedate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="订单日期不能为空")
	@ExcelField(title="订单日期", align=2, sort=10)
	public Date getDdate() {
		return ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	@ExcelField(title="备注", align=2, sort=13)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	@ExcelField(title="状态", align=2, sort=15)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="u8编号", align=2, sort=16)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	public Date getBeginDcreatedate() {
		return beginDcreatedate;
	}

	public void setBeginDcreatedate(Date beginDcreatedate) {
		this.beginDcreatedate = beginDcreatedate;
	}
	
	public Date getEndDcreatedate() {
		return endDcreatedate;
	}

	public void setEndDcreatedate(Date endDcreatedate) {
		this.endDcreatedate = endDcreatedate;
	}
		
	public Date getBeginDdate() {
		return beginDdate;
	}

	public void setBeginDdate(Date beginDdate) {
		this.beginDdate = beginDdate;
	}
	
	public Date getEndDdate() {
		return endDdate;
	}

	public void setEndDdate(Date endDdate) {
		this.endDdate = endDdate;
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
		
	public List<BusinessOmMoDetail> getBusinessOmMoDetailList() {
		return businessOmMoDetailList;
	}

	public void setBusinessOmMoDetailList(List<BusinessOmMoDetail> businessOmMoDetailList) {
		this.businessOmMoDetailList = businessOmMoDetailList;
	}
}