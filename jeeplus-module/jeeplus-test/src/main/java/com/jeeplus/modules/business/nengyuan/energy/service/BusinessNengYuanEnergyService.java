/**
 * 
 */
package com.jeeplus.modules.business.nengyuan.energy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.nengyuan.energy.entity.BusinessNengYuanEnergy;
import com.jeeplus.modules.business.nengyuan.energy.mapper.BusinessNengYuanEnergyMapper;

/**
 * 电能管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessNengYuanEnergyService extends CrudService<BusinessNengYuanEnergyMapper, BusinessNengYuanEnergy> {

	public BusinessNengYuanEnergy get(String id) {
		return super.get(id);
	}
	
	public List<BusinessNengYuanEnergy> findList(BusinessNengYuanEnergy businessNengYuanEnergy) {
		return super.findList(businessNengYuanEnergy);
	}
	
	public Page<BusinessNengYuanEnergy> findPage(Page<BusinessNengYuanEnergy> page, BusinessNengYuanEnergy businessNengYuanEnergy) {
		return super.findPage(page, businessNengYuanEnergy);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessNengYuanEnergy businessNengYuanEnergy) {
		super.save(businessNengYuanEnergy);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessNengYuanEnergy businessNengYuanEnergy) {
		super.delete(businessNengYuanEnergy);
	}
	
}