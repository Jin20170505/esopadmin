/**
 *
 */
package com.jeeplus.modules.business.arrivalvouch.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 采购到货单Entity
 * @author Jin
 */
public class BusinessArrivalVouch extends DataEntity<BusinessArrivalVouch> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 到货单号
	private Date arriveDate;		// 到货日期
	private String u8code;		// U8单号
	private Office dept;		// 部门
	private BaseVendor vendor;		// 供应商
	private String status;		// 状态
	private String u8status;		// U8状态
	private Date beginArriveDate;		// 开始 到货日期
	private Date endArriveDate;		// 结束 到货日期
	private List<BusinessArrivalVouchMx> businessArrivalVouchMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessArrivalVouch() {
		super();
	}

	public BusinessArrivalVouch(String id){
		super(id);
	}

	@ExcelField(title="到货单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="到货日期不能为空")
	@ExcelField(title="到货日期", align=2, sort=8)
	public Date getArriveDate() {
		return arriveDate;
	}

	public void setArriveDate(Date arriveDate) {
		this.arriveDate = arriveDate;
	}
	
	@ExcelField(title="U8单号", align=2, sort=9)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=10)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="供应商", fieldType=BaseVendor.class, value="vendor.name", align=2, sort=11)
	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="U8状态", align=2, sort=13)
	public String getU8status() {
		return u8status;
	}

	public void setU8status(String u8status) {
		this.u8status = u8status;
	}
	
	public Date getBeginArriveDate() {
		return beginArriveDate;
	}

	public void setBeginArriveDate(Date beginArriveDate) {
		this.beginArriveDate = beginArriveDate;
	}
	
	public Date getEndArriveDate() {
		return endArriveDate;
	}

	public void setEndArriveDate(Date endArriveDate) {
		this.endArriveDate = endArriveDate;
	}
		
	public List<BusinessArrivalVouchMx> getBusinessArrivalVouchMxList() {
		return businessArrivalVouchMxList;
	}

	public void setBusinessArrivalVouchMxList(List<BusinessArrivalVouchMx> businessArrivalVouchMxList) {
		this.businessArrivalVouchMxList = businessArrivalVouchMxList;
	}
}