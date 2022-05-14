/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.chuku.lingliao.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 领料单明细Entity
 * @author Jin
 * @version 2022-05-13
 */
public class BusinessChuKuLingLiaoMx extends DataEntity<BusinessChuKuLingLiaoMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessChuKuLingLiao p;		// 父键 父类
	private String sclinecode;		// 生产行号
	private String cinvcode;		// 存货编码
	private String cinvname;		// 存货名称
	private Double gdnum;		// 工单数量
	private Double cknum;		// 出库数量
	private BaseHuoWei huowei;		// 货位
	private Double xcnum;		// 现存量
	private String schid;		// 生产行ID
	private String sych;		// 是否同步
	
	public BusinessChuKuLingLiaoMx() {
		super();
	}

	public BusinessChuKuLingLiaoMx(String id){
		super(id);
	}

	public BusinessChuKuLingLiaoMx(BusinessChuKuLingLiao p){
		this.p = p;
	}

	public BusinessChuKuLingLiao getP() {
		return p;
	}

	public void setP(BusinessChuKuLingLiao p) {
		this.p = p;
	}
	
	@ExcelField(title="生产行号", align=2, sort=7)
	public String getSclinecode() {
		return sclinecode;
	}

	public void setSclinecode(String sclinecode) {
		this.sclinecode = sclinecode;
	}
	
	@ExcelField(title="存货编码", align=2, sort=8)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="存货名称", align=2, sort=9)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@NotNull(message="工单数量不能为空")
	@ExcelField(title="工单数量", align=2, sort=10)
	public Double getGdnum() {
		return gdnum;
	}

	public void setGdnum(Double gdnum) {
		this.gdnum = gdnum;
	}
	
	@NotNull(message="出库数量不能为空")
	@ExcelField(title="出库数量", align=2, sort=11)
	public Double getCknum() {
		return cknum;
	}

	public void setCknum(Double cknum) {
		this.cknum = cknum;
	}
	
	@NotNull(message="货位不能为空")
	@ExcelField(title="货位", fieldType=BaseHuoWei.class, value="huowei.name", align=2, sort=12)
	public BaseHuoWei getHuowei() {
		return huowei;
	}

	public void setHuowei(BaseHuoWei huowei) {
		this.huowei = huowei;
	}
	
	@NotNull(message="现存量不能为空")
	@ExcelField(title="现存量", align=2, sort=13)
	public Double getXcnum() {
		return xcnum;
	}

	public void setXcnum(Double xcnum) {
		this.xcnum = xcnum;
	}
	
	@ExcelField(title="生产行ID", align=2, sort=15)
	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}
	
	@ExcelField(title="是否同步", align=2, sort=16)
	public String getSych() {
		return sych;
	}

	public void setSych(String sych) {
		this.sych = sych;
	}
	
}