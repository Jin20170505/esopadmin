/**
 * 
 */
package com.jeeplus.modules.base.site.hegelv.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.site.hegelv.entity.BaseSiteHegelv;
import com.jeeplus.modules.base.site.hegelv.mapper.BaseSiteHegelvMapper;

/**
 * 工序合格率Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseSiteHegelvService extends CrudService<BaseSiteHegelvMapper, BaseSiteHegelv> {

	public BaseSiteHegelv get(String id) {
		return super.get(id);
	}
	
	public List<BaseSiteHegelv> findList(BaseSiteHegelv baseSiteHegelv) {
		return super.findList(baseSiteHegelv);
	}
	
	public Page<BaseSiteHegelv> findPage(Page<BaseSiteHegelv> page, BaseSiteHegelv baseSiteHegelv) {
		return super.findPage(page, baseSiteHegelv);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseSiteHegelv baseSiteHegelv) {
		super.save(baseSiteHegelv);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseSiteHegelv baseSiteHegelv) {
		super.delete(baseSiteHegelv);
	}
	
}