/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 存货档案Entity
 * @author Jin
 * @version 2022-05-04
 */
public class BusinessProduct extends DataEntity<BusinessProduct> {
	
	private static final long serialVersionUID = 1L;
	private BusinessProductTypeOnlyRead type;		// 存货分类 父类
	private String cinvaddcode; // 代码
	private String code;		// 存货编码
	private String name;		// 存货名称
	private String specification;		// 规格型号
	private String unit;		// 计量单位
	private String daynum; // 日生产量
	private String sccall;	// 是否生产叫料
	public BusinessProduct() {
		super();
	}

	public BusinessProduct(String id){
		super(id);
	}

	public BusinessProduct(BusinessProductTypeOnlyRead type){
		this.type = type;
	}

	public BusinessProductTypeOnlyRead getType() {
		return type;
	}

	public void setType(BusinessProductTypeOnlyRead type) {
		this.type = type;
	}
	@ExcelField(title="存货代码", align=2, sort=6)
	public String getCinvaddcode() {
		return cinvaddcode;
	}

	public BusinessProduct setCinvaddcode(String cinvaddcode) {
		this.cinvaddcode = cinvaddcode;
		return this;
	}

	@ExcelField(title="存货编码", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="存货名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="规格型号", align=2, sort=9)
	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDaynum() {
		return daynum;
	}

	public BusinessProduct setDaynum(String daynum) {
		this.daynum = daynum;
		return this;
	}

	@ExcelField(title="计量单位", align=2, sort=10)
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getSccall() {
		return sccall;
	}

	public BusinessProduct setSccall(String sccall) {
		this.sccall = sccall;
		return this;
	}
}