/**
 * 
 */
package com.jeeplus.modules.business.route.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.route.entity.BusinessRoute;
import com.jeeplus.modules.business.route.mapper.BusinessRouteMapper;

/**
 * 工艺路线Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessRouteService extends CrudService<BusinessRouteMapper, BusinessRoute> {

	public BusinessRoute get(String id) {
		return super.get(id);
	}
	
	public List<BusinessRoute> findList(BusinessRoute businessRoute) {
		return super.findList(businessRoute);
	}
	
	public Page<BusinessRoute> findPage(Page<BusinessRoute> page, BusinessRoute businessRoute) {
		return super.findPage(page, businessRoute);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessRoute businessRoute) {
		super.save(businessRoute);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessRoute businessRoute) {
		super.delete(businessRoute);
	}

	public List<String> findVersion(String productid){
		return mapper.findVersions(productid);
	}
}