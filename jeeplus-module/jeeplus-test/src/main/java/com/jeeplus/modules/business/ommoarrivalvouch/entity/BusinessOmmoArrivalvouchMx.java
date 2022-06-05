/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommoarrivalvouch.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.sys.entity.Office;

import java.util.Date;

/**
 * 委外到货明细Entity
 * @author Jin
 * @version 2022-05-31
 */
public class BusinessOmmoArrivalvouchMx extends DataEntity<BusinessOmmoArrivalvouchMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessOmmoArrivalVouch p;		// 父键 父类
	private Integer no;		// 序号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private String cinvstd;		// 规格型号
	private String batchno;		// 批号
	private String scdate;		// 生产日期
	private Double num;		// 数量
	private String unit;		// 单位
	private Double minnum;		// 最小包装数
	private String ckcode;		// 仓库编码
	private String ckname;		// 仓库名称
	private String hw;		// 推荐货位
	private String csocode;		// 来源单号
	private String irowno;		// 跟踪行号
	private String printstatus;		// 打印状态
	private Date beginDdate;		// 开始 到货日期
	private Date endDdate;		// 结束 到货日期
	private BaseVendor vendor;		// 供应商
	private Office dept;		// 部门

	public BaseVendor getVendor() {
		return vendor;
	}

	public BusinessOmmoArrivalvouchMx setVendor(BaseVendor vendor) {
		this.vendor = vendor;
		return this;
	}

	public Office getDept() {
		return dept;
	}

	public BusinessOmmoArrivalvouchMx setDept(Office dept) {
		this.dept = dept;
		return this;
	}

	public BusinessOmmoArrivalvouchMx() {
		super();
	}

	public BusinessOmmoArrivalvouchMx(String id){
		super(id);
	}

	public BusinessOmmoArrivalvouchMx(BusinessOmmoArrivalVouch p){
		this.p = p;
	}

	public BusinessOmmoArrivalVouch getP() {
		return p;
	}

	public void setP(BusinessOmmoArrivalVouch p) {
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

	public String getSimpleCinvname(){
		if(cinvname==null){
			return "";
		}
		if(cinvname.length()>20){
			return cinvname.substring(0,20);
		}
		return cinvname;
	}
	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=11)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@ExcelField(title="批号", align=2, sort=12)
	public String getBatchno() {
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=13)
	public String getScdate() {
		return scdate;
	}

	public void setScdate(String scdate) {
		this.scdate = scdate;
	}
	
	@ExcelField(title="数量", align=2, sort=14)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=15)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@ExcelField(title="最小包装数", align=2, sort=16)
	public Double getMinnum() {
		return minnum;
	}

	public void setMinnum(Double minnum) {
		this.minnum = minnum;
	}
	
	@ExcelField(title="仓库编码", align=2, sort=17)
	public String getCkcode() {
		return ckcode;
	}

	public void setCkcode(String ckcode) {
		this.ckcode = ckcode;
	}
	
	@ExcelField(title="仓库名称", align=2, sort=18)
	public String getCkname() {
		return ckname;
	}

	public void setCkname(String ckname) {
		this.ckname = ckname;
	}
	
	@ExcelField(title="推荐货位", align=2, sort=19)
	public String getHw() {
		if(hw==null){
			hw = "";
		}
		return hw;
	}

	public void setHw(String hw) {
		this.hw = hw;
	}
	
	@ExcelField(title="来源单号", align=2, sort=20)
	public String getCsocode() {
		return csocode;
	}

	public void setCsocode(String csocode) {
		this.csocode = csocode;
	}
	
	@ExcelField(title="跟踪行号", align=2, sort=21)
	public String getIrowno() {
		return irowno;
	}

	public void setIrowno(String irowno) {
		this.irowno = irowno;
	}

	public String getPrintstatus() {
		return printstatus;
	}

	public BusinessOmmoArrivalvouchMx setPrintstatus(String printstatus) {
		this.printstatus = printstatus;
		return this;
	}

	public Date getBeginDdate() {
		return beginDdate;
	}

	public BusinessOmmoArrivalvouchMx setBeginDdate(Date beginDdate) {
		this.beginDdate = beginDdate;
		return this;
	}

	public Date getEndDdate() {
		return endDdate;
	}

	public BusinessOmmoArrivalvouchMx setEndDdate(Date endDdate) {
		this.endDdate = endDdate;
		return this;
	}
}