/**
 * 
 */
package com.jeeplus.modules.base.factory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.factory.entity.BaseFactory;
import com.jeeplus.modules.base.factory.mapper.BaseFactoryMapper;

/**
 * 工厂管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseFactoryService extends CrudService<BaseFactoryMapper, BaseFactory> {

	public BaseFactory get(String id) {
		return super.get(id);
	}
	
	public List<BaseFactory> findList(BaseFactory baseFactory) {
		return super.findList(baseFactory);
	}
	
	public Page<BaseFactory> findPage(Page<BaseFactory> page, BaseFactory baseFactory) {
		return super.findPage(page, baseFactory);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseFactory baseFactory) {
		super.save(baseFactory);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseFactory baseFactory) {
		super.delete(baseFactory);
	}
	
}