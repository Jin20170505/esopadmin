/**
 * 
 */
package com.jeeplus.modules.base.unit.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.unit.entity.BaseUnit;
import com.jeeplus.modules.base.unit.mapper.BaseUnitMapper;

/**
 * 计量单位Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseUnitService extends CrudService<BaseUnitMapper, BaseUnit> {

	public BaseUnit get(String id) {
		return super.get(id);
	}
	
	public List<BaseUnit> findList(BaseUnit baseUnit) {
		return super.findList(baseUnit);
	}
	
	public Page<BaseUnit> findPage(Page<BaseUnit> page, BaseUnit baseUnit) {
		return super.findPage(page, baseUnit);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseUnit baseUnit) {
		super.save(baseUnit);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseUnit baseUnit) {
		super.delete(baseUnit);
	}
	
}