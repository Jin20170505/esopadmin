/**
 * 
 */
package com.jeeplus.modules.base.dept.time.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.dept.time.entity.BaseDeptTime;
import com.jeeplus.modules.base.dept.time.mapper.BaseDeptTimeMapper;

/**
 * 部门时间Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseDeptTimeService extends CrudService<BaseDeptTimeMapper, BaseDeptTime> {

	public BaseDeptTime get(String id) {
		return super.get(id);
	}
	
	public List<BaseDeptTime> findList(BaseDeptTime baseDeptTime) {
		return super.findList(baseDeptTime);
	}
	
	public Page<BaseDeptTime> findPage(Page<BaseDeptTime> page, BaseDeptTime baseDeptTime) {
		return super.findPage(page, baseDeptTime);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseDeptTime baseDeptTime) {
		super.save(baseDeptTime);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseDeptTime baseDeptTime) {
		super.delete(baseDeptTime);
	}
	
}