/**
 * 
 */
package com.jeeplus.modules.business.dispatch.service;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.chuku.dispatch.mapper.BusinessChukuDispatchMxMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.u8data.dispatch.entity.U8Dispatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;
import com.jeeplus.modules.business.dispatch.mapper.BusinessDispatchMapper;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatchMx;
import com.jeeplus.modules.business.dispatch.mapper.BusinessDispatchMxMapper;

/**
 * 销售发货单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessDispatchService extends CrudService<BusinessDispatchMapper, BusinessDispatch> {

	@Autowired
	private BusinessDispatchMxMapper businessDispatchMxMapper;
	
	public BusinessDispatch get(String id) {
		BusinessDispatch businessDispatch = super.get(id);
		businessDispatch.setBusinessDispatchMxList(businessDispatchMxMapper.findList(new BusinessDispatchMx(businessDispatch)));
		return businessDispatch;
	}
	
	public List<BusinessDispatch> findList(BusinessDispatch businessDispatch) {
		return super.findList(businessDispatch);
	}
	
	public Page<BusinessDispatch> findPage(Page<BusinessDispatch> page, BusinessDispatch businessDispatch) {
		return super.findPage(page, businessDispatch);
	}
	@Transactional(readOnly = false)
	public void print(String rid){
		mapper.print(rid);
	}
	@Transactional(readOnly = false)
	public void save(BusinessDispatch businessDispatch) {
		super.save(businessDispatch);
		for (BusinessDispatchMx businessDispatchMx : businessDispatch.getBusinessDispatchMxList()){
			if (businessDispatchMx.getId() == null){
				continue;
			}
			if (BusinessDispatchMx.DEL_FLAG_NORMAL.equals(businessDispatchMx.getDelFlag())){
				businessDispatchMx.setP(businessDispatch);
				businessDispatchMx.setCustomer(businessDispatch.getCustomer());
				businessDispatch.setDept(businessDispatch.getDept());
				if (StringUtils.isBlank(businessDispatchMx.getId())){

					businessDispatchMx.preInsert();
					businessDispatchMxMapper.insert(businessDispatchMx);
				}else{
					businessDispatchMx.preUpdate();
					businessDispatchMxMapper.update(businessDispatchMx);
				}
			}else{
				businessDispatchMxMapper.delete(businessDispatchMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessDispatch businessDispatch) {
		super.delete(businessDispatch);
		businessDispatchMxMapper.delete(new BusinessDispatchMx(businessDispatch));
	}
	@Transactional(readOnly = false)
    public void sychu8(List<U8Dispatch> data) {
		List<BusinessDispatch> list = Lists.newArrayList();
		data.forEach(d->{
			BusinessDispatch dispatch = getBusinessDispatch(d.getMid(),list);
			if(dispatch==null){
				dispatch = new BusinessDispatch();
				dispatch.preInsert();
				dispatch.setId(d.getMid());
				dispatch.setCode(d.getCdlcode());
				dispatch.setU8code(d.getCdlcode());
				dispatch.setStatus("");
				dispatch.setDept(new Office(d.getCdepcode()));
				dispatch.setCustomer(new BaseCustomer(d.getcCusCode()));
				dispatch.setFahuodate(d.getFahuoDate());
			}
			BusinessDispatchMx mx = new BusinessDispatchMx();
			mx.preInsert();
			mx.setP(dispatch);
			mx.setId(d.getLineid());
			mx.setNo(d.getIrowno());
			mx.setCordercode(d.getcSoCode());
			mx.setIrowno(d.getIrowno()+"");
			mx.setDept(dispatch.getDept());
			mx.setCustomer(dispatch.getCustomer());
			mx.setCk(new BaseCangKu(d.getcWhCode()));
			mx.setHw(new BaseHuoWei(d.getcPosition()));
			mx.setBatchno(d.getcBatch());
			mx.setScdate(d.getScdate());
			mx.setCinvcode(d.getcInvCode());
			mx.setCinvname(d.getcInvName());
			mx.setCinvstd(d.getcInvStd());
			mx.setNum(d.getIquantity());
			mx.setUnit(d.getcComUnitName());
			dispatch.getBusinessDispatchMxList().add(mx);
			list.add(dispatch);
		});
		saveU8Data(list);
	}
	@Transactional(readOnly = false)
	public void saveU8Data(List<BusinessDispatch> list){
		list.forEach(d->{
			if(null == mapper.hasById(d.getId())){
				mapper.insert(d);
			}
			d.getBusinessDispatchMxList().forEach(e->{
				if(null==businessDispatchMxMapper.hasById(e.getId())){
					businessDispatchMxMapper.insert(e);
				}
			});
		});
	}

	public BusinessDispatch getBusinessDispatch(String mid, List<BusinessDispatch> list){
		Optional<BusinessDispatch> optional = list.stream().filter(d->mid.equals(d.getId())).findAny();
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}
	@Autowired
	private BusinessChukuDispatchMxMapper chukuDispatchMxMapper;
	public BusinessDispatch getInfo(String xsfhid) {
		Integer i = chukuDispatchMxMapper.hasChuKu(xsfhid);
		if(i!=null){
			throw  new RuntimeException("此单已出库。");
		}
		return get(xsfhid);
	}
}