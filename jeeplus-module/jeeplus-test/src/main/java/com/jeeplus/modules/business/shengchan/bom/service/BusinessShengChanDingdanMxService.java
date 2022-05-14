/**
 * 
 */
package com.jeeplus.modules.business.shengchan.bom.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanDingdanMx;
import com.jeeplus.modules.business.shengchan.bom.mapper.BusinessShengChanDingdanMxMapper;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.bom.mapper.BusinessShengChanBomMapper;

/**
 * 生产子件Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanDingdanMxService extends CrudService<BusinessShengChanDingdanMxMapper, BusinessShengChanDingdanMx> {

	@Autowired
	private BusinessShengChanBomMapper businessShengChanBomMapper;
	
	public BusinessShengChanDingdanMx get(String id) {
		BusinessShengChanDingdanMx businessShengChanDingdanMx = new BusinessShengChanDingdanMx();
		return businessShengChanDingdanMx;
	}

	public List<BusinessShengChanBom> findBomList(String schid){
		BusinessShengChanBom bom = new BusinessShengChanBom();
		bom.setSchid(schid);
		return businessShengChanBomMapper.findList(bom);
	}

	public List<BusinessShengChanDingdanMx> findList(BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		return super.findList(businessShengChanDingdanMx);
	}
	
	public Page<BusinessShengChanDingdanMx> findPage(Page<BusinessShengChanDingdanMx> page, BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		return super.findPage(page, businessShengChanDingdanMx);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		for (BusinessShengChanBom businessShengChanBom : businessShengChanDingdanMx.getBusinessShengChanBomList()){
			if (businessShengChanBom.getId() == null){
				continue;
			}
			if (BusinessShengChanBom.DEL_FLAG_NORMAL.equals(businessShengChanBom.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanBom.getId())){
					businessShengChanBom.setSchid(businessShengChanDingdanMx.getId());
					businessShengChanBom.preInsert();
					businessShengChanBomMapper.insert(businessShengChanBom);
				}else{
					businessShengChanBom.setSchid(businessShengChanDingdanMx.getId());
					businessShengChanBom.preUpdate();
					businessShengChanBomMapper.update(businessShengChanBom);
				}
			}else{
				businessShengChanBomMapper.delete(businessShengChanBom);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		super.delete(businessShengChanDingdanMx);
		BusinessShengChanBom bom = new BusinessShengChanBom();
		bom.setSchid(businessShengChanDingdanMx.getId());
		businessShengChanBomMapper.delete(bom);
	}
	
}