/**
 * 
 */
package com.jeeplus.modules.business.product.archive.service;

import java.util.List;

import com.jeeplus.modules.api.bean.ApiFileViewBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.business.product.archive.mapper.BusinessProductMapper;
import com.jeeplus.modules.business.product.archive.entity.BusinessRoute;
import com.jeeplus.modules.business.product.archive.mapper.BusinessRouteMapper;

/**
 * 产品档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessProductService extends CrudService<BusinessProductMapper, BusinessProduct> {

	@Autowired
	private BusinessRouteMapper businessRouteMapper;
	
	public BusinessProduct get(String id) {
		BusinessProduct businessProduct = super.get(id);
		businessProduct.setBusinessRouteList(businessRouteMapper.findList(new BusinessRoute(businessProduct)));
		return businessProduct;
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
		for (BusinessRoute businessRoute : businessProduct.getBusinessRouteList()){
			if (businessRoute.getId() == null){
				continue;
			}
			if (BusinessRoute.DEL_FLAG_NORMAL.equals(businessRoute.getDelFlag())){
				if (StringUtils.isBlank(businessRoute.getId())){
					businessRoute.setP(businessProduct);
					businessRoute.preInsert();
					businessRouteMapper.insert(businessRoute);
				}else{
					businessRoute.preUpdate();
					businessRouteMapper.update(businessRoute);
				}
			}else{
				businessRouteMapper.delete(businessRoute);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessProduct businessProduct) {
		super.delete(businessProduct);
		businessRouteMapper.delete(new BusinessRoute(businessProduct));
	}

	public List<ApiFileViewBean> findFileBySite(String siteid,String filename,int pagenum,int size){
		return businessRouteMapper.findFileBySite(siteid, filename, (pagenum-1)*size, size);
	}

	public int countFileBySite(String siteid,String filename,int size){
		int rownum = businessRouteMapper.countFileBySite(siteid,filename);
		if(rownum%size==0){
			return rownum/size;
		}else {
			return rownum/size +1;
		}
	}
}