/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.dingdan.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.base.unit.entity.BaseUnit;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

/**
 * 生产订单明细Entity
 * @author Jin
 * @version 2022-05-06
 */
public class BusinessShengChanDingDanMingXi extends DataEntity<BusinessShengChanDingDanMingXi> {
	
	private static final long serialVersionUID = 1L;
	private BusinessShengChanDingDan p;		// 生产订单 父类
	private Office dept; // 生产部门
	private Integer no;		// 行号
	private String cinvcode;
	private BusinessProduct cinv;		// 存货编码
	private String cinvname;		// 存货名称
	private String std;		// 存货规格型号
	private String unit;		// 单位
	private Double num;		// 数量
	private String startdate;		// 开工日期
	private String enddate;		// 完工日期
	private String batchno; // 批号
	private String type;
	private Double donenum; // 已拆单数量
	private String status; // 锁定 开立
	private String ischaidan; // 是否拆单

	public String getIschaidan() {
		return ischaidan;
	}

	public BusinessShengChanDingDanMingXi setIschaidan(String ischaidan) {
		this.ischaidan = ischaidan;
		return this;
	}

	public String getCinvcode() {
		return cinvcode;
	}

	public BusinessShengChanDingDanMingXi setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
		return this;
	}

	public BusinessShengChanDingDanMingXi() {
		super();
	}

	public BusinessShengChanDingDanMingXi(String id){
		super(id);
	}

	public BusinessShengChanDingDanMingXi(BusinessShengChanDingDan p){
		this.p = p;
	}

	public BusinessShengChanDingDan getP() {
		return p;
	}

	public void setP(BusinessShengChanDingDan p) {
		this.p = p;
	}

	public Office getDept() {
		return dept;
	}

	public BusinessShengChanDingDanMingXi setDept(Office dept) {
		this.dept = dept;
		return this;
	}

	public Double getDonenum() {
		return donenum;
	}

	public BusinessShengChanDingDanMingXi setDonenum(Double donenum) {
		this.donenum = donenum;
		return this;
	}

	@NotNull(message="行号不能为空")
	@ExcelField(title="行号", align=2, sort=8)
	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
	
	@NotNull(message="存货编码不能为空")
	@ExcelField(title="存货编码", fieldType=BusinessProduct.class, value="cinv.cdoe", align=2, sort=9)
	public BusinessProduct getCinv() {
		return cinv;
	}

	public void setCinv(BusinessProduct cinv) {
		this.cinv = cinv;
	}
	
	@ExcelField(title="存货名称", align=2, sort=10)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="存货规格型号", align=2, sort=11)
	public String getStd() {
		return std;
	}

	public void setStd(String std) {
		this.std = std;
	}
	
	@NotNull(message="单位不能为空")
	@ExcelField(title="单位", align=2, sort=12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=13)
	public Double getNum() {
		if(num==null){
			num = .0;
		}
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="开工日期", align=2, sort=14)
	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	
	@ExcelField(title="完工日期", align=2, sort=15)
	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getBatchno() {
		return batchno;
	}

	public BusinessShengChanDingDanMingXi setBatchno(String batchno) {
		this.batchno = batchno;
		return this;
	}

	public String getType() {
		return type;
	}

	public BusinessShengChanDingDanMingXi setType(String type) {
		this.type = type;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public BusinessShengChanDingDanMingXi setStatus(String status) {
		this.status = status;
		return this;
	}
}