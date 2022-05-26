/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.dispatch.entity;

import javax.validation.constraints.NotNull;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 发货明细Entity
 * @author Jin
 * @version 2022-05-25
 */
public class BusinessDispatchMx extends DataEntity<BusinessDispatchMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessDispatch p;		// 父键 父类
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
	private String cordercode;		// 销售订单号
	private String irowno;		// 销售订单行号
	private BaseCustomer customer;		// 客户
	private Office dept;		// 部门
	
	public BusinessDispatchMx() {
		super();
	}

	public BusinessDispatchMx(String id){
		super(id);
	}

	public BusinessDispatchMx(BusinessDispatch p){
		this.p = p;
	}

	public BusinessDispatch getP() {
		return p;
	}

	public void setP(BusinessDispatch p) {
		this.p = p;
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
		if(ck == null){
			ck = new BaseCangKu();
			ck.setId("");
			ck.setName("");
		}
		return ck;
	}

	public void setCk(BaseCangKu ck) {
		this.ck = ck;
	}
	
	@ExcelField(title="货位", fieldType=BaseHuoWei.class, value="hw.name", align=2, sort=10)
	public BaseHuoWei getHw() {
		if(hw == null){
			hw = new BaseHuoWei();
			hw.setId("");
			hw.setName("");
		}
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
		if(StringUtils.isEmpty(batchno)){
			batchno = "";
		}
		return batchno;
	}

	public void setBatchno(String batchno) {
		this.batchno = batchno;
	}
	
	@ExcelField(title="生产日期", align=2, sort=15)
	public String getScdate() {
		if(StringUtils.isEmpty(scdate)){
			scdate = "";
		}
		scdate = scdate.split(" ")[0];
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
	
	@ExcelField(title="销售订单号", align=2, sort=18)
	public String getCordercode() {
		return cordercode;
	}

	public void setCordercode(String cordercode) {
		this.cordercode = cordercode;
	}
	
	@ExcelField(title="销售订单行号", align=2, sort=19)
	public String getIrowno() {
		return irowno;
	}

	public void setIrowno(String irowno) {
		this.irowno = irowno;
	}
	
	@ExcelField(title="客户", align=2, sort=20)
	public BaseCustomer getCustomer() {
		return customer;
	}

	public void setCustomer(BaseCustomer customer) {
		this.customer = customer;
	}
	
	@ExcelField(title="部门", align=2, sort=21)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
}