/**
 *
 */
package com.jeeplus.modules.base.unit.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 计量单位Entity
 * @author Jin
 */
public class BaseUnit extends DataEntity<BaseUnit> {
	
	private static final long serialVersionUID = 1L;
	private String code;  // 编号
	private String name;		// 名称
	private String useable;		// 是否可用
	
	public BaseUnit() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseUnit(String id){
		super(id);
	}
	@ExcelField(title="编码", align=2, sort=5)
	public String getCode() {
		return code;
	}

	public BaseUnit setCode(String code) {
		this.code = code;
		return this;
	}

	@ExcelField(title="名称", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="是否可用", dictType="yes_no", align=2, sort=7)
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
}