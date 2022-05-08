/**
 *
 */
package com.jeeplus.modules.base.workshop.entity;

import com.jeeplus.modules.base.factory.entity.BaseFactory;
import javax.validation.constraints.NotNull;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 车间管理Entity
 * @author Jin
 */
public class BaseWorkShop extends DataEntity<BaseWorkShop> {
	
	private static final long serialVersionUID = 1L;
	private BaseFactory factory;		// 所属工厂
	private String code; // 编号
	private String name;		// 车间名称
	private String manger;		// 负责人
	
	public BaseWorkShop() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseWorkShop(String id){
		super(id);
	}

	@NotNull(message="所属工厂不能为空")
	@ExcelField(title="所属工厂", fieldType=BaseFactory.class, value="factory.name", align=2, sort=5)
	public BaseFactory getFactory() {
		return factory;
	}

	public void setFactory(BaseFactory factory) {
		this.factory = factory;
	}
	@ExcelField(title="车间编号", align=2, sort=7)
	public String getCode() {
		return code;
	}

	public BaseWorkShop setCode(String code) {
		this.code = code;
		return this;
	}

	@ExcelField(title="车间名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="负责人", align=2, sort=10)
	public String getManger() {
		return manger;
	}

	public void setManger(String manger) {
		this.manger = manger;
	}
	
}