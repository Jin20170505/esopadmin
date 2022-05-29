/**
 * 
 */
package com.jeeplus.modules.business.pandian.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.number.RandomUtil;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.pandian.entity.BusinessPanDian;
import com.jeeplus.modules.business.pandian.mapper.BusinessPanDianMapper;
import com.jeeplus.modules.business.pandian.entity.BusinessPanDianMx;
import com.jeeplus.modules.business.pandian.mapper.BusinessPanDianMxMapper;

/**
 * 盘点单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessPanDianService extends CrudService<BusinessPanDianMapper, BusinessPanDian> {

	@Autowired
	private BusinessPanDianMxMapper businessPanDianMxMapper;
	
	public BusinessPanDian get(String id) {
		BusinessPanDian businessPanDian = super.get(id);
		businessPanDian.setBusinessPanDianMxList(businessPanDianMxMapper.findList(new BusinessPanDianMx(businessPanDian)));
		return businessPanDian;
	}
	
	public List<BusinessPanDian> findList(BusinessPanDian businessPanDian) {
		return super.findList(businessPanDian);
	}
	
	public Page<BusinessPanDian> findPage(Page<BusinessPanDian> page, BusinessPanDian businessPanDian) {
		return super.findPage(page, businessPanDian);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessPanDian businessPanDian) {
		super.save(businessPanDian);
		for (BusinessPanDianMx businessPanDianMx : businessPanDian.getBusinessPanDianMxList()){
			if (businessPanDianMx.getId() == null){
				continue;
			}
			if (BusinessPanDianMx.DEL_FLAG_NORMAL.equals(businessPanDianMx.getDelFlag())){
				businessPanDianMx.setP(businessPanDian);
				if (StringUtils.isBlank(businessPanDianMx.getId())){
					businessPanDianMx.preInsert();
					businessPanDianMxMapper.insert(businessPanDianMx);
				}else{
					businessPanDianMx.preUpdate();
					businessPanDianMxMapper.update(businessPanDianMx);
				}
			}else{
				businessPanDianMxMapper.delete(businessPanDianMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessPanDian businessPanDian) {
		super.delete(businessPanDian);
		businessPanDianMxMapper.delete(new BusinessPanDianMx(businessPanDian));
	}
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
	public  void doPandian(String userid, String ck, String hw, String mxJson) {
		String ckcode = cangKuMapper.getCodeById(ck);
		User user = UserUtils.get(userid);
		BaseCangKu cangKu = new BaseCangKu(ck);
		BaseHuoWei huoWei = new BaseHuoWei(hw);
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray array = json.getJSONArray("list");
		BusinessPanDian main = new BusinessPanDian();
		String ddate = DateUtils.getDate();
		main.setDdate(ddate);
		main.setDuser(user);
		main.setCk(cangKu);main.setHw(huoWei);
		String code = "";
		synchronized (this){
			code = "PDD"+DateUtils.getDate("yyyyMMddHHmmss")+ RandomUtil.nextInt(100,999);
		}
		main.setCode(code);
		// 其他出库
		List<BusinessPanDianMx> outck = Lists.newArrayList();
		// 其他入库
		List<BusinessPanDianMx> inck = Lists.newArrayList();
		array.forEach(a->{
			JSONObject j = JSONObject.fromObject(a);
			BusinessPanDianMx mx = new BusinessPanDianMx();
			mx.setId("");mx.setDelFlag("0");
			mx.setNo(j.optInt("no"));
			mx.setCinvcode(j.optString("cinvcode"));
			mx.setCinvname(j.optString("cinvname"));
			mx.setCinvstd(j.optString("cinvstd"));
			mx.setNum(j.optDouble("num",0.0));
			mx.setPannum(j.optDouble("pannum",0.0));
			mx.setCkcode(ckcode);mx.setHwcode(hw);
			mx.setCha(mx.getPannum()- mx.getNum());
			mx.setUnit(j.optString("unit"));
			mx.setScdate(j.optString("scdate",""));
			mx.setBatchno(j.optString("batchno",""));
			main.getBusinessPanDianMxList().add(mx);
			if(mx.getPannum().toString().equals(mx.getNum().toString())){

			}else {
				if(mx.getPannum() > mx.getNum()){
					inck.add(mx);
				}
				if(mx.getPannum() < mx.getNum()){
					outck.add(mx);
				}
			}
		});
		save(main);
		// TODO U8接口
		// 其他入库
		if(inck.size()>0){

		}
		// 其他出库
		if(outck.size()>0){

		}
	}
}