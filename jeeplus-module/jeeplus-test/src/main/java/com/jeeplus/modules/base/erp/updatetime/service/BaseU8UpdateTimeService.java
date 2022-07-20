/**
 * 
 */
package com.jeeplus.modules.base.erp.updatetime.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.erp.updatetime.entity.BaseU8UpdateTime;
import com.jeeplus.modules.base.erp.updatetime.mapper.BaseU8UpdateTimeMapper;

/**
 * ERP更新时间Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseU8UpdateTimeService extends CrudService<BaseU8UpdateTimeMapper, BaseU8UpdateTime> {

	public BaseU8UpdateTime get(String id) {
		return super.get(id);
	}
	
	public List<BaseU8UpdateTime> findList(BaseU8UpdateTime baseU8UpdateTime) {
		return super.findList(baseU8UpdateTime);
	}
	
	public Page<BaseU8UpdateTime> findPage(Page<BaseU8UpdateTime> page, BaseU8UpdateTime baseU8UpdateTime) {
		return super.findPage(page, baseU8UpdateTime);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseU8UpdateTime baseU8UpdateTime) {
		super.save(baseU8UpdateTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseU8UpdateTime baseU8UpdateTime) {
		super.delete(baseU8UpdateTime);
	}

	public BaseU8UpdateTime getByCode(String code){
		return mapper.getByCode(code);
	}
	
}