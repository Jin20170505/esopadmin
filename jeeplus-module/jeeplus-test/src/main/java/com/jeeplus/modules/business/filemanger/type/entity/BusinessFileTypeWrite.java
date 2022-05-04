/**
 * Jin
 */
package com.jeeplus.modules.business.filemanger.type.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 文件类型Entity
 * @author Jin
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class BusinessFileTypeWrite extends TreeEntity<BusinessFileTypeWrite> {
	
	private static final long serialVersionUID = 1L;
	
	
	public BusinessFileTypeWrite() {
		super();
	}

	public BusinessFileTypeWrite(String id){
		super(id);
	}

	public  BusinessFileTypeWrite getParent() {
			return parent;
	}
	
	@Override
	public void setParent(BusinessFileTypeWrite parent) {
		this.parent = parent;
		
	}
	
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}