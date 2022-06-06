/**
 * 
 */
package com.jeeplus.modules.business.shengchan.paichan.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.business.shengchan.paichan.dept.mapper.BusinessShengChanPaiChanDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.paichan.entity.BusinessShengChanPaiChan;
import com.jeeplus.modules.business.shengchan.paichan.mapper.BusinessShengChanPaiChanMapper;
import com.jeeplus.modules.business.shengchan.paichan.entity.BusinessShengChanPaiChaiMx;
import com.jeeplus.modules.business.shengchan.paichan.mapper.BusinessShengChanPaiChaiMxMapper;

/**
 * 生产排产Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanPaiChanService extends CrudService<BusinessShengChanPaiChanMapper, BusinessShengChanPaiChan> {

	@Autowired
	private BusinessShengChanPaiChaiMxMapper businessShengChanPaiChaiMxMapper;
	
	public BusinessShengChanPaiChan get(String id) {
		BusinessShengChanPaiChan businessShengChanPaiChan = super.get(id);
		businessShengChanPaiChan.setBusinessShengChanPaiChaiMxList(businessShengChanPaiChaiMxMapper.findList(new BusinessShengChanPaiChaiMx(businessShengChanPaiChan)));
		return businessShengChanPaiChan;
	}
	
	public List<BusinessShengChanPaiChan> findList(BusinessShengChanPaiChan businessShengChanPaiChan) {
		return super.findList(businessShengChanPaiChan);
	}
	
	public Page<BusinessShengChanPaiChan> findPage(Page<BusinessShengChanPaiChan> page, BusinessShengChanPaiChan businessShengChanPaiChan) {
		return super.findPage(page, businessShengChanPaiChan);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessShengChanPaiChan businessShengChanPaiChan) {
		super.save(businessShengChanPaiChan);
		for (BusinessShengChanPaiChaiMx businessShengChanPaiChaiMx : businessShengChanPaiChan.getBusinessShengChanPaiChaiMxList()){
			if (businessShengChanPaiChaiMx.getId() == null){
				continue;
			}
			if (BusinessShengChanPaiChaiMx.DEL_FLAG_NORMAL.equals(businessShengChanPaiChaiMx.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanPaiChaiMx.getId())){
					businessShengChanPaiChaiMx.setP(businessShengChanPaiChan);
					businessShengChanPaiChaiMx.preInsert();
					businessShengChanPaiChaiMxMapper.insert(businessShengChanPaiChaiMx);
				}else{
					businessShengChanPaiChaiMx.preUpdate();
					businessShengChanPaiChaiMxMapper.update(businessShengChanPaiChaiMx);
				}
			}else{
				businessShengChanPaiChaiMxMapper.delete(businessShengChanPaiChaiMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanPaiChan businessShengChanPaiChan) {
		super.delete(businessShengChanPaiChan);
		businessShengChanPaiChaiMxMapper.delete(new BusinessShengChanPaiChaiMx(businessShengChanPaiChan));
	}

	@Autowired
	private BusinessShengChanPaiChanDeptMapper chanPaiChanDeptMapper;
	public void checckPaiChan(String dept,String sccode, String scline, Date scdate){
		Integer i = chanPaiChanDeptMapper.hasPaichanDept(dept);
		if(i!=null && i == 1){
			Date date = businessShengChanPaiChaiMxMapper.getPanChanDate(sccode,scline);
			if(date==null){
				throw new RuntimeException("未做排产，不可拆单。");
			}
			if(scdate==null){
				return;
			}
			double d = DateUtils.getDistanceOfTwoDate(date,scdate);
			if(d>2||d<1){
				throw new RuntimeException("排产单必须提前1天.");
			}
		}
	}
}