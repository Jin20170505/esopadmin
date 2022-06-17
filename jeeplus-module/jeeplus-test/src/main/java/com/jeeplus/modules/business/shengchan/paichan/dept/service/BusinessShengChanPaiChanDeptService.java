/**
 * 
 */
package com.jeeplus.modules.business.shengchan.paichan.dept.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.shengchan.paichan.dept.entity.BusinessShengChanPaiChanDept;
import com.jeeplus.modules.business.shengchan.paichan.dept.mapper.BusinessShengChanPaiChanDeptMapper;

/**
 * 排产部门Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanPaiChanDeptService extends CrudService<BusinessShengChanPaiChanDeptMapper, BusinessShengChanPaiChanDept> {

	public BusinessShengChanPaiChanDept get(String id) {
		return super.get(id);
	}
	
	public List<BusinessShengChanPaiChanDept> findList(BusinessShengChanPaiChanDept businessShengChanPaiChanDept) {
		return super.findList(businessShengChanPaiChanDept);
	}
	
	public Page<BusinessShengChanPaiChanDept> findPage(Page<BusinessShengChanPaiChanDept> page, BusinessShengChanPaiChanDept businessShengChanPaiChanDept) {
		return super.findPage(page, businessShengChanPaiChanDept);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessShengChanPaiChanDept businessShengChanPaiChanDept) {
		super.save(businessShengChanPaiChanDept);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanPaiChanDept businessShengChanPaiChanDept) {
		super.delete(businessShengChanPaiChanDept);
	}

	public boolean hasPaichanDept(String dept){
		Integer i = mapper.hasPaichanDept(dept);
		return i!=null && i==1;
	}
}