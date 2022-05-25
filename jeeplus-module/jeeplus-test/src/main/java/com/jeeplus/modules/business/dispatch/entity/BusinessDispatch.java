/**
 *
 */
package com.jeeplus.modules.business.dispatch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;

import java.util.Date;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 销售发货单Entity
 * @author Jin
 */
public class BusinessDispatch extends DataEntity<BusinessDispatch> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 销售发货单号
	private String opcode;		// 业务员编码
	private String u8code;		// U8单号
	private Office dept;		// 部门
	private BaseCustomer customer;		// 客户
	private String status;		// 状态
	private String u8status;		// U8状态
	private String printstatus;		// 打印状态
	private Date fahuodate;		// 发货日期
	private String fdate;

	public String getFdate() {
		fdate = DateUtils.formatDate(fahuodate,"yyyy-MM-dd");
		return fdate;
	}

	private List<BusinessDispatchMx> businessDispatchMxList = Lists.newArrayList();		// 子表列表
	
	public BusinessDispatch() {
		super();
	}

	public BusinessDispatch(String id){
		super(id);
	}

	@ExcelField(title="销售发货单号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="业务员编码", align=2, sort=8)
	public String getOpcode() {
		return opcode;
	}

	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
	
	@ExcelField(title="U8单号", align=2, sort=9)
	public String getU8code() {
		return u8code;
	}

	public void setU8code(String u8code) {
		this.u8code = u8code;
	}
	
	@NotNull(message="部门不能为空")
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=10)
	public Office getDept() {
		if(dept==null){
			dept=new Office();
			dept.setName("");
		}
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@NotNull(message="客户不能为空")
	@ExcelField(title="客户", fieldType=BaseCustomer.class, value="customer.name", align=2, sort=11)
	public BaseCustomer getCustomer() {
		if(customer==null){
			customer = new BaseCustomer();
			customer.setName("");
		}
		return customer;
	}

	public void setCustomer(BaseCustomer customer) {
		this.customer = customer;
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
	
	@ExcelField(title="打印状态", align=2, sort=14)
	public String getPrintstatus() {
		return printstatus;
	}

	public void setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="发货日期", align=2, sort=15)
	public Date getFahuodate() {
		return fahuodate;
	}

	public void setFahuodate(Date fahuodate) {
		this.fahuodate = fahuodate;
	}
	
	public List<BusinessDispatchMx> getBusinessDispatchMxList() {
		return businessDispatchMxList;
	}

	public void setBusinessDispatchMxList(List<BusinessDispatchMx> businessDispatchMxList) {
		this.businessDispatchMxList = businessDispatchMxList;
	}
}