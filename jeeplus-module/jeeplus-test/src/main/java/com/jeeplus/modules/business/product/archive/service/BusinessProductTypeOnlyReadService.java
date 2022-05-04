/**
 * 
 */
package com.jeeplus.modules.business.product.archive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.product.archive.entity.BusinessProductTypeOnlyRead;
import com.jeeplus.modules.business.product.archive.mapper.BusinessProductTypeOnlyReadMapper;

/**
 * 存货档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessProductTypeOnlyReadService extends TreeService<BusinessProductTypeOnlyReadMapper, BusinessProductTypeOnlyRead> {

	public BusinessProductTypeOnlyRead get(String id) {
		return super.get(id);
	}
	
	public List<BusinessProductTypeOnlyRead> findList(BusinessProductTypeOnlyRead businessProductTypeOnlyRead) {
		if (StringUtils.isNotBlank(businessProductTypeOnlyRead.getParentIds())){
			businessProductTypeOnlyRead.setParentIds(","+businessProductTypeOnlyRead.getParentIds()+",");
		}
		return super.findList(businessProductTypeOnlyRead);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessProductTypeOnlyRead businessProductTypeOnlyRead) {
		super.save(businessProductTypeOnlyRead);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessProductTypeOnlyRead businessProductTypeOnlyRead) {
		super.delete(businessProductTypeOnlyRead);
	}
	
}