/**
 * 
 */
package com.jeeplus.modules.base.classgroup.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroup;
import com.jeeplus.modules.base.classgroup.mapper.BaseClassGroupMapper;

/**
 * 班组管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseClassGroupService extends CrudService<BaseClassGroupMapper, BaseClassGroup> {

	public BaseClassGroup get(String id) {
		return super.get(id);
	}
	
	public List<BaseClassGroup> findList(BaseClassGroup baseClassGroup) {
		return super.findList(baseClassGroup);
	}
	
	public Page<BaseClassGroup> findPage(Page<BaseClassGroup> page, BaseClassGroup baseClassGroup) {
		return super.findPage(page, baseClassGroup);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseClassGroup baseClassGroup) {
		super.save(baseClassGroup);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseClassGroup baseClassGroup) {
		super.delete(baseClassGroup);
	}
	
}