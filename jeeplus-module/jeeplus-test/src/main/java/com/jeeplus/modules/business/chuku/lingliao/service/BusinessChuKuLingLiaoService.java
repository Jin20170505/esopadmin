/**
 * 
 */
package com.jeeplus.modules.business.chuku.lingliao.service;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
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
	public synchronized void save(BusinessChuKuLingLiao businessChuKuLingLiao) {
		if(StringUtils.isEmpty(businessChuKuLingLiao.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessChuKuLingLiao.setCode(code);
		}
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
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "LLD" +ymd + "00001";
		}else {
			code = maxcode.substring(0,11);
			int c =  Integer.valueOf(maxcode.substring(11));
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
	public void delete(BusinessChuKuLingLiao businessChuKuLingLiao) {
		super.delete(businessChuKuLingLiao);
		businessChuKuLingLiaoMxMapper.delete(new BusinessChuKuLingLiaoMx(businessChuKuLingLiao));
	}
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
	public void lingliao(String bgid,String bgcode,String sccode,String scline,String plancode,String planid,
						 String cinvcode,String cinvname,String cinvstd,String unit,Double num,
						 String ckid,String remarks,String userid, String mxJson){
		BusinessChuKuLingLiao lingLiao = new BusinessChuKuLingLiao();
		lingLiao.setPlancode(plancode).setPlanid(planid).setBgcode(bgcode);
		lingLiao.setBgid(bgid);lingLiao.setSccode(sccode);lingLiao.setSclinecode(scline).setCinvcode(cinvcode);
		lingLiao.setCinvname(cinvname);lingLiao.setCinvstd(cinvstd);lingLiao.setNum(num);lingLiao.setUnit(unit);
		lingLiao.setCk(new BaseCangKu(ckid));lingLiao.setRemarks(remarks);
		lingLiao.setCreateBy(new User(userid));
		List<BusinessChuKuLingLiaoMx> mxes = Lists.newArrayList();
		JSONObject json = JSONObject.fromObject(mxJson);
		JSONArray list = json.getJSONArray("list");
		list.forEach(d->{
			JSONObject j = JSONObject.fromObject(d);
			BusinessChuKuLingLiaoMx mx = new BusinessChuKuLingLiaoMx();
			mx.setNo(j.optInt("no",0)).setCinvcode(j.getString("cinvcode"));
			mx.setCinvname(j.getString("cinvname"));mx.setJhbomid(j.getString("bomid"));
			mx.setUnit(j.getString("unit"));
			mx.setGdnum(num);
			mx.setScbomid(j.getString("scbomid"));
			mx.setCknum(j.getDouble("num"));
			mx.setHuowei(new BaseHuoWei(j.getString("hwid")));
			mx.setCinvstd(j.getString("cinvstd"));
			mx.setP(lingLiao);
			mx.setSych("0");
			mxes.add(mx);
			mx.setId("");mx.setDelFlag("0");
			lingLiao.getBusinessChuKuLingLiaoMxList().add(mx);
		});
		save(lingLiao);
		try {
			String ckcdoe = cangKuMapper.getCodeById(ckid);
			//TODO 向U8抛数据 （材料出库）
			YT_Rd11 rd11 = new YT_Rd11();
			rd11.setcBusType("领料");
			rd11.setcSource("生产订单");
			rd11.setcWhCode(ckcdoe);
			rd11.setcRdcode("21");
			rd11.setdDate(DateUtils.getDate());
			rd11.setcCode(lingLiao.getCode());
			rd11.setcMemo(remarks);
			User user= UserUtils.get(userid);
			rd11.setcMaker(user.getName());
			rd11.setcDepCode(user.getOffice().getCode());
			List<YT_Rds11> rd11s = new ArrayList<>();
			mxes.forEach(d->{
				YT_Rds11 r = new YT_Rds11();
				r.setcInvCode(d.getCinvcode());
				r.setiQuantity(d.getCknum()+"");
				r.setCmocode(sccode);
				r.setImoseq(scline);
				r.setInvcode(cinvcode);
				r.setIopseq(d.getNo()+"");
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

	public void u8in(String rid) throws Exception {
		BusinessChuKuLingLiao lingLiao = get(rid);
		String ckcdoe = cangKuMapper.getCodeById(lingLiao.getCk().getId());
		YT_Rd11 rd11 = new YT_Rd11();
		rd11.setcBusType("领料");
		rd11.setcSource("生产订单");
		rd11.setcWhCode(ckcdoe);
		rd11.setcRdcode("21");
		rd11.setdDate(DateUtils.getDate());
		rd11.setcCode(lingLiao.getCode());
		rd11.setcMemo(lingLiao.getRemarks());
		User user= UserUtils.get(lingLiao.getCreateBy().getId());
		rd11.setcMaker(user.getName());
		rd11.setcDepCode(user.getOffice().getCode());
		List<YT_Rds11> rd11s = new ArrayList<>();
		lingLiao.getBusinessChuKuLingLiaoMxList().forEach(d->{
			YT_Rds11 r = new YT_Rds11();
			r.setcInvCode(d.getCinvcode());
			r.setiQuantity(d.getCknum()+"");
			r.setCmocode(lingLiao.getSccode());
			r.setImoseq(lingLiao.getSclinecode());
			r.setInvcode(lingLiao.getCinvcode());
			r.setIopseq(d.getNo()+"");
			rd11s.add(r);
		});
		rd11.setRd11s(rd11s);
		U8WebServiceResult rs = U8Post.Rd11Post(rd11, U8Url.URL);
		if("1".equals(rs.getCount())){
			throw new RuntimeException(rs.getMessage());
		}
	}
}