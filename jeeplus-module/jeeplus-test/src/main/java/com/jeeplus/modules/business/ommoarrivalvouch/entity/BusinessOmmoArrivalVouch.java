/**
 *
 */
package com.jeeplus.modules.business.ommoarrivalvouch.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.sys.entity.Office;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 委外到货单Entity
 * @author Jin
 */
public class BusinessOmmoArrivalVouch extends DataEntity<BusinessOmmoArrivalVouch> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 到货单号
	private Date ddate;		// 到货日期
	private BaseVendor vendor;		// 供应商
	private Office dept;		// 部门
	private String cmarker;		// 制单人
	private String cpersonname;		// 业务员
	private String printstatus;		// 打印状态
	private String memo;		// 备注
	private Date beginDdate;		// 开始 到货日期
	private Date endDdate;		// 结束 到货日期
	private List<BusinessOmmoArrivalvouchMx> businessOmmoArrivalvouchMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessOmmoArrivalVouch() {
		super();
	}

	public BusinessOmmoArrivalVouch(String id){
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
	public Date getDdate() {
		return ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}
	
	@NotNull(message="供应商不能为空")
	@ExcelField(title="供应商", fieldType=BaseVendor.class, value="vendor.name", align=2, sort=9)
	public BaseVendor getVendor() {
		return vendor;
	}

	public void setVendor(BaseVendor vendor) {
		this.vendor = vendor;
	}
	
	@NotNull(message="部门不能为空")
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=10)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="制单人", align=2, sort=11)
	public String getCmarker() {
		return cmarker;
	}

	public void setCmarker(String cmarker) {
		this.cmarker = cmarker;
	}
	
	@ExcelField(title="业务员", align=2, sort=12)
	public String getCpersonname() {
		return cpersonname;
	}

	public void setCpersonname(String cpersonname) {
		this.cpersonname = cpersonname;
	}
	
	@ExcelField(title="打印状态", align=2, sort=13)
	public String getPrintstatus() {
		return printstatus;
	}

	public void setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
	}
	
	@ExcelField(title="备注", align=2, sort=14)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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
		
	public List<BusinessOmmoArrivalvouchMx> getBusinessOmmoArrivalvouchMxList() {
		return businessOmmoArrivalvouchMxList;
	}

	public void setBusinessOmmoArrivalvouchMxList(List<BusinessOmmoArrivalvouchMx> businessOmmoArrivalvouchMxList) {
		this.businessOmmoArrivalvouchMxList = businessOmmoArrivalvouchMxList;
	}
}