/**
 *
 */
package com.jeeplus.modules.qiyewx.base.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 部门员工Entity
 * @author Jin
 * @version 2021-08-25
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class QiYeWxDept extends TreeEntity<QiYeWxDept> {
	
	private static final long serialVersionUID = 1L;
	
	private List<QiYeWxEmployee> qiYeWxEmployeeList = Lists.newArrayList();		// 子表列表
	
	public QiYeWxDept() {
		super();
	}

	public QiYeWxDept(String id){
		super(id);
	}

	public  QiYeWxDept getParent() {
			return parent;
	}
	
	@Override
	public void setParent(QiYeWxDept parent) {
		this.parent = parent;
		
	}
	
	public List<QiYeWxEmployee> getQiYeWxEmployeeList() {
		return qiYeWxEmployeeList;
	}

	public void setQiYeWxEmployeeList(List<QiYeWxEmployee> qiYeWxEmployeeList) {
		this.qiYeWxEmployeeList = qiYeWxEmployeeList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}