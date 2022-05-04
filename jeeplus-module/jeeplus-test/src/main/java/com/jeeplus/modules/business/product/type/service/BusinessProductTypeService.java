/**
 * 
 */
package com.jeeplus.modules.business.product.type.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.product.type.entity.BusinessProductType;
import com.jeeplus.modules.business.product.type.mapper.BusinessProductTypeMapper;

/**
 * 存货分类Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessProductTypeService extends TreeService<BusinessProductTypeMapper, BusinessProductType> {

	public BusinessProductType get(String id) {
		return super.get(id);
	}
	
	public List<BusinessProductType> findList(BusinessProductType businessProductType) {
		if (StringUtils.isNotBlank(businessProductType.getParentIds())){
			businessProductType.setParentIds(","+businessProductType.getParentIds()+",");
		}
		return super.findList(businessProductType);
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