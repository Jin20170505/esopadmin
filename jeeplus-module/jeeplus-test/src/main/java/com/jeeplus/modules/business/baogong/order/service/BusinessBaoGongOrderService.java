/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.service;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.modules.api.bean.baogong.BaoGongBean;
import com.jeeplus.modules.api.bean.baogong.BaoGongItem;
import com.jeeplus.modules.api.bean.ruku.ProductRuKuBean;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrderMingXi;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMingXiMapper;

/**
 * 报工单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessBaoGongOrderService extends CrudService<BusinessBaoGongOrderMapper, BusinessBaoGongOrder> {

	@Autowired
	private BusinessBaoGongOrderMingXiMapper businessBaoGongOrderMingXiMapper;
	@Autowired
	private BusinessBaoGongRecordService businessBaoGongRecordService;

	public BusinessBaoGongOrder get(String id) {
		BusinessBaoGongOrder businessBaoGongOrder = super.get(id);
		businessBaoGongOrder.setBusinessBaoGongOrderMingXiList(businessBaoGongOrderMingXiMapper.findList(new BusinessBaoGongOrderMingXi(businessBaoGongOrder)));
		return businessBaoGongOrder;
	}
	// 用于 报工
	public BusinessBaoGongOrder getBaoGongInfo(String bgid,String bghid,String bgcode){
		BusinessBaoGongOrder order = null;
		if(StringUtils.isNotEmpty(bgid)){
			order = super.get(bgid);
			order.getBusinessBaoGongOrderMingXiList().add(businessBaoGongOrderMingXiMapper.get(bghid));
		}else {
			order = mapper.getByCode(bgcode);
			if(order==null){
				return null;
			}else {
				findDoingBaoGaoItem(order,order.getId(),order.getNum());
			}
		}
		return order;
	}

	public void findDoingBaoGaoItem(BusinessBaoGongOrder order,String bgid,double gdnum){
		BusinessBaoGongOrderMingXi mingXi = new BusinessBaoGongOrderMingXi();
		mingXi.setComplete("0");
		mingXi.setPid(new BusinessBaoGongOrder(bgid));
		List<BusinessBaoGongOrderMingXi> mingXis = businessBaoGongOrderMingXiMapper.findList(mingXi);
		if(mingXis==null || mingXis.isEmpty()){
			double donenum = businessBaoGongRecordService.getDoneSumNum(bgid,null);
			order.setDelFlag(donenum+"");
			return ;
		}
		mingXis.forEach(m->{
			double donenum = businessBaoGongRecordService.getDoneSumNum(bgid,m.getId());
			m.setNum(gdnum-donenum);
		});
		order.setBusinessBaoGongOrderMingXiList(mingXis);
	}
	public List<BusinessBaoGongOrder> findList(BusinessBaoGongOrder businessBaoGongOrder) {
		return super.findList(businessBaoGongOrder);
	}
	
	public Page<BusinessBaoGongOrder> findPage(Page<BusinessBaoGongOrder> page, BusinessBaoGongOrder businessBaoGongOrder) {
		return super.findPage(page, businessBaoGongOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessBaoGongOrder businessBaoGongOrder) {
		super.save(businessBaoGongOrder);
		for (BusinessBaoGongOrderMingXi businessBaoGongOrderMingXi : businessBaoGongOrder.getBusinessBaoGongOrderMingXiList()){
			if (businessBaoGongOrderMingXi.getId() == null){
				continue;
			}
			if (BusinessBaoGongOrderMingXi.DEL_FLAG_NORMAL.equals(businessBaoGongOrderMingXi.getDelFlag())){
				if (StringUtils.isBlank(businessBaoGongOrderMingXi.getId())){
					businessBaoGongOrderMingXi.setPid(businessBaoGongOrder);
					businessBaoGongOrderMingXi.preInsert();
					businessBaoGongOrderMingXiMapper.insert(businessBaoGongOrderMingXi);
				}else{
					businessBaoGongOrderMingXi.preUpdate();
					businessBaoGongOrderMingXiMapper.update(businessBaoGongOrderMingXi);
				}
			}else{
				businessBaoGongOrderMingXiMapper.delete(businessBaoGongOrderMingXi);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessBaoGongOrder businessBaoGongOrder) {
		super.delete(businessBaoGongOrder);
		businessBaoGongOrderMingXiMapper.delete(new BusinessBaoGongOrderMingXi(businessBaoGongOrder));
	}

	public boolean hasScOrderFromPlan(String planid){
		Integer rownum = mapper.hasScOrderFromPlan(planid);
		return rownum!=null;
	}
	
	public String getQrCode(String id){
		return mapper.getQrCodeById(id);
	}

	@Autowired
	private BusinessRuKuProductMapper businessRuKuProductMapper;

	public ProductRuKuBean getRuKuInfo(String bgcode){
		BusinessBaoGongOrder order = mapper.getByCode(bgcode);
		if(order==null){
			throw new RuntimeException("没有找到对应的报工单.");
		}
		if("0".equals(order.getComplate())){
			throw new RuntimeException("此报工单未报工完成.");
		}
		Double rukunum = businessRuKuProductMapper.getRuKuNumByBgid(order.getId());
		if(rukunum==null){
			rukunum = 0.0;
		}
		if(order.getNum()<=rukunum){
			throw new RuntimeException("此报工单入库数量已满,无法入库。");
		}
		ProductRuKuBean bean = new ProductRuKuBean();
		bean.setBatchno(order.getBatchno()).setBgcode(order.getBgcode()).setUnit(order.getUnit()).setBgid(order.getId())
		.setCinvcode(order.getCinvcode()).setCinvname(order.getCinvname()).setCinvstd(order.getCinvstd()).setSccode(order.getOrdercode())
		.setScline(order.getOrderline());
		bean.setNum(order.getNum()-rukunum);
		return bean;
	}



	// 根据报工单号 获取 报工信息
	public BaoGongBean getBaoGongInfo(String bgcode){
		BusinessBaoGongOrder order = getBaoGongInfo(null,null,bgcode);
		if(order==null){
			throw new RuntimeException("没有找到对应的报工单.");
		}
		if("1".equals(order.getComplate())){
			throw new RuntimeException("此单已完成.");
		}
		BaoGongBean bean = new BaoGongBean();
		bean.setBgcode(order.getBgcode()).setBgid(order.getId()).setGdnum(order.getNum()).setSccode(order.getOrdercode()).setScline(order.getOrderline());
		bean.setCinvcode(order.getCinvcode()).setCinvname(order.getCinvname()).setCinvstd(order.getCinvstd());
		if(order.getBusinessBaoGongOrderMingXiList()==null || order.getBusinessBaoGongOrderMingXiList().isEmpty()){
			bean.setSynum(Double.valueOf(order.getDelFlag()));
			return  bean;
		}
		order.getBusinessBaoGongOrderMingXiList().forEach(m->{
			BaoGongItem item = new BaoGongItem();
			item.setBghid(m.getId()).setSite(m.getSite()).setDbnum(m.getNum());
			bean.getBaoGongItems().add(item);
		});
		return bean;
	}

	@Transactional(readOnly = false)
	public void completeBg(String hid){
		businessBaoGongOrderMingXiMapper.completeBg(hid);
	}

	public void complete(String bgid,Double gdnum){
		double donenum = businessBaoGongRecordService.getDoneSumNum(bgid,null);
		if(donenum>=gdnum){
			mapper.completeBg(bgid);
		}

	}
}