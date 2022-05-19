/**
 * 
 */
package com.jeeplus.modules.business.product.type.service;

import java.util.List;

import com.jeeplus.modules.u8data.inventory.entity.U8InventoryClass;
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

	@Transactional(readOnly = false)
	public void sychu8(List<U8InventoryClass> data){
		data.forEach(t->{
			Integer i = mapper.hasByCode(t.getcInvCCode());
			if(i==null){
				BusinessProductType type = new BusinessProductType();
				type.setCode(t.getcInvCCode());
				type.setName(t.getcInvCName());
				type.setIdType(t.getcInvCCode());
				type.setParent(new BusinessProductType(t.getParentCode()));
				type.preInsert();
				type.setId(t.getcInvCCode());
				mapper.insert(type);
			}
		});
	}

}