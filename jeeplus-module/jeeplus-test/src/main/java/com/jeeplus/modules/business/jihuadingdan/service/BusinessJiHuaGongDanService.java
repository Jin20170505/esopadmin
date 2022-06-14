/**
 * 
 */
package com.jeeplus.modules.business.jihuadingdan.service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
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
	public synchronized void  save(BusinessJiHuaGongDan businessJiHuaGongDan) {
		if (StringUtils.isEmpty(businessJiHuaGongDan.getId())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessJiHuaGongDan.setCode(code);
			super.save(businessJiHuaGongDan);
		}else {
			super.save(businessJiHuaGongDan);
		}
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
		if(businessBaoGongOrderService.hasScOrderFromPlan(businessJiHuaGongDan.getId())){
			throw new RuntimeException("删除失败，原因：该计划工单有对应的报工单存在。");
		}
		shengChanDingDanMingXiMapper.updateChaidanStatus(businessJiHuaGongDan.getDd().getId(),"未拆完");
		super.delete(businessJiHuaGongDan);
		businessJiHuaGongDanMingXiMapper.delete(new BusinessJiHuaGongDanMingXi(businessJiHuaGongDan));
		businessJiHuaGongDanBomMapper.delete(new BusinessJiHuaGongDanBom(businessJiHuaGongDan));
	}

	public Boolean hasScddLineid(String lineid){
		Integer num = mapper.hasScdd(lineid);
		return num!=null;
	}

	public Boolean hasScCode(String sccode){
		Integer num = mapper.hasScddByOrderCode(sccode);
		return num !=null;
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
		if(businessBaoGongOrderService.hasScOrderFromPlan(id)){
			throw new RuntimeException("撤回失败，原因：该计划工单有对应的报工单存在。");
		}
		mapper.updateSatus(id,"未下发");
	}
	/**  生成 报工单 **/
	@Autowired
	private BusinessBaoGongOrderService businessBaoGongOrderService;
	@Transactional(readOnly = false)
	public  void shengchengbaogongdan(String id){
		boolean flag = businessBaoGongOrderService.hasScOrderFromPlan(id);
		if(flag){
			throw new RuntimeException("该计划工单已生成报工单。请勿多次生成.");
		}
		BusinessJiHuaGongDan jiHuaGongDan = get(id);
		if("未下发".equals(jiHuaGongDan.getStatus())){
			throw new RuntimeException("该计划工单的状态：未下发.不可生成。请审核后再操作");
		}
		BusinessBaoGongOrder order = new BusinessBaoGongOrder();
		order.setBatchno(jiHuaGongDan.getBatchno());
		order.setOrderlineid(jiHuaGongDan.getDd().getId());
		order.setOrderline(jiHuaGongDan.getOrderno());
		order.setOrdercode(jiHuaGongDan.getDd().getCode());
		order.setPlanid(jiHuaGongDan.getId());
		order.setPlancode(jiHuaGongDan.getCode());
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
		String code="";
		synchronized (this){
			code = businessBaoGongOrderService.getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			order.setBgcode(code);
			StringBuffer sb = new StringBuffer("{");
			sb.append("\"batchno\":\"").append(order.getBatchno()).append("\",");
			sb.append("\"sccode\":\"").append(order.getOrdercode()).append("\",");
			sb.append("\"lineno\":\"").append(order.getOrderline()).append("\",");
			sb.append("\"plancode\":\"").append(order.getPlancode()).append("\",");
			sb.append("\"num\":\"").append(order.getNum()).append("\",");
			sb.append("\"bgcode\":\"").append(order.getBgcode()).append("\"");
			sb.append("}");
			order.setQrcode(sb.toString());
			businessBaoGongOrderService.save(order);
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "JHGD" +ymd + "00001";
		}else {
			code = maxcode.substring(0,10);
			int c =  Integer.valueOf(maxcode.substring(10));
			c = c+1;
			if(c<10){
				code = code +"0000"+c;
			}else if(10<=c && c<100){
				code = code +"000"+c;
			}else if(100<=c && c<1000) {
				code = code +"00"+c;
			}else if(1000<=c && c<10000){
				code = code +"0"+c;
			}else {
				code = code+c;
			}
		}
		return code;
	}
	public Double getSumnumByScYid(String scyid){
		return businessJiHuaGongDanBomMapper.getSumnumByScYid(scyid);
	}

	@Transactional(readOnly = false)
	public void updateWeiCha(String scyid,double num){
		businessJiHuaGongDanBomMapper.updateWeiCha(scyid,num);
	}
}