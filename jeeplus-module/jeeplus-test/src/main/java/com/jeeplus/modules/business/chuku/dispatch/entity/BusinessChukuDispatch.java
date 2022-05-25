/**
 *
 */
package com.jeeplus.modules.business.chuku.dispatch.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 销售出库单Entity
 * @author Jin
 */
public class BusinessChukuDispatch extends DataEntity<BusinessChukuDispatch> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 出库单号
	private String dispatchcode;		// 发货单号
	private Date fahuoDate;		// 发货日期
	private String u8code;		// U8单号
	private Office dept;		// 部门
	private BaseCustomer customer;		// 客户
	private String status;		// 状态
	private String u8status;		// U8状态
	private Date beginFahuoDate;		// 开始 发货日期
	private Date endFahuoDate;		// 结束 发货日期
	private List<BusinessChukuDispatchMx> businessChukuDispatchMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessChukuDispatch() {
		super();
	}

	public BusinessChukuDispatch(String id){
		super(id);
	}

	@ExcelField(title="出库单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="发货单号", align=2, sort=8)
	public String getDispatchcode() {
		return dispatchcode;
	}

	public void setDispatchcode(String dispatchcode) {
		this.dispatchcode = dispatchcode;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="发货日期不能为空")
	@ExcelField(title="发货日期", align=2, sort=9)
	public Date getFahuoDate() {
		return fahuoDate;
	}

	public void setFahuoDate(Date fahuoDate) {
		this.fahuoDate = fahuoDate;
	}
	
	@ExcelField(title="U8单号", align=2, sort=10)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	@NotNull(message="部门不能为空")
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=11)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@NotNull(message="客户不能为空")
	@ExcelField(title="客户", fieldType=BaseCustomer.class, value="customer.name", align=2, sort=12)
	public BaseCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(BaseCustomer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="状态", align=2, sort=13)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@ExcelField(title="U8状态", align=2, sort=14)
	public String getU8status() {
		return u8status;
	}

	public void setU8status(String u8status) {
		this.u8status = u8status;
	}
	
	public Date getBeginFahuoDate() {
		return beginFahuoDate;
	}

	public void setBeginFahuoDate(Date beginFahuoDate) {
		this.beginFahuoDate = beginFahuoDate;
	}
	
	public Date getEndFahuoDate() {
		return endFahuoDate;
	}

	public void setEndFahuoDate(Date endFahuoDate) {
		this.endFahuoDate = endFahuoDate;
	}
		
	public List<BusinessChukuDispatchMx> getBusinessChukuDispatchMxList() {
		return businessChukuDispatchMxList;
	}

	public void setBusinessChukuDispatchMxList(List<BusinessChukuDispatchMx> businessChukuDispatchMxList) {
		this.businessChukuDispatchMxList = businessChukuDispatchMxList;
	}
}