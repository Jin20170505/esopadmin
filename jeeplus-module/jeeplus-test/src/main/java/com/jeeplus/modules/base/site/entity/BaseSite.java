/**
 *
 */
package com.jeeplus.modules.base.site.entity;

import com.jeeplus.modules.base.factory.entity.BaseFactory;
import javax.validation.constraints.NotNull;
import com.jeeplus.modules.base.workshop.entity.BaseWorkShop;
import com.jeeplus.modules.base.productionline.entity.BaseProductionLine;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 工作站管理Entity
 * @author Jin
 */
public class BaseSite extends DataEntity<BaseSite> {
	
	private static final long serialVersionUID = 1L;
	private BaseFactory factory;		// 所属工厂
	private BaseWorkShop workshop;		// 所属车间
	private BaseProductionLine line;		// 所属产线
	private String code; // 编号
	private String name;		// 工作站名称
	
	public BaseSite() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BaseSite(String id){
		super(id);
	}

	@NotNull(message="所属工厂不能为空")
	@ExcelField(title="所属工厂", fieldType=BaseFactory.class, value="factory.name", align=2, sort=6)
	public BaseFactory getFactory() {
		return factory;
	}

	public void setFactory(BaseFactory factory) {
		this.factory = factory;
	}
	
	@NotNull(message="所属车间不能为空")
	@ExcelField(title="所属车间", fieldType=BaseWorkShop.class, value="workshop.name", align=2, sort=7)
	public BaseWorkShop getWorkshop() {
		return workshop;
	}

	public void setWorkshop(BaseWorkShop workshop) {
		this.workshop = workshop;
	}
	
	@NotNull(message="所属产线不能为空")
	@ExcelField(title="所属产线", fieldType=BaseProductionLine.class, value="line.name", align=2, sort=8)
	public BaseProductionLine getLine() {
		return line;
	}

	public void setLine(BaseProductionLine line) {
		this.line = line;
	}

	@ExcelField(title="工作站编号", align=2, sort=10)
	public String getCode() {
		return code;
	}

	public BaseSite setCode(String code) {
		this.code = code;
		return this;
	}

	@ExcelField(title="工作站名称", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}