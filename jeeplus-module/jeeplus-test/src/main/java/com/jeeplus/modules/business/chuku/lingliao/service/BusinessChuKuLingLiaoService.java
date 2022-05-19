/**
 * 
 */
package com.jeeplus.modules.business.chuku.lingliao.service;

import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.sys.entity.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiao;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMapper;
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiaoMx;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMxMapper;

/**
 * 材料出库单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessChuKuLingLiaoService extends CrudService<BusinessChuKuLingLiaoMapper, BusinessChuKuLingLiao> {

	@Autowired
	private BusinessChuKuLingLiaoMxMapper businessChuKuLingLiaoMxMapper;
	
	public BusinessChuKuLingLiao get(String id) {
		BusinessChuKuLingLiao businessChuKuLingLiao = super.get(id);
		businessChuKuLingLiao.setBusinessChuKuLingLiaoMxList(businessChuKuLingLiaoMxMapper.findList(new BusinessChuKuLingLiaoMx(businessChuKuLingLiao)));
		return businessChuKuLingLiao;
	}
	
	public List<BusinessChuKuLingLiao> findList(BusinessChuKuLingLiao businessChuKuLingLiao) {
		return super.findList(businessChuKuLingLiao);
	}
	
	public Page<BusinessChuKuLingLiao> findPage(Page<BusinessChuKuLingLiao> page, BusinessChuKuLingLiao businessChuKuLingLiao) {
		return super.findPage(page, businessChuKuLingLiao);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessChuKuLingLiao businessChuKuLingLiao) {
		super.save(businessChuKuLingLiao);
		for (BusinessChuKuLingLiaoMx businessChuKuLingLiaoMx : businessChuKuLingLiao.getBusinessChuKuLingLiaoMxList()){
			if (businessChuKuLingLiaoMx.getId() == null){
				continue;
			}
			if (BusinessChuKuLingLiaoMx.DEL_FLAG_NORMAL.equals(businessChuKuLingLiaoMx.getDelFlag())){
				if (StringUtils.isBlank(businessChuKuLingLiaoMx.getId())){
					businessChuKuLingLiaoMx.setP(businessChuKuLingLiao);
					businessChuKuLingLiaoMx.preInsert();
					businessChuKuLingLiaoMxMapper.insert(businessChuKuLingLiaoMx);
				}else{
					businessChuKuLingLiaoMx.preUpdate();
					businessChuKuLingLiaoMxMapper.update(businessChuKuLingLiaoMx);
				}
			}else{
				businessChuKuLingLiaoMxMapper.delete(businessChuKuLingLiaoMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessChuKuLingLiao businessChuKuLingLiao) {
		super.delete(businessChuKuLingLiao);
		businessChuKuLingLiaoMxMapper.delete(new BusinessChuKuLingLiaoMx(businessChuKuLingLiao));
	}
	@Transactional(readOnly = false)
	public void lingliao(String bgid,String bgcode,String sccode,String scline,String plancode,String planid,
						 String cinvcode,String cinvname,String cinvstd,String unit,Double num,
						 String ckid,String remarks,String userid, String mxJson){
		BusinessChuKuLingLiao lingLiao = new BusinessChuKuLingLiao();
		lingLiao.setPlancode(plancode).setPlanid(planid).setBgcode(bgcode);
		lingLiao.setBgid(bgid);lingLiao.setSccode(sccode);lingLiao.setSclinecode(scline).setCinvcode(cinvcode);
		lingLiao.setCinvname(cinvname);lingLiao.setCinvstd(cinvstd);lingLiao.setNum(num);lingLiao.setUnit(unit);
		lingLiao.setCk(new BaseCangKu(ckid));lingLiao.setRemarks(remarks);
		lingLiao.setCode("LLD"+ DateUtils.getDate("yyyyMMddHHmmss"));
		lingLiao.preInsert();
		lingLiao.setCreateBy(new User(userid));
		mapper.insert(lingLiao);
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray list = json.getJSONArray("list");
		list.forEach(d->{
			JSONObject j = JSONObject.fromObject(d);
			BusinessChuKuLingLiaoMx mx = new BusinessChuKuLingLiaoMx();
			mx.setNo(j.optInt("no",0)).setCinvcode(j.getString("cinvcode"));
			mx.setCinvname(j.getString("cinvname"));mx.setJhbomid(j.getString("bomid"));
			mx.setUnit(j.getString("unit"));
			mx.setScbomid(j.getString("scbomid"));
			mx.setCknum(j.getDouble("num"));
			mx.setHuowei(new BaseHuoWei(j.getString("hwid")));
			mx.setCinvstd(j.getString("cinvstd"));
			mx.setP(lingLiao);
			mx.setSych("0");
			mx.preInsert();
			businessChuKuLingLiaoMxMapper.insert(mx);
		});
	}
}