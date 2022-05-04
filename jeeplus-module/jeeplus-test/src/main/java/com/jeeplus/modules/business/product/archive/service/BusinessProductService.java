/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.business.product.archive.mapper.BusinessProductMapper;

/**
 * 存货档案Service
 * @author Jin
 * @version 2022-05-04
 */
@Service
@Transactional(readOnly = true)
public class BusinessProductService extends CrudService<BusinessProductMapper, BusinessProduct> {

	public BusinessProduct get(String id) {
		return super.get(id);
	}
	
	public List<BusinessProduct> findList(BusinessProduct businessProduct) {
		return super.findList(businessProduct);
	}
	
	public Page<BusinessProduct> findPage(Page<BusinessProduct> page, BusinessProduct businessProduct) {
		return super.findPage(page, businessProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessProduct businessProduct) {
		super.save(businessProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessProduct businessProduct) {
		super.delete(businessProduct);
	}
	
}