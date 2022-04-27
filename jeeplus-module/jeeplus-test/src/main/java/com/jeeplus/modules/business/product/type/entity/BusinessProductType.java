/**
 *
 */
package com.jeeplus.modules.business.product.type.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 产品类型Entity
 * @author Jin
 */
public class BusinessProductType extends DataEntity<BusinessProductType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 类型
	
	public BusinessProductType() {
		super();
		this.setIdType(IDTYPE_AUTO);
	}

	public BusinessProductType(String id){
		super(id);
	}

	@ExcelField(title="类型", align=2, sort=6)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}