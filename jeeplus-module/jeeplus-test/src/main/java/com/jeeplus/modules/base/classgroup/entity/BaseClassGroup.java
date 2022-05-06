/**
 *
 */
package com.jeeplus.modules.base.classgroup.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 班组管理Entity
 * @author Jin
 */
public class BaseClassGroup extends DataEntity<BaseClassGroup> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 班组编号
	private String name;		// 班组名称
	
	public BaseClassGroup() {
		super();
	}

	public BaseClassGroup(String id){
		super(id);
	}

	@ExcelField(title="班组编号", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="班组名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}