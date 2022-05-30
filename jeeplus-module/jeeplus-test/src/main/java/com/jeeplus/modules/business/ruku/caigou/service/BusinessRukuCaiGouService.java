/**
 * 
 */
package com.jeeplus.modules.business.ruku.caigou.service;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.arrivalvouch.mapper.BusinessArrivalVouchMapper;
import com.jeeplus.modules.business.arrivalvouch.mapper.BusinessArrivalVouchMxMapper;
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaiGou;
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaigouMx;
import com.jeeplus.modules.business.ruku.caigou.mapper.BusinessRukuCaiGouMapper;
import com.jeeplus.modules.business.ruku.caigou.mapper.BusinessRukuCaigouMxMapper;
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
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
	public void doRuKu(String cgid,String cgcode,String ckid,String hwid,String userid,String mxJson){
		BusinessRukuCaiGou businessRukuCaiGou = new BusinessRukuCaiGou();
		User user = UserUtils.get(userid);
		businessRukuCaiGou.setCreateBy(user);
		BusinessArrivalVouch vouch = arrivalVouchMapper.get(cgid);
		businessRukuCaiGou.setArrivalcode(vouch.getCode());
		businessRukuCaiGou.setArrivaldate(DateUtils.formatDate(vouch.getArriveDate()));
		businessRukuCaiGou.setHw(new BaseHuoWei(hwid));
		businessRukuCaiGou.setCk(new BaseCangKu(ckid));
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
		String ckcode = cangKuMapper.getCodeById(ckid);
		save(businessRukuCaiGou);
		YT_Rd01 rd01 = new YT_Rd01();
		rd01.setCode(businessRukuCaiGou.getCode());
		rd01.setcBusType("普通采购");
		rd01.setcSource("来料检验单");
		rd01.setcWhCode(ckcode);

		rd01.setdDate(DateUtils.formatDate(vouch.getArriveDate(),"yyyy-MM-dd"));
		rd01.setcRdCode("11");
		rd01.setcDepCode(user.getOffice().getCode());
		rd01.setcPersonCode("");
		rd01.setcPTCode("01");
		rd01.setcVenCode(vouch.getVendor().getCode());
		System.out.println(vouch.getVendor().getCode());
		rd01.setcMaker("demo");
		rd01.setDnmaketim(DateUtils.getDate());
		List<YT_Rds01> rd01s = new ArrayList<>();
		businessRukuCaiGou.getBusinessRukuCaigouMxList().forEach(d->{
			YT_Rds01 rds01 = new YT_Rds01();
			rds01.setIrowno(d.getNo()+"");
			rds01.setcInvCode(d.getCinvcode());
			rds01.setiArrsId(d.getCghid());
			rds01.setcBatch(d.getBatchno());
			rds01.setcPosition(hwid);
			rds01.setdMadeDate(d.getScdate());
			rds01.setcARVCode(cgcode);
			rds01.setiQuantity(d.getNum()+"");
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


	public Double getRukuNum(String cghid){
		return businessRukuCaigouMxMapper.sumRukuNumByCghid(cghid);
	}
}