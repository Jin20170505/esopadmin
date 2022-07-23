/**
 * 
 */
package com.jeeplus.modules.business.ommo.chaidan.service;

import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.business.chuku.ommo.mapper.BusinessChuKuWeiWaiMapper;
import com.jeeplus.modules.business.ommo.mapper.BusinessOmMoDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.ommo.chaidan.entity.BusinessOmmoChaiDan;
import com.jeeplus.modules.business.ommo.chaidan.mapper.BusinessOmmoChaiDanMapper;
import com.jeeplus.modules.business.ommo.chaidan.entity.BusinessOmmoChaiDanMx;
import com.jeeplus.modules.business.ommo.chaidan.mapper.BusinessOmmoChaiDanMxMapper;

/**
 * 委外拆单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessOmmoChaiDanService extends CrudService<BusinessOmmoChaiDanMapper, BusinessOmmoChaiDan> {

	@Autowired
	private BusinessOmmoChaiDanMxMapper businessOmmoChaiDanMxMapper;
	
	public BusinessOmmoChaiDan get(String id) {
		BusinessOmmoChaiDan businessOmmoChaiDan = super.get(id);
		businessOmmoChaiDan.setBusinessOmmoChaiDanMxList(businessOmmoChaiDanMxMapper.findList(new BusinessOmmoChaiDanMx(businessOmmoChaiDan)));
		return businessOmmoChaiDan;
	}

	public BusinessOmmoChaiDan getMain(String id){
		return super.get(id);
	}

	public List<BusinessOmmoChaiDan> findList(BusinessOmmoChaiDan businessOmmoChaiDan) {
		return super.findList(businessOmmoChaiDan);
	}
	
	public Page<BusinessOmmoChaiDan> findPage(Page<BusinessOmmoChaiDan> page, BusinessOmmoChaiDan businessOmmoChaiDan) {
		return super.findPage(page, businessOmmoChaiDan);
	}
	
	@Transactional(readOnly = false)
	public synchronized void save(BusinessOmmoChaiDan businessOmmoChaiDan) {
		if(StringUtils.isEmpty(businessOmmoChaiDan.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessOmmoChaiDan.setCode(code);
		}
		super.save(businessOmmoChaiDan);
		for (BusinessOmmoChaiDanMx businessOmmoChaiDanMx : businessOmmoChaiDan.getBusinessOmmoChaiDanMxList()){
			if (businessOmmoChaiDanMx.getId() == null){
				continue;
			}
			if (BusinessOmmoChaiDanMx.DEL_FLAG_NORMAL.equals(businessOmmoChaiDanMx.getDelFlag())){
				if (StringUtils.isBlank(businessOmmoChaiDanMx.getId())){
					businessOmmoChaiDanMx.setMain(businessOmmoChaiDan);
					businessOmmoChaiDanMx.preInsert();
					businessOmmoChaiDanMxMapper.insert(businessOmmoChaiDanMx);
				}else{
					businessOmmoChaiDanMx.preUpdate();
					businessOmmoChaiDanMxMapper.update(businessOmmoChaiDanMx);
				}
			}else{
				businessOmmoChaiDanMxMapper.delete(businessOmmoChaiDanMx);
			}
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "WWCD" +ymd + "00001";
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
	@Autowired
	private BusinessOmMoDetailMapper businessOmMoDetailMapper;
	@Autowired
	private BusinessChuKuWeiWaiMapper businessChuKuWeiWaiMapper;

	@Transactional(readOnly = false)
	public void delete(BusinessOmmoChaiDan businessOmmoChaiDan) {
		Integer i = businessChuKuWeiWaiMapper.hasByChaidanid(businessOmmoChaiDan.getId());
		if(i!=null){
			throw new RuntimeException("删除失败，单号["+businessOmmoChaiDan.getCode()+"]有对应的出库单");
		}
		super.delete(businessOmmoChaiDan);
		businessOmmoChaiDanMxMapper.delete(new BusinessOmmoChaiDanMx(businessOmmoChaiDan));
		businessOmMoDetailMapper.chaidan(businessOmmoChaiDan.getWwhid(),"未拆完");
	}
	@Transactional(readOnly = false)
    public void print(String rid) {
		mapper.print(rid);
    }

	public BusinessOmmoChaiDanMx getMx(String rid) {
		return businessOmmoChaiDanMxMapper.get(rid);
	}
}