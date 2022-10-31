/**
 * 
 */
package com.jeeplus.modules.business.baogong.record.service;

import java.util.Date;
import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrderMingXi;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMingXiMapper;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.baogong.record.entity.BusinessBaoGongRecord;
import com.jeeplus.modules.business.baogong.record.mapper.BusinessBaoGongRecordMapper;

/**
 * 员工报工Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessBaoGongRecordService extends CrudService<BusinessBaoGongRecordMapper, BusinessBaoGongRecord> {
	@Autowired
	@Lazy
	private BusinessBaoGongOrderService baoGongOrderService;

	public BusinessBaoGongRecord get(String id) {
		return super.get(id);
	}
	
	public List<BusinessBaoGongRecord> findList(BusinessBaoGongRecord businessBaoGongRecord) {
		return super.findList(businessBaoGongRecord);
	}
	
	public Page<BusinessBaoGongRecord> findPage(Page<BusinessBaoGongRecord> page, BusinessBaoGongRecord businessBaoGongRecord) {
		return super.findPage(page, businessBaoGongRecord);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessBaoGongRecord businessBaoGongRecord) {
		super.save(businessBaoGongRecord);
	}

	@Autowired
	private BusinessBaoGongOrderMingXiMapper businessBaoGongOrderMingXiMapper;
	@Autowired
	private BusinessBaoGongOrderMapper businessBaoGongOrderMapper;

	@Transactional(readOnly = false)
	public void delete(BusinessBaoGongRecord businessBaoGongRecord) {
		mapper.deleteByLogic(businessBaoGongRecord);
		businessBaoGongOrderMapper.uncompleteBg(businessBaoGongRecord.getBgid());
		businessBaoGongOrderMingXiMapper.uncompleteBg(businessBaoGongRecord.getBghid());
	}

	public double getDoneSumNum(String bgid,String bghid){
		Double sum = mapper.getDoneSumNum(bgid, bghid);
		if(sum == null){
			return 0.0;
		}
		return sum;
	}

	@Autowired
	private UserMapper userMapper;
	// 报工
	@Transactional(readOnly = false)
	public void baogong(String bgid,String bghid,String remarks,String userid,String opname,String douser,Double dbnum,Double lfnum,Double fgnum,Double gfnum,Double bhgnum,Double hgnum,String complete){
		User dou = userMapper.getByNo(douser);
		if(dou==null){
			throw new RuntimeException("实际报工人的工号不存在");
		}
		BusinessBaoGongOrder order = baoGongOrderService.getBaoGongInfo(bgid,bghid,null);
		BusinessBaoGongOrderMingXi mingXi = order.getBusinessBaoGongOrderMingXiList().get(0);
		if(mingXi==null){
			mingXi = new BusinessBaoGongOrderMingXi();
		}
		double donenum = getDoneSumNum(bgid,bghid);
		double sum = donenum + hgnum;
		if(sum>order.getNum()){
			throw new RuntimeException("报工数量超出工单数量");
		}
//		String pregxid = businessBaoGongOrderMingXiMapper.getPreHid(bgid,bghid,mingXi.getNo());
//		if(StringUtils.isNotEmpty(pregxid)){
//			double prenum = getDoneSumNum(bgid,pregxid);
//			if(prenum<sum){
//				throw new RuntimeException("本道工序报工数量超出上道工序已报工数量，当前本道工序可报数量："+(prenum - donenum));
//			}
//		}
		BusinessBaoGongRecord record = new BusinessBaoGongRecord();
		record.setBatchno(order.getBatchno());
		record.setDouser(dou);
		record.setBgdate(new Date());
		record.setBgcode(order.getBgcode());
		record.setBgid(bgid);
		record.setBghid(bghid);
		record.setRouteid(mingXi.getRouteid());
		fgnum = fgnum==null?0.0:fgnum;
		gfnum = gfnum==null?0.0:gfnum;
		lfnum = lfnum==null?0.0:lfnum;
		// 不合格 = 料废 + 工废 2022/10/31
		// record.setBhgnum(fgnum+gfnum+lfnum);
		record.setBhgnum(gfnum+lfnum);
		record.setUsername(opname);
		record.setDoingnum(dbnum);
		record.setCinvcode(order.getCinvcode());
		record.setCinvname(order.getCinvname());
		record.setCinvstd(order.getCinvstd());
		record.setBgcode(order.getBgcode());
		record.setOrdercode(order.getOrdercode());
		record.setOrderline(order.getOrderline());
		record.setRemarks(remarks);
		record.setGdnum(order.getNum());
		record.setFgnum(fgnum);
		record.setGfnum(gfnum);
		record.setLfnum(lfnum);
		record.setHgnum(hgnum);
		record.setSite(mingXi.getSite());
		record.setPlanid(order.getPlanid());
		record.setLineid(order.getOrderlineid());
		record.setUnit(order.getUnit());
		if("1".equals(complete) && dbnum<=hgnum){
			if(StringUtils.isEmpty(bghid)){
			}else {
				baoGongOrderService.completeBg(bghid);
			}
		}
		record.preInsert();
		record.setCreateBy(new User(userid));
		mapper.insert(record);
		baoGongOrderService.complete(order.getId(),order.getNum());
	}

	@Transactional(readOnly = false)
	public void deleteByBgid(String bgid){
		mapper.deleteByBgid(bgid);
	}

	public Boolean hasBgId(String bgid){
		return  null != mapper.hasBgId(bgid);
	}

	@Transactional(readOnly = false)
	public void updateFromEdit(BusinessBaoGongRecord record){
		mapper.updateFromEdit(record);
	}
}