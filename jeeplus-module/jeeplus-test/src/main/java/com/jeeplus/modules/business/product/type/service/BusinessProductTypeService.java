/**
 * 
 */
package com.jeeplus.modules.business.product.type.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.product.type.entity.BusinessProductType;
import com.jeeplus.modules.business.product.type.mapper.BusinessProductTypeMapper;

/**
 * 产品类型Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessProductTypeService extends CrudService<BusinessProductTypeMapper, BusinessProductType> {

	public BusinessProductType get(String id) {
		return super.get(id);
	}
	
	public List<BusinessProductType> findList(BusinessProductType businessProductType) {
		return super.findList(businessProductType);
	}
	
	public Page<BusinessProductType> findPage(Page<BusinessProductType> page, BusinessProductType businessProductType) {
		return super.findPage(page, businessProductType);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessProductType businessProductType) {
		super.save(businessProductType);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessProductType businessProductType) {
		super.delete(businessProductType);
	}
	
}