/**
 * 
 */
package com.jeeplus.modules.business.dispatch.service;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.chuku.dispatch.mapper.BusinessChukuDispatchMapper;
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
	@Autowired
	private BusinessChukuDispatchMapper businessChukuDispatchMapper;
	@Transactional(readOnly = false)
	public synchronized void save(BusinessDispatch businessDispatch) {
		if(StringUtils.isEmpty(businessDispatch.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessDispatch.setCode(code);
		}
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
				Integer i = chukuDispatchMxMapper.hasChuKu(businessDispatchMx.getId());
				if(i!=null && i==1){
					throw new RuntimeException("删除失败，原因：【"+businessDispatchMx.getNo()+"】的明细有对应的销售出库单");
				}
				businessDispatchMxMapper.delete(businessDispatchMx);
			}
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "XSFH" +ymd + "00001";
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
	@Transactional(readOnly = false)
	public void delete(BusinessDispatch businessDispatch) {
		Integer i = businessChukuDispatchMapper.hasXSFHCode(businessDispatch.getCode());
		if(i!=null && i==1){
			throw new RuntimeException("删除失败，原因：【"+businessDispatch.getCode()+"】的工单有对应的销售出库单");
		}
		super.delete(businessDispatch);
		businessDispatchMxMapper.delete(new BusinessDispatchMx(businessDispatch));
	}
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
    public void sychu8(List<U8Dispatch> data) {
		List<BusinessDispatch> list = Lists.newArrayList();
		List<BusinessDispatchMx> mxlist = Lists.newArrayList();
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
				list.add(dispatch);
			}
			BusinessDispatchMx mx = new BusinessDispatchMx();
			String ckid = cangKuMapper.getIdByCode(d.getcWhCode());
			mx.preInsert();
			mx.setP(dispatch);
			mx.setId(d.getLineid());
			mx.setNo(d.getIrowno());
			mx.setCordercode(d.getcSoCode());
			mx.setIrowno(d.getIrowno()+"");
			mx.setDept(dispatch.getDept());
			mx.setCustomer(dispatch.getCustomer());
			mx.setCk(new BaseCangKu(ckid));
			mx.setHw(new BaseHuoWei(d.getcPosition()));
			mx.setBatchno(d.getcBatch());
			mx.setScdate(d.getScdate());
			mx.setCinvcode(d.getcInvCode());
			mx.setCinvname(d.getcInvName());
			mx.setCinvstd(d.getcInvStd());
			mx.setNum(d.getIquantity());
			mx.setUnit(d.getcComUnitName());
			mxlist.add(mx);
		});
		saveU8Data(list,mxlist);
	}
	@Transactional(readOnly = false)
	public void saveU8Data(List<BusinessDispatch> mains,List<BusinessDispatchMx> details){
		if(!mains.isEmpty()){
			int i = 0;
			int j = 0;
			int mlen = mains.size();
			while (i<mlen){
				j = i;
				i = i+300;
				if(i>=mlen){
					mapper.batchInsert(mains.subList(j,mlen));
				}else {
					mapper.batchInsert(mains.subList(j,i));
				}
			}
		}
		if(!details.isEmpty()){
			int i = 0;
			int j = 0;
			int mlen = details.size();
			while (i<mlen){
				j = i;
				i = i+300;
				if(i>=mlen){
					businessDispatchMxMapper.batchInsert(details.subList(j,mlen));
				}else {
					businessDispatchMxMapper.batchInsert(details.subList(j,i));
				}
			}
		}
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