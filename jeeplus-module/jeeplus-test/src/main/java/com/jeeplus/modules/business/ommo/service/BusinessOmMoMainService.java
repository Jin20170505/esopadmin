/**
 * 
 */
package com.jeeplus.modules.business.ommo.service;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.business.chuku.ommo.mapper.BusinessChuKuWeiWaiMapper;
import com.jeeplus.modules.business.ommo.bom.mapper.BussinessOmMoYongItemMapper;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.u8data.ommo.entity.U8OmMoMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoMain;
import com.jeeplus.modules.business.ommo.mapper.BusinessOmMoMainMapper;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoDetail;
import com.jeeplus.modules.business.ommo.mapper.BusinessOmMoDetailMapper;

/**
 * 委外订单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessOmMoMainService extends CrudService<BusinessOmMoMainMapper, BusinessOmMoMain> {

	@Autowired
	private BusinessOmMoDetailMapper businessOmMoDetailMapper;

	public BusinessOmMoDetail getDetail(String id){
		return businessOmMoDetailMapper.get(id);
	}
	public BusinessOmMoMain get(String id) {
		BusinessOmMoMain businessOmMoMain = super.get(id);
		businessOmMoMain.setBusinessOmMoDetailList(businessOmMoDetailMapper.findList(new BusinessOmMoDetail(businessOmMoMain)));
		return businessOmMoMain;
	}
	
	public List<BusinessOmMoMain> findList(BusinessOmMoMain businessOmMoMain) {
		return super.findList(businessOmMoMain);
	}
	
	public Page<BusinessOmMoMain> findPage(Page<BusinessOmMoMain> page, BusinessOmMoMain businessOmMoMain) {
		return super.findPage(page, businessOmMoMain);
	}

	public Page<BusinessOmMoDetail> findMxPage(Page<BusinessOmMoDetail> page, BusinessOmMoDetail businessOmMoDetail) {
		dataRuleFilter(businessOmMoDetail);
		businessOmMoDetail.setPage(page);
		page.setList(businessOmMoDetailMapper.findList(businessOmMoDetail));
		return page;
	}
	@Autowired
	private BusinessChuKuWeiWaiMapper chuKuWeiWaiMapper;
	@Autowired
	private BussinessOmMoYongItemMapper bussinessOmMoYongItemMapper;
	@Transactional(readOnly = false)
	public synchronized void save(BusinessOmMoMain businessOmMoMain) {
		if(StringUtils.isEmpty(businessOmMoMain.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessOmMoMain.setCode(code);
		}
		super.save(businessOmMoMain);
		for (BusinessOmMoDetail businessOmMoDetail : businessOmMoMain.getBusinessOmMoDetailList()){
			if (businessOmMoDetail.getId() == null){
				continue;
			}
			if (BusinessOmMoDetail.DEL_FLAG_NORMAL.equals(businessOmMoDetail.getDelFlag())){
				if (StringUtils.isBlank(businessOmMoDetail.getId())){
					businessOmMoDetail.setMo(businessOmMoMain);
					businessOmMoDetail.preInsert();
					businessOmMoDetailMapper.insert(businessOmMoDetail);
				}else{
					businessOmMoDetail.setMo(businessOmMoMain);
					businessOmMoDetail.preUpdate();
					businessOmMoDetailMapper.update(businessOmMoDetail);
				}
			}else{
				Integer i = chuKuWeiWaiMapper.hasByWwHid(businessOmMoDetail.getId());
				if(i!=null && i==1){
					throw new RuntimeException("删除失败，原因：【"+businessOmMoDetail.getNo()+"】的明细有对应的委外发料单");
				}
				businessOmMoDetailMapper.delete(businessOmMoDetail);
				bussinessOmMoYongItemMapper.deleteByOmHid(businessOmMoDetail.getId());
			}
		}
	}

	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "WWDD" +ymd + "00001";
		}else {
			code = maxcode.substring(0,12);
			int c =  Integer.valueOf(maxcode.substring(12));
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
	public void delete(BusinessOmMoMain businessOmMoMain) {
		Integer i = chuKuWeiWaiMapper.hasByWwid(businessOmMoMain.getId());
		if(i!=null && i==1){
			throw new RuntimeException("删除失败，原因：【"+businessOmMoMain.getCode()+"】的委外订单有对应的委外发料单");
		}
		super.delete(businessOmMoMain);
		businessOmMoDetailMapper.delete(new BusinessOmMoDetail(businessOmMoMain));
		if(businessOmMoMain.getBusinessOmMoDetailList()!=null){
			businessOmMoMain.getBusinessOmMoDetailList().forEach(d->{
				bussinessOmMoYongItemMapper.deleteByOmHid(d.getId());
			});
		}
	}

	@Transactional(readOnly = false)
    public List<String> sychu8(List<U8OmMoMain> data) {
		List<String> ids = Lists.newArrayList();
		List<BusinessOmMoMain> list = Lists.newArrayList();
		List<BusinessOmMoDetail> details = Lists.newArrayList();
		data.forEach(d->{
			BusinessOmMoMain main = getBusinessOmMoMain(d.getMoid(),list);
			if(main == null){
				main = new BusinessOmMoMain();
				main.preInsert();
				main.setId(d.getMoid());
				main.setCode(d.getcCode());
				main.setU8code(d.getcCode());
				main.setDcreatedate(d.getdCreateTime());
				main.setDdate(d.getdDate());
				main.setVendor(new BaseVendor(d.getcVenCode()));
				main.setMemo(d.getcMemo());
				list.add(main);
			}
			BusinessOmMoDetail mx = new BusinessOmMoDetail();
			mx.preInsert();
			mx.setMo(main);
			mx.setId(d.getMoDetailsID());
			mx.setArrivedate(d.getdArriveDate());
			mx.setStartdate(d.getdStartDate());
			mx.setNo(d.getiVouchRowNo());
			mx.setCinvcode(d.getcInvCode());
			mx.setCinvname(d.getcInvName());
			mx.setCinvstd(d.getcInvStd());
			mx.setNum(d.getiQuantity());
			mx.setUnit(d.getcComUnitName());
			mx.setMemo(d.getCbMemo());
			details.add(mx);
			ids.add(mx.getId());
		});
		saveU8(list,details);
		return ids;
    }
	@Transactional(readOnly = false)
    public void saveU8(List<BusinessOmMoMain> mains,List<BusinessOmMoDetail> details){
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
					businessOmMoDetailMapper.batchInsert(details.subList(j,mlen));
				}else {
					businessOmMoDetailMapper.batchInsert(details.subList(j,i));
				}
			}
		}
	}

	public BusinessOmMoMain getBusinessOmMoMain(String id, List<BusinessOmMoMain> list){
		Optional<BusinessOmMoMain> optional = list.stream().filter(d->id.equals(d.getId())).findAny();
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}

	@Transactional(readOnly = false)
	public void print(String rid){
		businessOmMoDetailMapper.print(rid);
	}
}