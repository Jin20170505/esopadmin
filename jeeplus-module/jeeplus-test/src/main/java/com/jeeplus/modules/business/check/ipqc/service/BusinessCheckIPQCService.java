/**
 * 
 */
package com.jeeplus.modules.business.check.ipqc.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.check.ipqc.entity.BusinessCheckIPQC;
import com.jeeplus.modules.business.check.ipqc.mapper.BusinessCheckIPQCMapper;

/**
 * IPQC检验Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessCheckIPQCService extends CrudService<BusinessCheckIPQCMapper, BusinessCheckIPQC> {

	public BusinessCheckIPQC get(String id) {
		return super.get(id);
	}
	
	public List<BusinessCheckIPQC> findList(BusinessCheckIPQC businessCheckIPQC) {
		return super.findList(businessCheckIPQC);
	}
	
	public Page<BusinessCheckIPQC> findPage(Page<BusinessCheckIPQC> page, BusinessCheckIPQC businessCheckIPQC) {
		return super.findPage(page, businessCheckIPQC);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessCheckIPQC businessCheckIPQC) {
		super.save(businessCheckIPQC);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessCheckIPQC businessCheckIPQC) {
		super.delete(businessCheckIPQC);
	}
	
}