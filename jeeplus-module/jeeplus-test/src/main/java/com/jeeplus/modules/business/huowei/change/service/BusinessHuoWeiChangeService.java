/**
 * 
 */
package com.jeeplus.modules.business.huowei.change.service;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWaiMx;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import org.jeeplus.u8.webservice.U8Post;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.jeeplus.u8.webservice.huoweitiaozheng.U8HuoWeiTiaoZhengService;
import org.jeeplus.u8.webservice.huoweitiaozheng.entity.U8WebHuoWeiTiaoZhengBean;
import org.jeeplus.u8.webservice.huoweitiaozheng.entity.U8WebHuoWeiTiaoZhengMxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.huowei.change.entity.BusinessHuoWeiChange;
import com.jeeplus.modules.business.huowei.change.mapper.BusinessHuoWeiChangeMapper;
import com.jeeplus.modules.business.huowei.change.entity.BusinessHuoWeiChangeMx;
import com.jeeplus.modules.business.huowei.change.mapper.BusinessHuoWeiChangeMxMapper;

/**
 * 货位调整Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessHuoWeiChangeService extends CrudService<BusinessHuoWeiChangeMapper, BusinessHuoWeiChange> {

	@Autowired
	private BusinessHuoWeiChangeMxMapper businessHuoWeiChangeMxMapper;
	
	public BusinessHuoWeiChange get(String id) {
		BusinessHuoWeiChange businessHuoWeiChange = super.get(id);
		businessHuoWeiChange.setBusinessHuoWeiChangeMxList(businessHuoWeiChangeMxMapper.findList(new BusinessHuoWeiChangeMx(businessHuoWeiChange)));
		return businessHuoWeiChange;
	}
	
	public List<BusinessHuoWeiChange> findList(BusinessHuoWeiChange businessHuoWeiChange) {
		return super.findList(businessHuoWeiChange);
	}
	
	public Page<BusinessHuoWeiChange> findPage(Page<BusinessHuoWeiChange> page, BusinessHuoWeiChange businessHuoWeiChange) {
		return super.findPage(page, businessHuoWeiChange);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessHuoWeiChange businessHuoWeiChange) {
		if(StringUtils.isEmpty(businessHuoWeiChange.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessHuoWeiChange.setCode(code);
		}
		super.save(businessHuoWeiChange);
		for (BusinessHuoWeiChangeMx businessHuoWeiChangeMx : businessHuoWeiChange.getBusinessHuoWeiChangeMxList()){
			if (businessHuoWeiChangeMx.getId() == null){
				continue;
			}
			if (BusinessHuoWeiChangeMx.DEL_FLAG_NORMAL.equals(businessHuoWeiChangeMx.getDelFlag())){
				if (StringUtils.isBlank(businessHuoWeiChangeMx.getId())){
					businessHuoWeiChangeMx.setP(businessHuoWeiChange);
					businessHuoWeiChangeMx.preInsert();
					businessHuoWeiChangeMxMapper.insert(businessHuoWeiChangeMx);
				}else{
					businessHuoWeiChangeMx.preUpdate();
					businessHuoWeiChangeMxMapper.update(businessHuoWeiChangeMx);
				}
			}else{
				businessHuoWeiChangeMxMapper.delete(businessHuoWeiChangeMx);
			}
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "HWTZ" +ymd + "00001";
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
	public void delete(BusinessHuoWeiChange businessHuoWeiChange) {
		super.delete(businessHuoWeiChange);
		businessHuoWeiChangeMxMapper.delete(new BusinessHuoWeiChangeMx(businessHuoWeiChange));
	}
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
	public void change(String ckid, String userid, String mxJson) {
		User user = UserUtils.get(userid);
		BusinessHuoWeiChange change = new BusinessHuoWeiChange();
		change.setCk(new BaseCangKu(ckid));
		change.setCmaker(user.getName());
		change.setCreateBy(user);
		change.setDdate(new Date());
		Object obj = JSONObject.parse(mxJson);
		net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(obj);
		JSONArray array = json.getJSONArray("list");
		array.forEach(a->{
			net.sf.json.JSONObject j = net.sf.json.JSONObject.fromObject(a);
			BusinessHuoWeiChangeMx mx = new BusinessHuoWeiChangeMx();
			mx.setId("");mx.setDelFlag("0");
			mx.setNo(j.optInt("no"));
			mx.setCinvcode(j.optString("cinvcode").trim());
			mx.setCinvname(j.optString("cinvname"));
			mx.setCinvstd(j.optString("cinvstd"));
			mx.setNum(j.optDouble("num",0.0));
			mx.setUnit(j.optString("unit"));
			mx.setHwafter(j.optString("ahw"));
			mx.setHwbefore(j.optString("bhw"));
			mx.setBatchno(j.optString("batchno").trim());
			mx.setScdate(j.optString("scdate"));
			change.getBusinessHuoWeiChangeMxList().add(mx);
		});
		save(change);
		String ckcode = cangKuMapper.getCodeById(ckid);
		try {
			U8WebHuoWeiTiaoZhengBean huoWeiTiaoZhengBean = new U8WebHuoWeiTiaoZhengBean();
			huoWeiTiaoZhengBean.setcMaker(change.getCmaker()).setcWhCode(ckcode).setcVouchCode(change.getCode())
					.setdDate(DateUtils.formatDate(change.getDdate()));
			change.getBusinessHuoWeiChangeMxList().forEach(d->{
				U8WebHuoWeiTiaoZhengMxBean mx = new U8WebHuoWeiTiaoZhengMxBean();
				mx.setIrowno(d.getNo().toString()).setiQuantity(d.getNum().toString()).setCinvcode(d.getCinvcode()).setcBatch(d.getBatchno());
				mx.setcBPosCode(d.getHwbefore()).setcAPosCode(d.getHwafter());
				huoWeiTiaoZhengBean.getDetails().add(mx);
			});
			U8WebServiceResult rs = U8HuoWeiTiaoZhengService.huoweichange(huoWeiTiaoZhengBean);
			if("1".equals(rs.getCount())){
				throw new RuntimeException(rs.getMessage());
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
		}
	}
}