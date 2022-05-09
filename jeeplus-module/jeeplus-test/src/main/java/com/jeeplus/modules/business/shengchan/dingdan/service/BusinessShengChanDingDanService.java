/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMapper;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;

/**
 * 生产订单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanDingDanService extends CrudService<BusinessShengChanDingDanMapper, BusinessShengChanDingDan> {

	@Autowired
	private BusinessShengChanDingDanMingXiMapper businessShengChanDingDanMingXiMapper;
	
	public BusinessShengChanDingDan get(String id) {
		BusinessShengChanDingDan businessShengChanDingDan = super.get(id);
		businessShengChanDingDan.setBusinessShengChanDingDanMingXiList(businessShengChanDingDanMingXiMapper.findList(new BusinessShengChanDingDanMingXi(businessShengChanDingDan)));
		return businessShengChanDingDan;
	}
	
	public List<BusinessShengChanDingDan> findList(BusinessShengChanDingDan businessShengChanDingDan) {
		return super.findList(businessShengChanDingDan);
	}
	
	public Page<BusinessShengChanDingDan> findPage(Page<BusinessShengChanDingDan> page, BusinessShengChanDingDan businessShengChanDingDan) {
		return super.findPage(page, businessShengChanDingDan);
	}

	public Page<BusinessShengChanDingDanMingXi> findPage(Page<BusinessShengChanDingDanMingXi> page,BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi){
		businessShengChanDingDanMingXi.setPage(page);
		page.setList(businessShengChanDingDanMingXiMapper.findShengChanDingDanMingXi(businessShengChanDingDanMingXi));
		return page;
	}


	@Transactional(readOnly = false)
	public void save(BusinessShengChanDingDan businessShengChanDingDan) {
		super.save(businessShengChanDingDan);
		for (BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi : businessShengChanDingDan.getBusinessShengChanDingDanMingXiList()){
			if (businessShengChanDingDanMingXi.getId() == null){
				continue;
			}
			if (BusinessShengChanDingDanMingXi.DEL_FLAG_NORMAL.equals(businessShengChanDingDanMingXi.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanDingDanMingXi.getId())){
					businessShengChanDingDanMingXi.setP(businessShengChanDingDan);
					businessShengChanDingDanMingXi.preInsert();
					businessShengChanDingDanMingXiMapper.insert(businessShengChanDingDanMingXi);
				}else{
					businessShengChanDingDanMingXi.preUpdate();
					businessShengChanDingDanMingXiMapper.update(businessShengChanDingDanMingXi);
				}
			}else{
				businessShengChanDingDanMingXiMapper.delete(businessShengChanDingDanMingXi);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanDingDan businessShengChanDingDan) {
		super.delete(businessShengChanDingDan);
		businessShengChanDingDanMingXiMapper.delete(new BusinessShengChanDingDanMingXi(businessShengChanDingDan));
	}
	@Transactional(readOnly = false)
	public void shenhe(String ids){
		Arrays.asList(ids.split(",")).forEach(id->businessShengChanDingDanMingXiMapper.shenhe(id));
	}

	@Transactional(readOnly = false)
	public void fanshen(String ids){
		Arrays.asList(ids.split(",")).forEach(id->businessShengChanDingDanMingXiMapper.fanshen(id));
	}

}