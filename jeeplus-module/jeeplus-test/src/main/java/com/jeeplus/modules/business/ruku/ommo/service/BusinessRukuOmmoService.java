/**
 * 
 */
package com.jeeplus.modules.business.ruku.ommo.service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.number.RandomUtil;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalVouch;
import com.jeeplus.modules.business.ommoarrivalvouch.mapper.BusinessOmmoArrivalVouchMapper;
import com.jeeplus.modules.business.ruku.ommo.entity.BusinessRukuOmmo;
import com.jeeplus.modules.business.ruku.ommo.entity.BusinessRukuOmmoMx;
import com.jeeplus.modules.business.ruku.ommo.mapper.BusinessRukuOmmoMapper;
import com.jeeplus.modules.business.ruku.ommo.mapper.BusinessRukuOmmoMxMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeeplus.u8.webservice.U8Post;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.YT_Rd01;
import org.jeeplus.u8.webservice.YT_Rds01;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 委外入库Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessRukuOmmoService extends CrudService<BusinessRukuOmmoMapper, BusinessRukuOmmo> {

	@Autowired
	private BusinessRukuOmmoMxMapper businessRukuOmmoMxMapper;
	
	public BusinessRukuOmmo get(String id) {
		BusinessRukuOmmo businessRukuOmmo = super.get(id);
		businessRukuOmmo.setBusinessRukuOmmoMxList(businessRukuOmmoMxMapper.findList(new BusinessRukuOmmoMx(businessRukuOmmo)));
		return businessRukuOmmo;
	}
	
	public List<BusinessRukuOmmo> findList(BusinessRukuOmmo businessRukuOmmo) {
		return super.findList(businessRukuOmmo);
	}
	
	public Page<BusinessRukuOmmo> findPage(Page<BusinessRukuOmmo> page, BusinessRukuOmmo businessRukuOmmo) {
		return super.findPage(page, businessRukuOmmo);
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "WWRK" +ymd + "00001";
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
	public void save(BusinessRukuOmmo businessRukuOmmo) {
		if(StringUtils.isEmpty(businessRukuOmmo.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessRukuOmmo.setCode(code);
		}
		super.save(businessRukuOmmo);
		for (BusinessRukuOmmoMx businessRukuOmmoMx : businessRukuOmmo.getBusinessRukuOmmoMxList()){
			if (businessRukuOmmoMx.getId() == null){
				continue;
			}
			if (BusinessRukuOmmoMx.DEL_FLAG_NORMAL.equals(businessRukuOmmoMx.getDelFlag())){
				if (StringUtils.isBlank(businessRukuOmmoMx.getId())){
					businessRukuOmmoMx.setP(businessRukuOmmo);
					businessRukuOmmoMx.preInsert();
					businessRukuOmmoMxMapper.insert(businessRukuOmmoMx);
				}else{
					businessRukuOmmoMx.preUpdate();
					businessRukuOmmoMxMapper.update(businessRukuOmmoMx);
				}
			}else{
				businessRukuOmmoMxMapper.delete(businessRukuOmmoMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessRukuOmmo businessRukuOmmo) {
		super.delete(businessRukuOmmo);
		businessRukuOmmoMxMapper.delete(new BusinessRukuOmmoMx(businessRukuOmmo));
	}


	public Double getRukuNum(String wdhid) {
		return businessRukuOmmoMxMapper.sumRukuNumByWwhid(wdhid);
	}
	@Autowired
	private BusinessOmmoArrivalVouchMapper arrivalVouchMapper;
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
	public void doRuKu(String wdid, String wdcode, String ckid, String userid, String mxJson) {
		BusinessOmmoArrivalVouch  vouch = arrivalVouchMapper.get(wdid);
		BusinessRukuOmmo main = new BusinessRukuOmmo();
		User user = UserUtils.get(userid);
		main.setRdate(DateUtils.getDate());
		main.setCreateBy(user);
		main.setWdcode(vouch.getCode());
		main.setDept(vouch.getDept());
		main.setVendor(vouch.getVendor());
		main.setDdate(DateUtils.formatDate(vouch.getDdate()));
		main.setCk(new BaseCangKu(ckid));
		main.setU8code(main.getCode());
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray array = json.getJSONArray("list");
		array.forEach(d->{
			JSONObject j = JSONObject.fromObject(d);
			BusinessRukuOmmoMx mx  = new BusinessRukuOmmoMx();
			mx.setDelFlag("0");
			mx.setId("");
			mx.setCinvcode(j.optString("cinvcode"));
			mx.setCinvname(j.optString("cinvname"));
			mx.setCinvstd(j.optString("cinvstd"));
			mx.setBatchno(j.optString("batchno"));
			mx.setRukunum( j.optDouble("num"));
			mx.setHw(j.optString("hwid"));
			mx.setNo(j.optInt("no"));
			mx.setScdate(j.optString("scdate"));
			mx.setUnit(j.optString("unit"));
			mx.setWdhid(j.getString("id"));mx.setWdid(wdid);
			main.getBusinessRukuOmmoMxList().add(mx);
		});
		save(main);
		String ckcode = cangKuMapper.getCodeById(ckid);
		YT_Rd01 rd01 = new YT_Rd01();
		rd01.setCode(main.getCode());
		rd01.setcBusType("委外订单");
		rd01.setcSource("来料检验单");
		rd01.setcWhCode(ckcode);
		System.out.println("仓库code："+ckcode);
		rd01.setdDate(DateUtils.formatDate(vouch.getDdate(),"yyyy-MM-dd"));
		rd01.setcRdCode("11");
		rd01.setcDepCode(user.getOffice().getCode());
		rd01.setcPersonCode("");
		rd01.setcPTCode("01");
		rd01.setcVenCode(vouch.getVendor().getCode());
		rd01.setcMaker("demo");
		rd01.setDnmaketim(DateUtils.getDate());
		List<YT_Rds01> rd01s = new ArrayList<>();
		main.getBusinessRukuOmmoMxList().forEach(d->{
			YT_Rds01 rds01 = new YT_Rds01();
			rds01.setIrowno(d.getNo()+"");
			rds01.setcInvCode(d.getCinvcode());
			rds01.setiArrsId(d.getWdhid());
			rds01.setcBatch(d.getBatchno());
			rds01.setcPosition(d.getHw());
			System.out.println("货位："+d.getHw());
			rds01.setdMadeDate(d.getScdate());
			rds01.setcARVCode(wdcode);
			rds01.setiQuantity(d.getRukunum()+"");
			rd01s.add(rds01);
		});
		rd01.setRd01s(rd01s);
		try{
			U8WebServiceResult rs = U8Post.Rd01Post(rd01, U8Url.URL);
			if("1".equals(rs.getCount())){
				throw new RuntimeException(rs.getMessage());
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
		}
	}

	public void u8in(String rid) throws Exception {
		BusinessRukuOmmo main = get(rid);
		User user = UserUtils.get(main.getCreateBy().getId());
		YT_Rd01 rd01 = new YT_Rd01();
		rd01.setCode(main.getCode());
		rd01.setcBusType("委外订单");
		rd01.setcSource("来料检验单");
		rd01.setcWhCode(main.getCk().getCode());
		rd01.setdDate(main.getDdate());
		rd01.setcRdCode("11");
		rd01.setcDepCode(user.getOffice().getCode());
		rd01.setcPersonCode("");
		rd01.setcPTCode("01");
		rd01.setcVenCode(main.getVendor().getCode());
		rd01.setcMaker("demo");
		rd01.setDnmaketim(DateUtils.getDate());
		List<YT_Rds01> rd01s = new ArrayList<>();
		main.getBusinessRukuOmmoMxList().forEach(d->{
			YT_Rds01 rds01 = new YT_Rds01();
			rds01.setIrowno(d.getNo()+"");
			rds01.setcInvCode(d.getCinvcode());
			rds01.setiArrsId(d.getWdhid());
			rds01.setcBatch(d.getBatchno());
			rds01.setcPosition(d.getHw());
			rds01.setdMadeDate(d.getScdate());
			rds01.setcARVCode(main.getWdcode());
			rds01.setiQuantity(d.getRukunum()+"");
			rd01s.add(rds01);
		});
		rd01.setRd01s(rd01s);
		U8WebServiceResult rs = U8Post.Rd01Post(rd01, U8Url.URL);
		if("1".equals(rs.getCount())){
			throw new RuntimeException(rs.getMessage());
		}
	}
}