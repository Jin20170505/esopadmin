/**
 * 
 */
package com.jeeplus.modules.business.ommoarrivalvouch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoDetail;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoMain;
import com.jeeplus.modules.business.ruku.ommo.mapper.BusinessRukuOmmoMxMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.u8data.ommoarrivalvouch.entity.U8OmmoArrivalVouch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalVouch;
import com.jeeplus.modules.business.ommoarrivalvouch.mapper.BusinessOmmoArrivalVouchMapper;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalvouchMx;
import com.jeeplus.modules.business.ommoarrivalvouch.mapper.BusinessOmmoArrivalvouchMxMapper;

/**
 * 委外到货单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessOmmoArrivalVouchService extends CrudService<BusinessOmmoArrivalVouchMapper, BusinessOmmoArrivalVouch> {

	@Autowired
	private BusinessOmmoArrivalvouchMxMapper businessOmmoArrivalvouchMxMapper;
	
	public BusinessOmmoArrivalVouch get(String id) {
		BusinessOmmoArrivalVouch businessOmmoArrivalVouch = super.get(id);
		businessOmmoArrivalVouch.setBusinessOmmoArrivalvouchMxList(businessOmmoArrivalvouchMxMapper.findList(new BusinessOmmoArrivalvouchMx(businessOmmoArrivalVouch)));
		return businessOmmoArrivalVouch;
	}
	
	public List<BusinessOmmoArrivalVouch> findList(BusinessOmmoArrivalVouch businessOmmoArrivalVouch) {
		return super.findList(businessOmmoArrivalVouch);
	}
	
	public Page<BusinessOmmoArrivalVouch> findPage(Page<BusinessOmmoArrivalVouch> page, BusinessOmmoArrivalVouch businessOmmoArrivalVouch) {
		return super.findPage(page, businessOmmoArrivalVouch);
	}
	@Autowired
	private BusinessRukuOmmoMxMapper rukuOmmoMxMapper;
	@Transactional(readOnly = false)
	public synchronized void save(BusinessOmmoArrivalVouch businessOmmoArrivalVouch) {
		if(StringUtils.isEmpty(businessOmmoArrivalVouch.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessOmmoArrivalVouch.setCode(code);
		}
		super.save(businessOmmoArrivalVouch);
		for (BusinessOmmoArrivalvouchMx businessOmmoArrivalvouchMx : businessOmmoArrivalVouch.getBusinessOmmoArrivalvouchMxList()){
			if (businessOmmoArrivalvouchMx.getId() == null){
				continue;
			}
			if (BusinessOmmoArrivalvouchMx.DEL_FLAG_NORMAL.equals(businessOmmoArrivalvouchMx.getDelFlag())){
				if (StringUtils.isBlank(businessOmmoArrivalvouchMx.getId())){
					businessOmmoArrivalvouchMx.setP(businessOmmoArrivalVouch);
					businessOmmoArrivalvouchMx.preInsert();
					businessOmmoArrivalvouchMxMapper.insert(businessOmmoArrivalvouchMx);
				}else{
					businessOmmoArrivalvouchMx.preUpdate();
					businessOmmoArrivalvouchMxMapper.update(businessOmmoArrivalvouchMx);
				}
			}else{
				Integer i = rukuOmmoMxMapper.hasWdhid(businessOmmoArrivalvouchMx.getId());
				if(i!=null && i==1){
					throw new RuntimeException("删除失败，原因：序号为【"+businessOmmoArrivalvouchMx.getNo()+"】的委外到货单明细有对应的委外入库单");
				}
				businessOmmoArrivalvouchMxMapper.delete(businessOmmoArrivalvouchMx);
			}
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "WWDH" +ymd + "00001";
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
	public void delete(BusinessOmmoArrivalVouch businessOmmoArrivalVouch) {
		Integer i = rukuOmmoMxMapper.hasWdid(businessOmmoArrivalVouch.getId());
		if(i!=null && i==1){
			throw new RuntimeException("删除失败，原因：【"+businessOmmoArrivalVouch.getCode()+"】的委外到货单有对应的委外入库单");
		}
		super.delete(businessOmmoArrivalVouch);
		businessOmmoArrivalvouchMxMapper.delete(new BusinessOmmoArrivalvouchMx(businessOmmoArrivalVouch));
	}
	@Transactional(readOnly = false)
	public void mainPrint(String id){
		mapper.print(id);
	}

	public BusinessOmmoArrivalvouchMx getMx(String mxid){
		return businessOmmoArrivalvouchMxMapper.get(mxid);
	}

	@Transactional(readOnly = false)
    public void sychu8(List<U8OmmoArrivalVouch> data) {
		List<BusinessOmmoArrivalVouch> mainList = new ArrayList<>();
		List<BusinessOmmoArrivalvouchMx> mxList = new ArrayList<>(data.size());
		data.forEach(d->{
			BusinessOmmoArrivalVouch main = getVouch(d.getMid(),mainList);
			if(main == null){
				main = new BusinessOmmoArrivalVouch();
				main.preInsert();
				main.setId(d.getMid());
				main.setCode(d.getCcode());
				main.setDdate(d.getDdate());
				main.setVendor(new BaseVendor(d.getCvencode()));
				main.setDept(new Office(d.getCdepcode()));
				main.setCmarker(d.getCmaker());
				main.setCpersonname(d.getCpersonname());
				mainList.add(main);
			}
			BusinessOmmoArrivalvouchMx mx = new BusinessOmmoArrivalvouchMx();
			mx.preInsert();
			mx.setP(main);
			mx.setId(d.getAutoid());
			mx.setScdate(d.getScdate());
			mx.setBatchno(d.getCbatch());
			mx.setNo(d.getIvouchrowno());
			mx.setCinvcode(d.getCinvcode());
			mx.setCinvname(d.getCinvname());
			mx.setCinvstd(d.getCinvstd());
			mx.setNum(d.getIquantity());
			mx.setUnit(d.getCinvmunit());
			mx.setCkcode(d.getCwhcode());
			mx.setCkname(d.getCwhname());
			mx.setHw(d.getCposcode());
			mx.setCsocode(d.getCsocode());
			mx.setIrowno(d.getIrowno()+"");
			mx.setMinnum(d.getMinNum());
			mxList.add(mx);
		});

		saveU8(mainList,mxList);
    }
	@Transactional(readOnly = false)
	public void saveU8(List<BusinessOmmoArrivalVouch> mains,List<BusinessOmmoArrivalvouchMx> details){
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
					businessOmmoArrivalvouchMxMapper.batchInsert(details.subList(j,mlen));
				}else {
					businessOmmoArrivalvouchMxMapper.batchInsert(details.subList(j,i));
				}
			}
		}
	}
    public BusinessOmmoArrivalVouch getVouch(String id,List<BusinessOmmoArrivalVouch> mainList){
		Optional<BusinessOmmoArrivalVouch> optional = mainList.stream().filter(d->id.equals(d.getId())).findAny();
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}

    public Page<BusinessOmmoArrivalvouchMx> findMxPage(Page<BusinessOmmoArrivalvouchMx> page, BusinessOmmoArrivalvouchMx businessOmmoArrivalvouchMx) {
		businessOmmoArrivalvouchMx.setPage(page);
		page.setList(businessOmmoArrivalvouchMxMapper.findMxList(businessOmmoArrivalvouchMx));
		return page;
	}

	@Transactional(readOnly = false)
    public void print(String id) {
		businessOmmoArrivalvouchMxMapper.print(id);
    }

    public BusinessOmmoArrivalvouchMx getMxByPidAndCinvcode(String pid,String cinvcode,String batchno,String scdate){
		return businessOmmoArrivalvouchMxMapper.getMxByPidAndCinvcode(pid, cinvcode, batchno,scdate);
	}
}