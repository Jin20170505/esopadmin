/**
 * 
 */
package com.jeeplus.modules.business.chuku.ommo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWai;
import com.jeeplus.modules.business.chuku.ommo.mapper.BusinessChuKuWeiWaiMapper;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWaiMx;
import com.jeeplus.modules.business.chuku.ommo.mapper.BusinessChuKuWeiWaiMxMapper;

/**
 * 委外发料Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessChuKuWeiWaiService extends CrudService<BusinessChuKuWeiWaiMapper, BusinessChuKuWeiWai> {

	@Autowired
	private BusinessChuKuWeiWaiMxMapper businessChuKuWeiWaiMxMapper;
	
	public BusinessChuKuWeiWai get(String id) {
		BusinessChuKuWeiWai businessChuKuWeiWai = super.get(id);
		businessChuKuWeiWai.setBusinessChuKuWeiWaiMxList(businessChuKuWeiWaiMxMapper.findList(new BusinessChuKuWeiWaiMx(businessChuKuWeiWai)));
		return businessChuKuWeiWai;
	}
	
	public List<BusinessChuKuWeiWai> findList(BusinessChuKuWeiWai businessChuKuWeiWai) {
		return super.findList(businessChuKuWeiWai);
	}
	
	public Page<BusinessChuKuWeiWai> findPage(Page<BusinessChuKuWeiWai> page, BusinessChuKuWeiWai businessChuKuWeiWai) {
		return super.findPage(page, businessChuKuWeiWai);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessChuKuWeiWai businessChuKuWeiWai) {
		super.save(businessChuKuWeiWai);
		for (BusinessChuKuWeiWaiMx businessChuKuWeiWaiMx : businessChuKuWeiWai.getBusinessChuKuWeiWaiMxList()){
			if (businessChuKuWeiWaiMx.getId() == null){
				continue;
			}
			if (BusinessChuKuWeiWaiMx.DEL_FLAG_NORMAL.equals(businessChuKuWeiWaiMx.getDelFlag())){
				if (StringUtils.isBlank(businessChuKuWeiWaiMx.getId())){
					businessChuKuWeiWaiMx.setP(businessChuKuWeiWai);
					businessChuKuWeiWaiMx.preInsert();
					businessChuKuWeiWaiMxMapper.insert(businessChuKuWeiWaiMx);
				}else{
					businessChuKuWeiWaiMx.preUpdate();
					businessChuKuWeiWaiMxMapper.update(businessChuKuWeiWaiMx);
				}
			}else{
				businessChuKuWeiWaiMxMapper.delete(businessChuKuWeiWaiMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessChuKuWeiWai businessChuKuWeiWai) {
		super.delete(businessChuKuWeiWai);
		businessChuKuWeiWaiMxMapper.delete(new BusinessChuKuWeiWaiMx(businessChuKuWeiWai));
	}
	
}