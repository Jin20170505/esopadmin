/**
 * 
 */
package com.jeeplus.modules.business.jihuadingdan.service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.number.RandomUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrderMingXi;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanBom;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanMingXi;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanBomMapper;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanMapper;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanMingXiMapper;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 计划工单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessJiHuaGongDanService extends CrudService<BusinessJiHuaGongDanMapper, BusinessJiHuaGongDan> {

	@Autowired
	private BusinessJiHuaGongDanMingXiMapper businessJiHuaGongDanMingXiMapper;
	@Autowired
	private BusinessJiHuaGongDanBomMapper businessJiHuaGongDanBomMapper;

	public BusinessJiHuaGongDan get(String id) {
		BusinessJiHuaGongDan businessJiHuaGongDan = super.get(id);
		businessJiHuaGongDan.setBusinessJiHuaGongDanMingXiList(businessJiHuaGongDanMingXiMapper.findList(new BusinessJiHuaGongDanMingXi(businessJiHuaGongDan)));
		businessJiHuaGongDan.setBusinessJiHuaGongDanBomList(businessJiHuaGongDanBomMapper.findList(new BusinessJiHuaGongDanBom(businessJiHuaGongDan)));
		return businessJiHuaGongDan;
	}
	
	public List<BusinessJiHuaGongDan> findList(BusinessJiHuaGongDan businessJiHuaGongDan) {
		return super.findList(businessJiHuaGongDan);
	}
	
	public Page<BusinessJiHuaGongDan> findPage(Page<BusinessJiHuaGongDan> page, BusinessJiHuaGongDan businessJiHuaGongDan) {
		return super.findPage(page, businessJiHuaGongDan);
	}
	@Transactional(readOnly = false)
	public void deleteMx(String pid){
		businessJiHuaGongDanMingXiMapper.delete(new BusinessJiHuaGongDanMingXi(new BusinessJiHuaGongDan(pid)));
	}

	@Transactional(readOnly = false)
	public void save(BusinessJiHuaGongDan businessJiHuaGongDan) {
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
					businessJiHuaGongDanMingXiMapper.insert(businessJiHuaGongDanMingXi);
				}
			}else{
				businessJiHuaGongDanMingXiMapper.delete(businessJiHuaGongDanMingXi);
			}
		}
		for (BusinessJiHuaGongDanBom businessJiHuaGongDanBom : businessJiHuaGongDan.getBusinessJiHuaGongDanBomList()){
			if (businessJiHuaGongDanBom.getId() == null){
				continue;
			}
			if (BusinessJiHuaGongDanMingXi.DEL_FLAG_NORMAL.equals(businessJiHuaGongDanBom.getDelFlag())){
				if (StringUtils.isBlank(businessJiHuaGongDanBom.getId())){
					businessJiHuaGongDanBom.setP(businessJiHuaGongDan);
					businessJiHuaGongDanBom.preInsert();
					businessJiHuaGongDanBomMapper.insert(businessJiHuaGongDanBom);
				}else{
					businessJiHuaGongDanBom.setP(businessJiHuaGongDan);
					businessJiHuaGongDanBom.preUpdate();
					businessJiHuaGongDanBomMapper.update(businessJiHuaGongDanBom);
				}
			}else{
				businessJiHuaGongDanBomMapper.delete(businessJiHuaGongDanBom);
			}
		}

	}


	@Transactional(readOnly = false)
	public void delete(BusinessJiHuaGongDan businessJiHuaGongDan) {
		super.delete(businessJiHuaGongDan);
		businessJiHuaGongDanMingXiMapper.delete(new BusinessJiHuaGongDanMingXi(businessJiHuaGongDan));
		businessJiHuaGongDanBomMapper.delete(new BusinessJiHuaGongDanBom(businessJiHuaGongDan));
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
	public void xiafa(String id){
		//TODO 检查是否可以下发
		mapper.updateSatus(id,"已下发");
	}
	@Transactional(readOnly = false)
	public void chehui(String id){
		//TODO 检查是否可以回撤
		mapper.updateSatus(id,"未下发");
	}
	/**  生成 报工单 **/
	@Autowired
	private BusinessBaoGongOrderService businessBaoGongOrderService;
	@Transactional(readOnly = false)
	public  void shengchengbaogongdan(String id){
		boolean flag = businessBaoGongOrderService.hasScOrderFromPlan(id);
		if(flag){
			throw new RuntimeException("该计划工单以生成报工单。请勿多次生成.");
		}
		BusinessJiHuaGongDan jiHuaGongDan = get(id);
		if("未下发".equals(jiHuaGongDan.getStatus())){
			throw new RuntimeException("该计划工单的状态：未下发.不可生成。请审核后再操作");
		}
		BusinessBaoGongOrder order = new BusinessBaoGongOrder();
		StringBuffer sb = new StringBuffer("{");
		order.setBatchno(jiHuaGongDan.getBatchno());
		sb.append("\"batchno\":\"").append(order.getBatchno()).append("\",");
		order.setOrderlineid(jiHuaGongDan.getDd().getId());
		order.setOrderline(jiHuaGongDan.getOrderno());
		order.setOrdercode(jiHuaGongDan.getDd().getCode());
		sb.append("\"sccode\":\"").append(order.getOrdercode()).append("\",");
		sb.append("\"lineno\":\"").append(order.getOrderline()).append("\",");
		order.setPlanid(jiHuaGongDan.getId());
		order.setPlancode(jiHuaGongDan.getCode());
		sb.append("\"plancode\":\"").append(order.getPlancode()).append("\",");
		order.setDept(jiHuaGongDan.getDept().getId());
		order.setDeptName(jiHuaGongDan.getDept().getName());
		order.setCinvcode(jiHuaGongDan.getCinvcode());
		order.setCinvname(jiHuaGongDan.getCinvname());
		order.setCinvstd(jiHuaGongDan.getCinvstd());
		order.setStartdate(jiHuaGongDan.getStartdate());
		order.setEnddate(jiHuaGongDan.getEnddate());
		order.setComplate("0");
		order.setNum(jiHuaGongDan.getGdnum());
		order.setUnit(jiHuaGongDan.getUnit());
		sb.append("\"num\":\"").append(order.getNum()).append("\",");
		String code="";
		synchronized (this){
			code = "BGD"+ DateUtils.getDate("yyyyMMddHHmmss")+ RandomUtil.nextInt(100,999);
		}

		order.setBgcode(code);
		sb.append("\"bgcode\":\"").append(order.getBgcode()).append("\"");
		sb.append("}");
		order.setQrcode(sb.toString());
		// 子表
		jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().forEach(mx->{
			BusinessBaoGongOrderMingXi xi = new BusinessBaoGongOrderMingXi();
			xi.setClassgroup(mx.getClassgroup().getName());
			xi.setNo(mx.getNo());xi.setNum(mx.getNum());xi.setSite(mx.getSite().getName());
			xi.setComplete("0");xi.setOpcode(mx.getUserno());xi.setOpname(mx.getUsername());
			xi.setId("");xi.setDelFlag("0");
			order.getBusinessBaoGongOrderMingXiList().add(xi);
		});
		mapper.updateisshengcheng(id,"已生成");
		businessBaoGongOrderService.save(order);
	}

	public Double getSumnumByScYid(String scyid){
		return businessJiHuaGongDanBomMapper.getSumnumByScYid(scyid);
	}

	@Transactional(readOnly = false)
	public void updateWeiCha(String scyid,double num){
		businessJiHuaGongDanBomMapper.updateWeiCha(scyid,num);
	}
}