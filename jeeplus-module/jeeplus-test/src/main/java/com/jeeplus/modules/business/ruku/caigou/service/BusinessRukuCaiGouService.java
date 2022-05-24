/**
 * 
 */
package com.jeeplus.modules.business.ruku.caigou.service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.mapper.BusinessArrivalVouchMapper;
import com.jeeplus.modules.business.arrivalvouch.mapper.BusinessArrivalVouchMxMapper;
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaiGou;
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaigouMx;
import com.jeeplus.modules.business.ruku.caigou.mapper.BusinessRukuCaiGouMapper;
import com.jeeplus.modules.business.ruku.caigou.mapper.BusinessRukuCaigouMxMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采购入库Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessRukuCaiGouService extends CrudService<BusinessRukuCaiGouMapper, BusinessRukuCaiGou> {

	@Autowired
	private BusinessRukuCaigouMxMapper businessRukuCaigouMxMapper;
	
	public BusinessRukuCaiGou get(String id) {
		BusinessRukuCaiGou businessRukuCaiGou = super.get(id);
		businessRukuCaiGou.setBusinessRukuCaigouMxList(businessRukuCaigouMxMapper.findList(new BusinessRukuCaigouMx(businessRukuCaiGou)));
		return businessRukuCaiGou;
	}
	
	public List<BusinessRukuCaiGou> findList(BusinessRukuCaiGou businessRukuCaiGou) {
		return super.findList(businessRukuCaiGou);
	}
	
	public Page<BusinessRukuCaiGou> findPage(Page<BusinessRukuCaiGou> page, BusinessRukuCaiGou businessRukuCaiGou) {
		return super.findPage(page, businessRukuCaiGou);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessRukuCaiGou businessRukuCaiGou) {
		super.save(businessRukuCaiGou);
		for (BusinessRukuCaigouMx businessRukuCaigouMx : businessRukuCaiGou.getBusinessRukuCaigouMxList()){
			if (businessRukuCaigouMx.getId() == null){
				continue;
			}
			if (BusinessRukuCaigouMx.DEL_FLAG_NORMAL.equals(businessRukuCaigouMx.getDelFlag())){
				if (StringUtils.isBlank(businessRukuCaigouMx.getId())){
					businessRukuCaigouMx.setP(businessRukuCaiGou);
					businessRukuCaigouMx.preInsert();
					businessRukuCaigouMxMapper.insert(businessRukuCaigouMx);
				}else{
					businessRukuCaigouMx.preUpdate();
					businessRukuCaigouMxMapper.update(businessRukuCaigouMx);
				}
			}else{
				businessRukuCaigouMxMapper.delete(businessRukuCaigouMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessRukuCaiGou businessRukuCaiGou) {
		super.delete(businessRukuCaiGou);
		businessRukuCaigouMxMapper.delete(new BusinessRukuCaigouMx(businessRukuCaiGou));
	}

	@Autowired
	private BusinessArrivalVouchMapper arrivalVouchMapper;
	@Autowired
	private BusinessArrivalVouchMxMapper arrivalVouchMxMapper;
	@Transactional(readOnly = false)
	public void doRuKu(String cgid,String hwid,String mxJson){
		BusinessRukuCaiGou businessRukuCaiGou = new BusinessRukuCaiGou();
		BusinessArrivalVouch vouch = arrivalVouchMapper.get(cgid);
		businessRukuCaiGou.setArrivalcode(vouch.getCode());
		businessRukuCaiGou.setArrivaldate(DateUtils.formatDate(vouch.getArriveDate()));
		businessRukuCaiGou.setHw(new BaseHuoWei(hwid));
		businessRukuCaiGou.setCode("CGRKD"+DateUtils.getDate("yyyyMMddHHmmss"));
		businessRukuCaiGou.setU8code(businessRukuCaiGou.getCode());
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray array = json.getJSONArray("list");
		array.forEach(d->{
			JSONObject j = JSONObject.fromObject(d);
			Integer no = j.optInt("no");
			String cghid = j.getString("cghid");
			Double num = j.optDouble("num");
			BusinessRukuCaigouMx mx  = new BusinessRukuCaigouMx();
			mx.setDelFlag("0");
			mx.setId("");
			mx.setCinvcode(j.optString("cinvcode"));
			mx.setCinvname(j.optString("cinvname"));
			mx.setCinvstd(j.optString("cinvstd"));
			mx.setBatchno(j.optString("batchno"));
			mx.setNum(num);
			mx.setNo(no);
			mx.setScdate(j.optString("scdate"));
			mx.setUnit(j.optString("unit"));
			mx.setCgid(cgid).setCghid(cghid);
			businessRukuCaiGou.getBusinessRukuCaigouMxList().add(mx);
		});
		// 判断数量超额与否
		save(businessRukuCaiGou);
	}
}