/**
 * 
 */
package com.jeeplus.modules.business.shengchan.beiliao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.beiliao.entity.BusinessShengChanBeiLiao;
import com.jeeplus.modules.business.shengchan.beiliao.mapper.BusinessShengChanBeiLiaoMapper;
import com.jeeplus.modules.business.shengchan.beiliao.entity.BusinessShengChanBeiLiaoMx;
import com.jeeplus.modules.business.shengchan.beiliao.mapper.BusinessShengChanBeiLiaoMxMapper;

/**
 * 生产备料Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanBeiLiaoService extends CrudService<BusinessShengChanBeiLiaoMapper, BusinessShengChanBeiLiao> {

	@Autowired
	private BusinessShengChanBeiLiaoMxMapper businessShengChanBeiLiaoMxMapper;
	
	public BusinessShengChanBeiLiao get(String id) {
		BusinessShengChanBeiLiao businessShengChanBeiLiao = super.get(id);
		businessShengChanBeiLiao.setBusinessShengChanBeiLiaoMxList(businessShengChanBeiLiaoMxMapper.findList(new BusinessShengChanBeiLiaoMx(businessShengChanBeiLiao)));
		return businessShengChanBeiLiao;
	}
	
	public List<BusinessShengChanBeiLiao> findList(BusinessShengChanBeiLiao businessShengChanBeiLiao) {
		return super.findList(businessShengChanBeiLiao);
	}
	
	public Page<BusinessShengChanBeiLiao> findPage(Page<BusinessShengChanBeiLiao> page, BusinessShengChanBeiLiao businessShengChanBeiLiao) {
		return super.findPage(page, businessShengChanBeiLiao);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessShengChanBeiLiao businessShengChanBeiLiao) {
		super.save(businessShengChanBeiLiao);
		for (BusinessShengChanBeiLiaoMx businessShengChanBeiLiaoMx : businessShengChanBeiLiao.getBusinessShengChanBeiLiaoMxList()){
			if (businessShengChanBeiLiaoMx.getId() == null){
				continue;
			}
			if (BusinessShengChanBeiLiaoMx.DEL_FLAG_NORMAL.equals(businessShengChanBeiLiaoMx.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanBeiLiaoMx.getId())){
					businessShengChanBeiLiaoMx.setP(businessShengChanBeiLiao);
					businessShengChanBeiLiaoMx.preInsert();
					businessShengChanBeiLiaoMxMapper.insert(businessShengChanBeiLiaoMx);
				}else{
					businessShengChanBeiLiaoMx.preUpdate();
					businessShengChanBeiLiaoMxMapper.update(businessShengChanBeiLiaoMx);
				}
			}else{
				businessShengChanBeiLiaoMxMapper.delete(businessShengChanBeiLiaoMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanBeiLiao businessShengChanBeiLiao) {
		super.delete(businessShengChanBeiLiao);
		businessShengChanBeiLiaoMxMapper.delete(new BusinessShengChanBeiLiaoMx(businessShengChanBeiLiao));
	}

	public boolean isSure(String blid){
		Integer i = mapper.isSure(blid);
		return i!=null&&i==1?true:false;
	}
}