/**
 * 
 */
package com.jeeplus.modules.business.chuku.lingliao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiao;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMapper;
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiaoMx;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMxMapper;

/**
 * 领料单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessChuKuLingLiaoService extends CrudService<BusinessChuKuLingLiaoMapper, BusinessChuKuLingLiao> {

	@Autowired
	private BusinessChuKuLingLiaoMxMapper businessChuKuLingLiaoMxMapper;
	
	public BusinessChuKuLingLiao get(String id) {
		BusinessChuKuLingLiao businessChuKuLingLiao = super.get(id);
		businessChuKuLingLiao.setBusinessChuKuLingLiaoMxList(businessChuKuLingLiaoMxMapper.findList(new BusinessChuKuLingLiaoMx(businessChuKuLingLiao)));
		return businessChuKuLingLiao;
	}
	
	public List<BusinessChuKuLingLiao> findList(BusinessChuKuLingLiao businessChuKuLingLiao) {
		return super.findList(businessChuKuLingLiao);
	}
	
	public Page<BusinessChuKuLingLiao> findPage(Page<BusinessChuKuLingLiao> page, BusinessChuKuLingLiao businessChuKuLingLiao) {
		return super.findPage(page, businessChuKuLingLiao);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessChuKuLingLiao businessChuKuLingLiao) {
		super.save(businessChuKuLingLiao);
		for (BusinessChuKuLingLiaoMx businessChuKuLingLiaoMx : businessChuKuLingLiao.getBusinessChuKuLingLiaoMxList()){
			if (businessChuKuLingLiaoMx.getId() == null){
				continue;
			}
			if (BusinessChuKuLingLiaoMx.DEL_FLAG_NORMAL.equals(businessChuKuLingLiaoMx.getDelFlag())){
				if (StringUtils.isBlank(businessChuKuLingLiaoMx.getId())){
					businessChuKuLingLiaoMx.setP(businessChuKuLingLiao);
					businessChuKuLingLiaoMx.preInsert();
					businessChuKuLingLiaoMxMapper.insert(businessChuKuLingLiaoMx);
				}else{
					businessChuKuLingLiaoMx.preUpdate();
					businessChuKuLingLiaoMxMapper.update(businessChuKuLingLiaoMx);
				}
			}else{
				businessChuKuLingLiaoMxMapper.delete(businessChuKuLingLiaoMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessChuKuLingLiao businessChuKuLingLiao) {
		super.delete(businessChuKuLingLiao);
		businessChuKuLingLiaoMxMapper.delete(new BusinessChuKuLingLiaoMx(businessChuKuLingLiao));
	}
	
}