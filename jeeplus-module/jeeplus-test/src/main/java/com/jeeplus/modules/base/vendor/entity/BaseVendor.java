/**
 *
 */
package com.jeeplus.modules.base.vendor.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 供应商档案Entity
 * @author Jin
 */
public class BaseVendor extends DataEntity<BaseVendor> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 供应商编码
	private String name;		// 供应商名称
	private String address;		// 地址
	private String telephone;		// 电话
	private String person;		// 联系人
	private Date enddate;		// 停用日期
	private List<BaseCkOfVendor> ckOfVendors = Lists.newArrayList();
	public BaseVendor() {
		super();
	}

	public BaseVendor(String id){
		super(id);
	}

	@ExcelField(title="供应商编码", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="供应商名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="地址", align=2, sort=8)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="电话", align=2, sort=9)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@ExcelField(title="联系人", align=2, sort=10)
	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="停用日期不能为空")
	@ExcelField(title="停用日期", align=2, sort=11)
	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public List<BaseCkOfVendor> getCkOfVendors() {
		return ckOfVendors;
	}

	public BaseVendor setCkOfVendors(List<BaseCkOfVendor> ckOfVendors) {
		this.ckOfVendors = ckOfVendors;
		return this;
	}
}