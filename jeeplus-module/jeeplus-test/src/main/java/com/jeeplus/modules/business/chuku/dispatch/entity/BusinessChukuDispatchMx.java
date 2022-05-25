/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.chuku.dispatch.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 销售出库单明细Entity
 * @author Jin
 * @version 2022-05-25
 */
public class BusinessChukuDispatchMx extends DataEntity<BusinessChukuDispatchMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessChukuDispatch pid;		// 父键 父类
	private Integer no;		// 行号
	private BaseCangKu ck;		// 仓库
	private BaseHuoWei hw;		// 货位
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批次号
	private String scdate;		// 生产日期
	private Double num;		// 数量
	private String unit;		// 单位
	private String customer;		// 客户
	private String dept;		// 部门
	private String fid; // 发货ID
	private String fhid;// 发货行ID
	
	public BusinessChukuDispatchMx() {
		super();
	}

	public BusinessChukuDispatchMx(String id){
		super(id);
	}

	public BusinessChukuDispatchMx(BusinessChukuDispatch pid){
		this.pid = pid;
	}

	public BusinessChukuDispatch getPid() {
		return pid;
	}

	public void setPid(BusinessChukuDispatch pid) {
		this.pid = pid;
	}
	
	@NotNull(message="行号不能为空")
	@ExcelField(title="行号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@ExcelField(title="仓库", fieldType=BaseCangKu.class, value="ck.name", align=2, sort=9)
	public BaseCangKu getCk() {
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="货位", fieldType=BaseHuoWei.class, value="hw.name", align=2, sort=10)
	public BaseHuoWei getHw() {
		return hw;
	}

	public void setHw(BaseHuoWei hw) {
		this.hw = hw;
	}
	
	@ExcelField(title="存货编码", align=2, sort=11)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=12)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=13)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批次号", align=2, sort=14)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=15)
	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=16)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=17)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="客户", align=2, sort=18)
	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="部门", align=2, sort=19)
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getFid() {
		return fid;
	}

	public BusinessChukuDispatchMx setFid(String fid) {
		this.fid = fid;
		return this;
	}

	public String getFhid() {
		return fhid;
	}

	public BusinessChukuDispatchMx setFhid(String fhid) {
		this.fhid = fhid;
		return this;
	}
}