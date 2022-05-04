/**
 * Jin
 */
package com.jeeplus.modules.business.product.type.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 存货分类Entity
 * @author Jin
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class BusinessProductType extends TreeEntity<BusinessProductType> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 分类编号
	
	
	public BusinessProductType() {
		super();
	}

	public BusinessProductType(String id){
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public  BusinessProductType getParent() {
			return parent;
	}
	
	@Override
	public void setParent(BusinessProductType parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}