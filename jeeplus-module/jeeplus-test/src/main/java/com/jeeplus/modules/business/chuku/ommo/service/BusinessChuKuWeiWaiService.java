/**
 * 
 */
package com.jeeplus.modules.business.chuku.ommo.service;

import java.util.ArrayList;
import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.number.RandomUtil;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeeplus.u8.webservice.U8Post;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.YT_Rd11;
import org.jeeplus.u8.webservice.YT_Rds11;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWai;
import com.jeeplus.modules.business.chuku.ommo.mapper.BusinessChuKuWeiWaiMapper;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWaiMx;
import com.jeeplus.modules.business.chuku.ommo.mapper.BusinessChuKuWeiWaiMxMapper;

/**
 * 委外发料Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessChuKuWeiWaiService extends CrudService<BusinessChuKuWeiWaiMapper, BusinessChuKuWeiWai> {

	@Autowired
	private BusinessChuKuWeiWaiMxMapper businessChuKuWeiWaiMxMapper;
	
	public BusinessChuKuWeiWai get(String id) {
		BusinessChuKuWeiWai businessChuKuWeiWai = super.get(id);
		businessChuKuWeiWai.setBusinessChuKuWeiWaiMxList(businessChuKuWeiWaiMxMapper.findList(new BusinessChuKuWeiWaiMx(businessChuKuWeiWai)));
		return businessChuKuWeiWai;
	}
	
	public List<BusinessChuKuWeiWai> findList(BusinessChuKuWeiWai businessChuKuWeiWai) {
		return super.findList(businessChuKuWeiWai);
	}
	
	public Page<BusinessChuKuWeiWai> findPage(Page<BusinessChuKuWeiWai> page, BusinessChuKuWeiWai businessChuKuWeiWai) {
		return super.findPage(page, businessChuKuWeiWai);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessChuKuWeiWai businessChuKuWeiWai) {
		super.save(businessChuKuWeiWai);
		for (BusinessChuKuWeiWaiMx businessChuKuWeiWaiMx : businessChuKuWeiWai.getBusinessChuKuWeiWaiMxList()){
			if (businessChuKuWeiWaiMx.getId() == null){
				continue;
			}
			if (BusinessChuKuWeiWaiMx.DEL_FLAG_NORMAL.equals(businessChuKuWeiWaiMx.getDelFlag())){
				if (StringUtils.isBlank(businessChuKuWeiWaiMx.getId())){
					businessChuKuWeiWaiMx.setP(businessChuKuWeiWai);
					businessChuKuWeiWaiMx.preInsert();
					businessChuKuWeiWaiMxMapper.insert(businessChuKuWeiWaiMx);
				}else{
					businessChuKuWeiWaiMx.preUpdate();
					businessChuKuWeiWaiMxMapper.update(businessChuKuWeiWaiMx);
				}
			}else{
				businessChuKuWeiWaiMxMapper.delete(businessChuKuWeiWaiMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessChuKuWeiWai businessChuKuWeiWai) {
		super.delete(businessChuKuWeiWai);
		businessChuKuWeiWaiMxMapper.delete(new BusinessChuKuWeiWaiMx(businessChuKuWeiWai));
	}


	public boolean hasByWwHid(String wwhid){
		Integer i = mapper.hasByWwHid(wwhid);
		return i!=null && i == 1;
	}
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
	public void weiwaichuku(String wwid, String wwhid,String ckid, BussinessOmMoDetailOnly info, String userid, String mxJson){
		User user = UserUtils.get(userid);
		BusinessChuKuWeiWai businessChuKuWeiWai = new BusinessChuKuWeiWai();
		String code = "";
		synchronized (this){
			code = "WWFL"+ DateUtils.getDate("yyyyMMddHHmmss")+ RandomUtil.nextInt(100,999);
		}
		businessChuKuWeiWai.setMoid(wwid);
		businessChuKuWeiWai.setMohid(wwhid);
		businessChuKuWeiWai.setArrivedate(info.getArrivedate());
		businessChuKuWeiWai.setStartdate(info.getStartdate());
		businessChuKuWeiWai.setMocode(info.getCode());
		businessChuKuWeiWai.setMono(info.getNo());
		businessChuKuWeiWai.setCreateBy(user);
		businessChuKuWeiWai.setCinvcode(info.getCinvcode());
		businessChuKuWeiWai.setCinvname(info.getCinvname());
		businessChuKuWeiWai.setCinvstd(info.getCinvstd());
		businessChuKuWeiWai.setCode(code);
		businessChuKuWeiWai.setNum(info.getNum());
		businessChuKuWeiWai.setUnit(info.getUnit());
		businessChuKuWeiWai.setVendor(new BaseVendor(info.getVendorid()));
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray array = json.getJSONArray("list");
		array.forEach(a->{
			JSONObject j = JSONObject.fromObject(a);
			BusinessChuKuWeiWaiMx mx = new BusinessChuKuWeiWaiMx();
			mx.setBomid(j.optString("id"));
			mx.setId("");mx.setDelFlag("0");
			mx.setNo(j.optInt("no"));
			mx.setCinvcode(j.optString("cinvcode"));
			mx.setCinvname(j.optString("cinvname"));
			mx.setCinvstd(j.optString("cinvstd"));
			mx.setNum(j.optDouble("num",0.0));
			mx.setUnit(j.optString("unit"));
			mx.setHw(j.optString("hwid"));
			mx.setBatchno(j.optString("batchno"));
			mx.setMohid(wwhid);mx.setMoid(wwid);
			businessChuKuWeiWai.getBusinessChuKuWeiWaiMxList().add(mx);
		});
		save(businessChuKuWeiWai);
		String ck = cangKuMapper.getCodeById(ckid);
		try {
			YT_Rd11 rd11 = new YT_Rd11();
			rd11.setcBusType("委外发料");
			rd11.setcSource("委外订单");
			rd11.setcWhCode(ck);
			rd11.setcRdcode("21");
			rd11.setdDate(DateUtils.getDate());
			rd11.setcCode(businessChuKuWeiWai.getCode());
			rd11.setcMemo("");
			rd11.setcMaker(user.getName());
			rd11.setcDepCode(user.getOffice().getCode());
			List<YT_Rds11> rd11s = new ArrayList<>();
			businessChuKuWeiWai.getBusinessChuKuWeiWaiMxList().forEach(d->{
				YT_Rds11 r = new YT_Rds11();
				r.setcInvCode(d.getCinvcode());
				r.setiQuantity(d.getNum()+"");
				r.setCmocode(info.getCode());
				r.setImoseq(info.getNo()+"");
				r.setInvcode(d.getCinvcode());
				r.setIopseq(d.getBomid()); // 委外订单子件ID
				rd11s.add(r);
			});
			rd11.setRd11s(rd11s);
			U8WebServiceResult rs = U8Post.Rd11Post(rd11, U8Url.URL);
			if("1".equals(rs.getCount())){
				throw new RuntimeException(rs.getMessage());
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
		}
	}
}