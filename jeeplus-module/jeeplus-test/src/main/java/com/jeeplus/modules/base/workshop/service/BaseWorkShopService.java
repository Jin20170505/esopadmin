/**
 * 
 */
package com.jeeplus.modules.base.workshop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.workshop.entity.BaseWorkShop;
import com.jeeplus.modules.base.workshop.mapper.BaseWorkShopMapper;

/**
 * 车间管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseWorkShopService extends CrudService<BaseWorkShopMapper, BaseWorkShop> {

	public BaseWorkShop get(String id) {
		return super.get(id);
	}
	
	public List<BaseWorkShop> findList(BaseWorkShop baseWorkShop) {
		return super.findList(baseWorkShop);
	}
	
	public Page<BaseWorkShop> findPage(Page<BaseWorkShop> page, BaseWorkShop baseWorkShop) {
		return super.findPage(page, baseWorkShop);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseWorkShop baseWorkShop) {
		super.save(baseWorkShop);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseWorkShop baseWorkShop) {
		super.delete(baseWorkShop);
	}
	
}