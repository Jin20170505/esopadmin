/**
 *
 */
package com.jeeplus.modules.business.product.archive.entity;

import com.jeeplus.modules.business.product.type.entity.BusinessProductType;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 产品档案Entity
 * @author Jin
 */
public class BusinessProduct extends DataEntity<BusinessProduct> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 产品编码
	private String name;		// 产品名称
	private BusinessProductType type;		// 产品类型
	private List<BusinessRoute> businessRouteList = Lists.newArrayList();		// 子表列表
	
	public BusinessProduct() {
		super();
	}

	public BusinessProduct(String id){
		super(id);
	}

	@ExcelField(title="产品编码", align=2, sort=6)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="产品名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="产品类型不能为空")
	@ExcelField(title="产品类型", fieldType=BusinessProductType.class, value="type.name", align=2, sort=8)
	public BusinessProductType getType() {
		return type;
	}

	public void setType(BusinessProductType type) {
		this.type = type;
	}
	
	public List<BusinessRoute> getBusinessRouteList() {
		return businessRouteList;
	}

	public void setBusinessRouteList(List<BusinessRoute> businessRouteList) {
		this.businessRouteList = businessRouteList;
	}
}