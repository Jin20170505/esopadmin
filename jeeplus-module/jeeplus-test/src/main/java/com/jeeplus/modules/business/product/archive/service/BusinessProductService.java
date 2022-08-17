/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.service;

import java.util.ArrayList;
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
		List<BusinessProduct> list = new ArrayList<>(data.size());
		data.forEach(d->{
				BusinessProduct product = new BusinessProduct();
				product.setCinvaddcode(d.getcInvAddCode());
				product.setCode(d.getcInvCode());
				product.setName(d.getcInvName());
				product.setUnit(d.getcComUnitName());
				product.setSpecification(d.getcInvStd());
				product.setDaynum(d.getDaynum());
				product.setKezhong(d.getKezhong());
				product.setCbprice(d.getDprice());
				product.setType(new BusinessProductTypeOnlyRead(d.getcInvCCode()));
				product.preInsert();
				product.setId(d.getcInvCode());
				list.add(product);
		});
		if(!list.isEmpty()){
			int i = 0;
			int j = 0;
			int mlen = list.size();
			while (i<mlen){
				j = i;
				i = i+300;
				if(i>=mlen){
					mapper.batchInsert(list.subList(j,mlen));
				}else {
					mapper.batchInsert(list.subList(j,i));
				}
			}
		}

	}

}