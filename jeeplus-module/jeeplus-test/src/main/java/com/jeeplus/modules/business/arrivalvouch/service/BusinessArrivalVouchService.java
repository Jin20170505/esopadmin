/**
 * 
 */
package com.jeeplus.modules.business.arrivalvouch.service;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.u8data.arrivalvouch.entity.U8ArrivalVouch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.mapper.BusinessArrivalVouchMapper;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.arrivalvouch.mapper.BusinessArrivalVouchMxMapper;

/**
 * 采购到货单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessArrivalVouchService extends CrudService<BusinessArrivalVouchMapper, BusinessArrivalVouch> {

	@Autowired
	private BusinessArrivalVouchMxMapper businessArrivalVouchMxMapper;
	
	public BusinessArrivalVouch get(String id) {
		BusinessArrivalVouch businessArrivalVouch = super.get(id);
		businessArrivalVouch.setBusinessArrivalVouchMxList(businessArrivalVouchMxMapper.findList(new BusinessArrivalVouchMx(businessArrivalVouch)));
		return businessArrivalVouch;
	}
	
	public List<BusinessArrivalVouch> findList(BusinessArrivalVouch businessArrivalVouch) {
		return super.findList(businessArrivalVouch);
	}
	
	public Page<BusinessArrivalVouch> findPage(Page<BusinessArrivalVouch> page, BusinessArrivalVouch businessArrivalVouch) {
		return super.findPage(page, businessArrivalVouch);
	}
	
	@Transactional(readOnly = false)
	public synchronized void save(BusinessArrivalVouch businessArrivalVouch) {
		if(StringUtils.isEmpty(businessArrivalVouch.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessArrivalVouch.setCode(code);
		}
		super.save(businessArrivalVouch);
		for (BusinessArrivalVouchMx businessArrivalVouchMx : businessArrivalVouch.getBusinessArrivalVouchMxList()){
			if (businessArrivalVouchMx.getId() == null){
				continue;
			}
			if (BusinessArrivalVouchMx.DEL_FLAG_NORMAL.equals(businessArrivalVouchMx.getDelFlag())){
				businessArrivalVouchMx.setP(businessArrivalVouch);
				businessArrivalVouchMx.setDept(businessArrivalVouch.getDept());
				businessArrivalVouchMx.setVendor(businessArrivalVouch.getVendor());
				if (StringUtils.isBlank(businessArrivalVouchMx.getId())){

					businessArrivalVouchMx.preInsert();
					businessArrivalVouchMxMapper.insert(businessArrivalVouchMx);
				}else{
					businessArrivalVouchMx.preUpdate();
					businessArrivalVouchMxMapper.update(businessArrivalVouchMx);
				}
			}else{
				businessArrivalVouchMxMapper.delete(businessArrivalVouchMx);
			}
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "CGDH" +ymd + "00001";
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
			}else{
				code = code+c;
			}
		}
		return code;
	}
	@Transactional(readOnly = false)
	public void delete(BusinessArrivalVouch businessArrivalVouch) {
		super.delete(businessArrivalVouch);
		businessArrivalVouchMxMapper.delete(new BusinessArrivalVouchMx(businessArrivalVouch));
	}

    public Page<BusinessArrivalVouchMx> findMxPage(Page<BusinessArrivalVouchMx> page, BusinessArrivalVouchMx businessArrivalVouchMx) {
		businessArrivalVouchMx.setPage(page);
		page.setList(businessArrivalVouchMxMapper.findMxList(businessArrivalVouchMx));
		return page;
	}

	@Transactional(readOnly = false)
	public void print(String id){
		businessArrivalVouchMxMapper.print(id);
	}
	@Transactional(readOnly = false)
	public void sychu8(List<U8ArrivalVouch> data) {
		List<BusinessArrivalVouch> list = Lists.newArrayList();
		List<BusinessArrivalVouchMx> mxList = Lists.newArrayList();
		data.forEach(d->{
			BusinessArrivalVouch vouch = getBusinessAouch(d.getMid(),list);
			if(vouch==null){
				vouch = new BusinessArrivalVouch();
				vouch.preInsert();
				vouch.setId(d.getMid());
				vouch.setCode(d.getCcode());
				vouch.setU8code(d.getCcode());
				vouch.setStatus(d.getVouchstate());
				vouch.setDept(new Office(d.getCdepcode()));
				vouch.setVendor(new BaseVendor(d.getCvencode()));
				vouch.setArriveDate(d.getDdate());
				list.add(vouch);
			}
			BusinessArrivalVouchMx mx = new BusinessArrivalVouchMx();
			mx.preInsert();
			mx.setP(vouch);
			mx.setId(d.getAutoid());
			mx.setNo(d.getIvouchrowno());
			mx.setCordercode(d.getCordercode());
			mx.setIrowno(d.getIrowno());
			mx.setDept(vouch.getDept());
			mx.setVendor(vouch.getVendor());
			mx.setCk(new BaseCangKu(d.getCwhcode()));
			mx.setHw(new BaseHuoWei(d.getCposcode()));
			mx.setBatchno(d.getCbatch());
			mx.setScdate(d.getDpdate());
			mx.setMinnum(d.getcInvDefine1());
			mx.setCinvcode(d.getCinvcode());
			mx.setCinvname(d.getCinvname());
			mx.setCinvstd(d.getCinvstd());
			mx.setNum(d.getIquantity());
			mx.setUnit(d.getCinvmunit());
			mxList.add(mx);
		});
		saveU8Data(list,mxList);
	}
	@Transactional(readOnly = false)
	public void saveU8Data(List<BusinessArrivalVouch> mains,List<BusinessArrivalVouchMx> details){
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
					businessArrivalVouchMxMapper.batchInsert(details.subList(j,mlen));
				}else {
					businessArrivalVouchMxMapper.batchInsert(details.subList(j,i));
				}
			}
		}
	}

	public BusinessArrivalVouch getBusinessAouch(String mid, List<BusinessArrivalVouch> list){
		Optional<BusinessArrivalVouch> optional = list.stream().filter(d->mid.equals(d.getId())).findAny();
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}

	public BusinessArrivalVouchMx getMx(String mxid){
		return businessArrivalVouchMxMapper.get(mxid);
	}

	public BusinessArrivalVouchMx getMxByCinvcodeAndBatchno(String pid,String cinvcode,String batchno,String scdate){
		return businessArrivalVouchMxMapper.getMxByCinvcodeAndBatchno(pid,cinvcode,batchno,scdate);
	}

	@Transactional(readOnly = false)
	public void mainPrint(String id){
		mapper.print(id);
	}
}