/**
 *
 */
package com.jeeplus.modules.base.factory.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工厂管理Entity
 * @author Jin
 */
public class BaseFactory extends DataEntity<BaseFactory> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 工厂名称
	private String manger;		// 负责人
	
	public BaseFactory() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseFactory(String id){
		super(id);
	}

	@ExcelField(title="工厂名称", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="负责人", align=2, sort=7)
	public String getManger() {
		return manger;
	}

	public void setManger(String manger) {
		this.manger = manger;
	}
	
}