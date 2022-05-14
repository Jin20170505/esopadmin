/**
 * 
 */
package com.jeeplus.modules.business.nengyuan.air.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.nengyuan.air.entity.BusinessNengYuanAir;
import com.jeeplus.modules.business.nengyuan.air.mapper.BusinessNengYuanAirMapper;

/**
 * 气能管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessNengYuanAirService extends CrudService<BusinessNengYuanAirMapper, BusinessNengYuanAir> {

	public BusinessNengYuanAir get(String id) {
		return super.get(id);
	}
	
	public List<BusinessNengYuanAir> findList(BusinessNengYuanAir businessNengYuanAir) {
		return super.findList(businessNengYuanAir);
	}
	
	public Page<BusinessNengYuanAir> findPage(Page<BusinessNengYuanAir> page, BusinessNengYuanAir businessNengYuanAir) {
		return super.findPage(page, businessNengYuanAir);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessNengYuanAir businessNengYuanAir) {
		super.save(businessNengYuanAir);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessNengYuanAir businessNengYuanAir) {
		super.delete(businessNengYuanAir);
	}
	
}