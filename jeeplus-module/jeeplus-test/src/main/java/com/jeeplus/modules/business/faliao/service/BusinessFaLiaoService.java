/**
 * 
 */
package com.jeeplus.modules.business.faliao.service;

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
import com.jeeplus.modules.business.faliao.entity.BusinessFaLiao;
import com.jeeplus.modules.business.faliao.mapper.BusinessFaLiaoMapper;
import com.jeeplus.modules.business.faliao.entity.BusinessFaLiaoMx;
import com.jeeplus.modules.business.faliao.mapper.BusinessFaLiaoMxMapper;

/**
 * 调拨单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessFaLiaoService extends CrudService<BusinessFaLiaoMapper, BusinessFaLiao> {

	@Autowired
	private BusinessFaLiaoMxMapper businessFaLiaoMxMapper;
	
	public BusinessFaLiao get(String id) {
		BusinessFaLiao businessFaLiao = super.get(id);
		businessFaLiao.setBusinessFaLiaoMxList(businessFaLiaoMxMapper.findList(new BusinessFaLiaoMx(businessFaLiao)));
		return businessFaLiao;
	}
	
	public List<BusinessFaLiao> findList(BusinessFaLiao businessFaLiao) {
		return super.findList(businessFaLiao);
	}
	
	public Page<BusinessFaLiao> findPage(Page<BusinessFaLiao> page, BusinessFaLiao businessFaLiao) {
		return super.findPage(page, businessFaLiao);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessFaLiao businessFaLiao) {
		super.save(businessFaLiao);
		for (BusinessFaLiaoMx businessFaLiaoMx : businessFaLiao.getBusinessFaLiaoMxList()){
			if (businessFaLiaoMx.getId() == null){
				continue;
			}
			if (BusinessFaLiaoMx.DEL_FLAG_NORMAL.equals(businessFaLiaoMx.getDelFlag())){
				if (StringUtils.isBlank(businessFaLiaoMx.getId())){
					businessFaLiaoMx.setPid(businessFaLiao);
					businessFaLiaoMx.preInsert();
					businessFaLiaoMxMapper.insert(businessFaLiaoMx);
				}else{
					businessFaLiaoMx.preUpdate();
					businessFaLiaoMxMapper.update(businessFaLiaoMx);
				}
			}else{
				businessFaLiaoMxMapper.delete(businessFaLiaoMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessFaLiao businessFaLiao) {
		super.delete(businessFaLiao);
		businessFaLiaoMxMapper.delete(new BusinessFaLiaoMx(businessFaLiao));
	}
	@Transactional(readOnly = false)
	public void faliao(String userid,String fromck,String tock,String mxJson){
		BusinessFaLiao businessFaLiao = new BusinessFaLiao();
		businessFaLiao.setCode("FLD"+ DateUtils.getDate("yyyyMMddHHmmss"));
		businessFaLiao.preInsert();
		businessFaLiao.setCreateBy(new User(userid));
		businessFaLiao.setFromck(new BaseCangKu(fromck));
		businessFaLiao.setTock(new BaseCangKu(tock));
		mapper.insert(businessFaLiao);
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray jsonArray = json.getJSONArray("list");
		jsonArray.forEach(j->{
			JSONObject o = JSONObject.fromObject(j);
			BusinessFaLiaoMx mx = new BusinessFaLiaoMx();
			mx.setPid(businessFaLiao);
			mx.setNum(o.optDouble("num"));
			mx.setNo(o.optInt("no"));
			mx.setCinvcode(o.optString("cinvcode"));
			mx.setCinvname(o.optString("cinvname"));
			mx.setCinvstd(o.optString("cinvstd"));
			mx.setUnit(o.optString("unit"));
			mx.setBatchno(o.optString("batchno"));
			mx.setScdate(o.optString("scdate"));
			mx.setHuowei(new BaseHuoWei(o.optString("hwid")));
			mx.preInsert();
			businessFaLiaoMxMapper.insert(mx);
		});
	}
}