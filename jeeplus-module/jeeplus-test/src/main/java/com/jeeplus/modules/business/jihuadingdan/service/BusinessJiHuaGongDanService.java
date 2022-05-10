/**
 * 
 */
package com.jeeplus.modules.business.jihuadingdan.service;

import java.util.Arrays;
import java.util.List;

import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanMapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanMingXi;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanMingXiMapper;

/**
 * 计划工单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessJiHuaGongDanService extends CrudService<BusinessJiHuaGongDanMapper, BusinessJiHuaGongDan> {

	@Autowired
	private BusinessJiHuaGongDanMingXiMapper businessJiHuaGongDanMingXiMapper;
	
	public BusinessJiHuaGongDan get(String id) {
		BusinessJiHuaGongDan businessJiHuaGongDan = super.get(id);
		businessJiHuaGongDan.setBusinessJiHuaGongDanMingXiList(businessJiHuaGongDanMingXiMapper.findList(new BusinessJiHuaGongDanMingXi(businessJiHuaGongDan)));
		return businessJiHuaGongDan;
	}
	
	public List<BusinessJiHuaGongDan> findList(BusinessJiHuaGongDan businessJiHuaGongDan) {
		return super.findList(businessJiHuaGongDan);
	}
	
	public Page<BusinessJiHuaGongDan> findPage(Page<BusinessJiHuaGongDan> page, BusinessJiHuaGongDan businessJiHuaGongDan) {
		return super.findPage(page, businessJiHuaGongDan);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessJiHuaGongDan businessJiHuaGongDan) {
		if(StringUtils.isNotEmpty(businessJiHuaGongDan.getId())){
			businessJiHuaGongDanMingXiMapper.delete(new BusinessJiHuaGongDanMingXi(businessJiHuaGongDan));
		}
		super.save(businessJiHuaGongDan);
		for (BusinessJiHuaGongDanMingXi businessJiHuaGongDanMingXi : businessJiHuaGongDan.getBusinessJiHuaGongDanMingXiList()){
			if (businessJiHuaGongDanMingXi.getId() == null){
				continue;
			}
			if (BusinessJiHuaGongDanMingXi.DEL_FLAG_NORMAL.equals(businessJiHuaGongDanMingXi.getDelFlag())){
				if (StringUtils.isBlank(businessJiHuaGongDanMingXi.getId())){
					businessJiHuaGongDanMingXi.setP(businessJiHuaGongDan);
					businessJiHuaGongDanMingXi.preInsert();
					businessJiHuaGongDanMingXiMapper.insert(businessJiHuaGongDanMingXi);
				}else{
					businessJiHuaGongDanMingXi.setP(businessJiHuaGongDan);
					businessJiHuaGongDanMingXi.preUpdate();
					businessJiHuaGongDanMingXiMapper.update(businessJiHuaGongDanMingXi);
				}
			}else{
				businessJiHuaGongDanMingXiMapper.delete(businessJiHuaGongDanMingXi);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessJiHuaGongDan businessJiHuaGongDan) {
		super.delete(businessJiHuaGongDan);
		businessJiHuaGongDanMingXiMapper.delete(new BusinessJiHuaGongDanMingXi(businessJiHuaGongDan));
	}

	public Boolean hasScddLineid(String lineid){
		Integer num = mapper.hasScdd(lineid);
		return num!=null;
	}
	@Autowired
	private BusinessShengChanDingDanMingXiMapper shengChanDingDanMingXiMapper;
	public Double getGdNum(String scddlineid){
		Double sumGdNum = mapper.getSumNum(scddlineid);
		if(sumGdNum==null){
			sumGdNum = 0.0;
		}
		Double scNum = shengChanDingDanMingXiMapper.getScNum(scddlineid);
		return scNum-sumGdNum;
	}

	@Transactional(readOnly = false)
	public void xiafa(String ids){
		Arrays.asList(ids.split(",")).forEach(id->{
			mapper.updateSatus(id,"已下发");
		});
	}
	@Transactional(readOnly = false)
	public void chehui(String ids){
		Arrays.asList(ids.split(",")).forEach(id->{
			mapper.updateSatus(id,"未下发");
		});
	}
}