/**
 * 
 */
package com.jeeplus.modules.business.product.archive.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 存货档案Entity
 * @author Jin
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class BusinessProductTypeOnlyRead extends TreeEntity<BusinessProductTypeOnlyRead> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 分类编号
	
	private List<BusinessProduct> businessProductList = Lists.newArrayList();		// 子表列表
	
	public BusinessProductTypeOnlyRead() {
		super();
	}

	public BusinessProductTypeOnlyRead(String id){
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public  BusinessProductTypeOnlyRead getParent() {
			return parent;
	}
	
	@Override
	public void setParent(BusinessProductTypeOnlyRead parent) {
		this.parent = parent;
		
	}
	
	public List<BusinessProduct> getBusinessProductList() {
		return businessProductList;
	}

	public void setBusinessProductList(List<BusinessProduct> businessProductList) {
		this.businessProductList = businessProductList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}