/**
 * 
 */
package com.jeeplus.modules.business.shengchan.beiliao.apply.service;

import java.util.List;

import com.jeeplus.modules.business.shengchan.beiliao.mapper.BusinessShengChanBeiLiaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengChanBeiLiaoApply;
import com.jeeplus.modules.business.shengchan.beiliao.apply.mapper.BusinessShengChanBeiLiaoApplyMapper;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengchanBeiliaoApplyMx;
import com.jeeplus.modules.business.shengchan.beiliao.apply.mapper.BusinessShengchanBeiliaoApplyMxMapper;

/**
 * 生产备料Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanBeiLiaoApplyService extends CrudService<BusinessShengChanBeiLiaoApplyMapper, BusinessShengChanBeiLiaoApply> {

	@Autowired
	private BusinessShengchanBeiliaoApplyMxMapper businessShengchanBeiliaoApplyMxMapper;
	
	public BusinessShengChanBeiLiaoApply get(String id) {
		BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply = super.get(id);
		businessShengChanBeiLiaoApply.setBusinessShengchanBeiliaoApplyMxList(businessShengchanBeiliaoApplyMxMapper.findList(new BusinessShengchanBeiliaoApplyMx(businessShengChanBeiLiaoApply)));
		return businessShengChanBeiLiaoApply;
	}
	
	public List<BusinessShengChanBeiLiaoApply> findList(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply) {
		return super.findList(businessShengChanBeiLiaoApply);
	}
	
	public Page<BusinessShengChanBeiLiaoApply> findPage(Page<BusinessShengChanBeiLiaoApply> page, BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply) {
		return super.findPage(page, businessShengChanBeiLiaoApply);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply) {
		super.save(businessShengChanBeiLiaoApply);
		for (BusinessShengchanBeiliaoApplyMx businessShengchanBeiliaoApplyMx : businessShengChanBeiLiaoApply.getBusinessShengchanBeiliaoApplyMxList()){
			if (businessShengchanBeiliaoApplyMx.getId() == null){
				continue;
			}
			if (BusinessShengchanBeiliaoApplyMx.DEL_FLAG_NORMAL.equals(businessShengchanBeiliaoApplyMx.getDelFlag())){
				if (StringUtils.isBlank(businessShengchanBeiliaoApplyMx.getId())){
					businessShengchanBeiliaoApplyMx.setP(businessShengChanBeiLiaoApply);
					businessShengchanBeiliaoApplyMx.preInsert();
					businessShengchanBeiliaoApplyMxMapper.insert(businessShengchanBeiliaoApplyMx);
				}else{
					businessShengchanBeiliaoApplyMx.preUpdate();
					businessShengchanBeiliaoApplyMxMapper.update(businessShengchanBeiliaoApplyMx);
				}
			}else{
				businessShengchanBeiliaoApplyMxMapper.delete(businessShengchanBeiliaoApplyMx);
			}
		}
	}
	@Autowired
	private BusinessShengChanBeiLiaoMapper shengChanBeiLiaoMapper;
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanBeiLiaoApply businessShengChanBeiLiaoApply) {
		if(shengChanBeiLiaoMapper.isSure(businessShengChanBeiLiaoApply.getId())!=null){
			throw new RuntimeException("删除失败，原因：该生产备料单有对应备料确认记录存在。");
		}
		super.delete(businessShengChanBeiLiaoApply);
		businessShengchanBeiliaoApplyMxMapper.delete(new BusinessShengchanBeiliaoApplyMx(businessShengChanBeiLiaoApply));
	}

    public BusinessShengchanBeiliaoApplyMx getBom(String bomid) {
		return businessShengchanBeiliaoApplyMxMapper.get(bomid);
    }
	@Transactional(readOnly = false)
    public void print(String id) {
		mapper.print(id);
    }

    public Double getDoneNum(String schid){
		return mapper.getDoneNum(schid);
	}

	public Boolean hasScOrder(String schid){
		return mapper.hasScOrder(schid) !=null;
	}
}