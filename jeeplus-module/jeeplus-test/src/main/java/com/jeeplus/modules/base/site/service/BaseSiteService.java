/**
 * 
 */
package com.jeeplus.modules.base.site.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.site.entity.BaseSite;
import com.jeeplus.modules.base.site.mapper.BaseSiteMapper;

/**
 * 工作站管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseSiteService extends CrudService<BaseSiteMapper, BaseSite> {

	public BaseSite get(String id) {
		return super.get(id);
	}
	
	public List<BaseSite> findList(BaseSite baseSite) {
		return super.findList(baseSite);
	}
	
	public Page<BaseSite> findPage(Page<BaseSite> page, BaseSite baseSite) {
		return super.findPage(page, baseSite);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseSite baseSite) {
		super.save(baseSite);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseSite baseSite) {
		super.delete(baseSite);
	}
	
}