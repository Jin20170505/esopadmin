/**
 * 
 */
package com.jeeplus.modules.business.filemanger.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 文件管理Entity
 * @author Jin
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class BusinessFileType extends TreeEntity<BusinessFileType> {
	
	private static final long serialVersionUID = 1L;
	
	private List<BussinessFileManger> bussinessFileMangerList = Lists.newArrayList();		// 子表列表
	
	public BusinessFileType() {
		super();
	}

	public BusinessFileType(String id){
		super(id);
	}

	public  BusinessFileType getParent() {
			return parent;
	}
	
	@Override
	public void setParent(BusinessFileType parent) {
		this.parent = parent;
		
	}
	
	public List<BussinessFileManger> getBussinessFileMangerList() {
		return bussinessFileMangerList;
	}

	public void setBussinessFileMangerList(List<BussinessFileManger> bussinessFileMangerList) {
		this.bussinessFileMangerList = bussinessFileMangerList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}