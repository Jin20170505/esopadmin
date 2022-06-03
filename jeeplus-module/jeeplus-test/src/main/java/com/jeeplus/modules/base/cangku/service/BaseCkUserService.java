/**
 * 
 */
package com.jeeplus.modules.base.cangku.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.cangku.entity.BaseCkUser;
import com.jeeplus.modules.base.cangku.mapper.BaseCkUserMapper;

/**
 * 仓库可见人Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseCkUserService extends CrudService<BaseCkUserMapper, BaseCkUser> {

	public BaseCkUser get(String id) {
		return super.get(id);
	}
	
	public List<BaseCkUser> findList(BaseCkUser baseCkUser) {
		return super.findList(baseCkUser);
	}
	
	public Page<BaseCkUser> findPage(Page<BaseCkUser> page, BaseCkUser baseCkUser) {
		return super.findPage(page, baseCkUser);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseCkUser baseCkUser) {
		super.save(baseCkUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseCkUser baseCkUser) {
		super.delete(baseCkUser);
	}
	
}