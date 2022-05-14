/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ruku.product.entity;

import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 产成品入库单明细Entity
 * @author Jin
 * @version 2022-05-13
 */
public class BusinessRuKuProductMx extends DataEntity<BusinessRuKuProductMx> {
	
	private static final long serialVersionUID = 1L;
	private BusinessRuKuProduct p;		// 父键 父类
	private String linecode;		// 生产订单行号
	private String cinvcode;		// 产品编码
	private String cinvname;		// 产品名称
	private String cinvstd;		// 规格型号
	private Double num;		// 数量
	private String unit;		// 单位
	private BaseHuoWei huowei;		// 货位
	private String sych;		// 是否同步
	private String schid;		// 生产行ID
	
	public BusinessRuKuProductMx() {
		super();
	}

	public BusinessRuKuProductMx(String id){
		super(id);
	}

	public BusinessRuKuProductMx(BusinessRuKuProduct p){
		this.p = p;
	}

	public BusinessRuKuProduct getP() {
		return p;
	}

	public void setP(BusinessRuKuProduct p) {
		this.p = p;
	}
	
	@ExcelField(title="生产订单行号", align=2, sort=7)
	public String getLinecode() {
		return linecode;
	}

	public void setLinecode(String linecode) {
		this.linecode = linecode;
	}
	
	@ExcelField(title="产品编码", align=2, sort=8)
	public String getCinvcode() {
		return cinvcode;
	}

	public void setCinvcode(String cinvcode) {
		this.cinvcode = cinvcode;
	}
	
	@ExcelField(title="产品名称", align=2, sort=9)
	public String getCinvname() {
		return cinvname;
	}

	public void setCinvname(String cinvname) {
		this.cinvname = cinvname;
	}
	
	@ExcelField(title="规格型号", align=2, sort=10)
	public String getCinvstd() {
		return cinvstd;
	}

	public void setCinvstd(String cinvstd) {
		this.cinvstd = cinvstd;
	}
	
	@NotNull(message="数量不能为空")
	@ExcelField(title="数量", align=2, sort=11)
	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}
	
	@ExcelField(title="单位", align=2, sort=12)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@NotNull(message="货位不能为空")
	@ExcelField(title="货位", fieldType=BaseHuoWei.class, value="huowei.name", align=2, sort=13)
	public BaseHuoWei getHuowei() {
		return huowei;
	}

	public void setHuowei(BaseHuoWei huowei) {
		this.huowei = huowei;
	}
	
	@ExcelField(title="是否同步", align=2, sort=15)
	public String getSych() {
		return sych;
	}

	public void setSych(String sych) {
		this.sych = sych;
	}
	
	@ExcelField(title="生产行ID", align=2, sort=16)
	public String getSchid() {
		return schid;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}
	
}