/**
 * 
 */
package com.jeeplus.modules.base.huowei.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;

/**
 * 货位档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseHuoWeiService extends CrudService<BaseHuoWeiMapper, BaseHuoWei> {

	public BaseHuoWei get(String id) {
		return super.get(id);
	}
	
	public List<BaseHuoWei> findList(BaseHuoWei baseHuoWei) {
		return super.findList(baseHuoWei);
	}
	
	public Page<BaseHuoWei> findPage(Page<BaseHuoWei> page, BaseHuoWei baseHuoWei) {
		return super.findPage(page, baseHuoWei);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseHuoWei baseHuoWei) {
		super.save(baseHuoWei);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseHuoWei baseHuoWei) {
		super.delete(baseHuoWei);
	}
	
}