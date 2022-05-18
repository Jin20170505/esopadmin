/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.service;

import java.util.List;

import com.jeeplus.modules.business.product.archive.entity.BusinessProductTypeOnlyRead;
import com.jeeplus.modules.u8data.inventory.entity.U8Inventory;
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

	@Transactional(readOnly = false)
	public void sychu8(List<U8Inventory> data){
		data.forEach(d->{
			Integer i = mapper.hasByCode(d.getcInvCode());
			if(i==null){
				BusinessProduct product = new BusinessProduct();
				product.setCode(d.getcInvCode());
				product.setName(d.getcInvName());
				product.setUnit(d.getcComUnitName());
				product.setSpecification(d.getcInvStd());
				product.setType(new BusinessProductTypeOnlyRead(d.getcInvCCode()));
				product.preInsert();
				product.setId(d.getcInvCode());
				mapper.insert(product);
			}
		});

	}

}