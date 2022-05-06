/**
 * 
 */
package com.jeeplus.modules.business.baogong.record.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.baogong.record.entity.BusinessBaoGongRecord;
import com.jeeplus.modules.business.baogong.record.mapper.BusinessBaoGongRecordMapper;

/**
 * 员工报工Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessBaoGongRecordService extends CrudService<BusinessBaoGongRecordMapper, BusinessBaoGongRecord> {

	public BusinessBaoGongRecord get(String id) {
		return super.get(id);
	}
	
	public List<BusinessBaoGongRecord> findList(BusinessBaoGongRecord businessBaoGongRecord) {
		return super.findList(businessBaoGongRecord);
	}
	
	public Page<BusinessBaoGongRecord> findPage(Page<BusinessBaoGongRecord> page, BusinessBaoGongRecord businessBaoGongRecord) {
		return super.findPage(page, businessBaoGongRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessBaoGongRecord businessBaoGongRecord) {
		super.save(businessBaoGongRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessBaoGongRecord businessBaoGongRecord) {
		super.delete(businessBaoGongRecord);
	}
	
}