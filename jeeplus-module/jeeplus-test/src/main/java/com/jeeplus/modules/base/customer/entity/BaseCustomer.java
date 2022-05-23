/**
 *
 */
package com.jeeplus.modules.base.customer.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 客户档案Entity
 * @author Jin
 */
public class BaseCustomer extends DataEntity<BaseCustomer> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 客户编码
	private String name;		// 客户名称
	private String address;		// 地址
	private String telephone;		// 电话
	private String person;		// 负责人
	private Date enddate;		// 停用日期
	
	public BaseCustomer() {
		super();
	}

	public BaseCustomer(String id){
		super(id);
	}

	@ExcelField(title="客户编码", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="客户名称", align=2, sort=7)
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
	
	@ExcelField(title="负责人", align=2, sort=10)
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
	
}