/**
 * 
 */
package com.jeeplus.modules.business.baogong.change.service;

import java.util.List;

import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMingXiMapper;
import com.jeeplus.modules.business.baogong.record.entity.BusinessBaoGongRecord;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.baogong.change.entity.BusinessBaoGongChange;
import com.jeeplus.modules.business.baogong.change.mapper.BusinessBaoGongChangeMapper;

/**
 * 报工修改Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessBaoGongChangeService extends CrudService<BusinessBaoGongChangeMapper, BusinessBaoGongChange> {
	@Autowired
	private BusinessBaoGongRecordService businessBaoGongRecordService;
	public BusinessBaoGongChange get(String id) {
		return super.get(id);
	}
	
	public List<BusinessBaoGongChange> findList(BusinessBaoGongChange businessBaoGongChange) {
		return super.findList(businessBaoGongChange);
	}
	
	public Page<BusinessBaoGongChange> findPage(Page<BusinessBaoGongChange> page, BusinessBaoGongChange businessBaoGongChange) {
		return super.findPage(page, businessBaoGongChange);
	}
	@Autowired
	private BusinessBaoGongOrderMingXiMapper businessBaoGongOrderMingXiMapper;
	@Autowired
	private BusinessBaoGongOrderMapper businessBaoGongOrderMapper;
	@Transactional(readOnly = false)
	public void save(BusinessBaoGongChange businessBaoGongChange) {
		BusinessBaoGongRecord record = businessBaoGongRecordService.get(businessBaoGongChange.getRecordid());
		if(record.getDoingnum()<businessBaoGongChange.getHgnum()){
			throw new RuntimeException("合格数量大于工单数量");
		}
		record.setHgnum(businessBaoGongChange.getHgnum());
		record.setLfnum(businessBaoGongChange.getLfnum());
		record.setGfnum(businessBaoGongChange.getGfnum());
		record.setGfnum(businessBaoGongChange.getGfnum());
		record.setDouser(businessBaoGongChange.getDouser());
		if(businessBaoGongChange.getYhgnum()> businessBaoGongChange.getHgnum()){
			businessBaoGongOrderMapper.uncompleteBg(record.getBgid());
			businessBaoGongOrderMingXiMapper.uncompleteBg(record.getBghid());
		}
		record.setBhgnum(businessBaoGongChange.getLfnum()+ businessBaoGongChange.getGfnum()+businessBaoGongChange.getFgnum());
		super.save(businessBaoGongChange);
		businessBaoGongRecordService.updateFromEdit(record);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessBaoGongChange businessBaoGongChange) {
		super.delete(businessBaoGongChange);
	}
	
}