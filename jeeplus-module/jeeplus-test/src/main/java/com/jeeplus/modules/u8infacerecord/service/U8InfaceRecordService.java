/**
 * 
 */
package com.jeeplus.modules.u8infacerecord.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.u8infacerecord.entity.U8InfaceRecord;
import com.jeeplus.modules.u8infacerecord.mapper.U8InfaceRecordMapper;

/**
 * ERP接口记录Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class U8InfaceRecordService extends CrudService<U8InfaceRecordMapper, U8InfaceRecord> {

	public U8InfaceRecord get(String id) {
		return super.get(id);
	}
	
	public List<U8InfaceRecord> findList(U8InfaceRecord u8InfaceRecord) {
		return super.findList(u8InfaceRecord);
	}
	
	public Page<U8InfaceRecord> findPage(Page<U8InfaceRecord> page, U8InfaceRecord u8InfaceRecord) {
		return super.findPage(page, u8InfaceRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(U8InfaceRecord u8InfaceRecord) {
		super.save(u8InfaceRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(U8InfaceRecord u8InfaceRecord) {
		super.delete(u8InfaceRecord);
	}
	
}