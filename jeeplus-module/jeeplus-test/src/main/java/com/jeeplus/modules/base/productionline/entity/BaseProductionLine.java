/**
 *
 */
package com.jeeplus.modules.base.productionline.entity;

import com.jeeplus.modules.base.factory.entity.BaseFactory;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.workshop.entity.BaseWorkShop;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 产线管理Entity
 * @author Jin
 */
public class BaseProductionLine extends DataEntity<BaseProductionLine> {
	
	private static final long serialVersionUID = 1L;
	private BaseFactory factory;		// 所属工厂
	private BaseWorkShop workshop;		// 所属车间
	private String name;		// 产线名称
	private String manger;		// 负责人
	
	public BaseProductionLine() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseProductionLine(String id){
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
	
	@NotNull(message="所属车间不能为空")
	@ExcelField(title="所属车间", fieldType=BaseWorkShop.class, value="workshop.name", align=2, sort=6)
	public BaseWorkShop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(BaseWorkShop workshop) {
		this.workshop = workshop;
	}
	
	@ExcelField(title="产线名称", align=2, sort=8)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="负责人", align=2, sort=9)
	public String getManger() {
		return manger;
	}

	public void setManger(String manger) {
		this.manger = manger;
	}
	
}