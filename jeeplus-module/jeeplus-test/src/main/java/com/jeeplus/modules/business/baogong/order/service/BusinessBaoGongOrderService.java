/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.service;

import java.util.List;

import com.jeeplus.modules.api.bean.baogong.BaoGongBean;
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
	
	public BusinessBaoGongOrder get(String id) {
		BusinessBaoGongOrder businessBaoGongOrder = super.get(id);
		businessBaoGongOrder.setBusinessBaoGongOrderMingXiList(businessBaoGongOrderMingXiMapper.findList(new BusinessBaoGongOrderMingXi(businessBaoGongOrder)));
		return businessBaoGongOrder;
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

	// 根据报工单号 获取 报工信息
	public BaoGongBean getBaoGongInfo(String bgcode){
		BaoGongBean bean = new BaoGongBean();

		return bean;
	}

}