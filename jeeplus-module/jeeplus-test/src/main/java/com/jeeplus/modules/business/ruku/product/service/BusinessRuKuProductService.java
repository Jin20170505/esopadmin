/**
 * 
 */
package com.jeeplus.modules.business.ruku.product.service;

import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMapper;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMxMapper;

/**
 * 产成品入库Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessRuKuProductService extends CrudService<BusinessRuKuProductMapper, BusinessRuKuProduct> {
	@Autowired
	private BusinessRuKuProductMxMapper businessRuKuProductMxMapper;
	public BusinessRuKuProduct get(String id) {
		BusinessRuKuProduct businessRuKuProduct = super.get(id);
		businessRuKuProduct.setBusinessRuKuProductMxList(businessRuKuProductMxMapper.findList(new BusinessRuKuProductMx(businessRuKuProduct)));
		return businessRuKuProduct;
	}
	
	public List<BusinessRuKuProduct> findList(BusinessRuKuProduct businessRuKuProduct) {
		return super.findList(businessRuKuProduct);
	}
	
	public Page<BusinessRuKuProduct> findPage(Page<BusinessRuKuProduct> page, BusinessRuKuProduct businessRuKuProduct) {
		return super.findPage(page, businessRuKuProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessRuKuProduct businessRuKuProduct) {
		super.save(businessRuKuProduct);
		for (BusinessRuKuProductMx businessRuKuProductMx : businessRuKuProduct.getBusinessRuKuProductMxList()){
			if (businessRuKuProductMx.getId() == null){
				continue;
			}
			if (BusinessRuKuProductMx.DEL_FLAG_NORMAL.equals(businessRuKuProductMx.getDelFlag())){
				if (StringUtils.isBlank(businessRuKuProductMx.getId())){
					businessRuKuProductMx.setP(businessRuKuProduct);
					businessRuKuProductMx.preInsert();
					businessRuKuProductMxMapper.insert(businessRuKuProductMx);
				}else{
					businessRuKuProductMx.preUpdate();
					businessRuKuProductMxMapper.update(businessRuKuProductMx);
				}
			}else{
				businessRuKuProductMxMapper.delete(businessRuKuProductMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessRuKuProduct businessRuKuProduct) {
		super.delete(businessRuKuProduct);
		businessRuKuProductMxMapper.delete(new BusinessRuKuProductMx(businessRuKuProduct));
	}
	@Autowired
	private BusinessBaoGongOrderService businessBaoGongOrderService;
	@Transactional(readOnly = false)
	public void ruku(String bgid,String ckid,String hwid,String remarks,String userid,Double rukunum){
		BusinessBaoGongOrder order  = businessBaoGongOrderService.get(bgid);
		if(order==null){
			throw new RuntimeException("报工单不存在啦");
		}
		BusinessRuKuProduct product = new BusinessRuKuProduct();
		BusinessRuKuProductMx mx = new BusinessRuKuProductMx();
		product.setBatchno(order.getBatchno());
		product.setBgcode(order.getBgcode());
		product.setBgid(order.getId());
		product.setCangku(new BaseCangKu(ckid));
		product.setCinvcode(order.getCinvcode());
		product.setCinvname(order.getCinvname());
		product.setCinvstd(order.getCinvstd());
		product.setCode("CPRK"+ DateUtils.getDate("yyyyMMddHHmmss"));
		product.setNum(rukunum);
		product.setSych("0");
		product.setSccode(order.getOrdercode());
		product.setRemarks(remarks);
		product.preInsert();
		product.setCreateBy(new User(userid));
		mx.setP(product);
		mx.setHuowei(new BaseHuoWei(hwid));
		mx.setLinecode(order.getOrderline());
		mx.setSchid(order.getOrderlineid());
		mx.setSchid(order.getOrderlineid());
		mx.setUnit(order.getUnit());
		mx.setCinvcode(order.getCinvcode());
		mx.setCinvname(order.getCinvname());
		mx.setCinvstd(order.getCinvstd());
		mx.setNum(rukunum);
		mx.setSych("0");
		mx.setRemarks(remarks);
		mx.preInsert();
		mx.setCreateBy(new User(userid));
		mapper.insert(product);
		businessRuKuProductMxMapper.insert(mx);
	}

}