/**
 *
 */
package com.jeeplus.modules.base.erp.updatetime.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * ERP更新时间Entity
 * @author Jin
 */
public class BaseU8UpdateTime extends DataEntity<BaseU8UpdateTime> {
	
	private static final long serialVersionUID = 1L;
	// 01：工艺路线
	private String code;		// 业务编码
	private String name;		// 业务名称
	private Date lastTime;		// 更新最新时间
	
	public BaseU8UpdateTime() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseU8UpdateTime(String id){
		super(id);
	}

	@ExcelField(title="业务编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="业务名称", align=2, sort=2)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="更新最新时间", align=2, sort=3)
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	
}