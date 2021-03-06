///**
// *
// */
//package com.jeeplus.modules.business.faliao.service;
//
//import com.google.common.collect.Lists;
//import com.jeeplus.common.utils.DateUtils;
//import com.jeeplus.common.utils.StringUtils;
//import com.jeeplus.core.persistence.Page;
//import com.jeeplus.core.service.CrudService;
//import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
//import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
//import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
//import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;
//import com.jeeplus.modules.business.faliao.entity.BusinessFaLiao;
//import com.jeeplus.modules.business.faliao.entity.BusinessFaLiaoMx;
//import com.jeeplus.modules.business.faliao.mapper.BusinessFaLiaoMapper;
//import com.jeeplus.modules.business.faliao.mapper.BusinessFaLiaoMxMapper;
//import com.jeeplus.modules.sys.entity.User;
//import com.jeeplus.modules.sys.utils.UserUtils;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.jeeplus.u8.webservice.U8Post;
//import org.jeeplus.u8.webservice.U8Url;
//import org.jeeplus.u8.webservice.YT_Tran;
//import org.jeeplus.u8.webservice.YT_Trans;
//import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 调拨单Service
// * @author Jin
// */
//@Service
//@Transactional(readOnly = true)
//public class BusinessFaLiaoServiceTemp extends CrudService<BusinessFaLiaoMapper, BusinessFaLiao> {
//
//	@Autowired
//	private BusinessFaLiaoMxMapper businessFaLiaoMxMapper;
//
//	public BusinessFaLiao get(String id) {
//		BusinessFaLiao businessFaLiao = super.get(id);
//		businessFaLiao.setBusinessFaLiaoMxList(businessFaLiaoMxMapper.findList(new BusinessFaLiaoMx(businessFaLiao)));
//		return businessFaLiao;
//	}
//
//	public List<BusinessFaLiao> findList(BusinessFaLiao businessFaLiao) {
//		return super.findList(businessFaLiao);
//	}
//
//	public Page<BusinessFaLiao> findPage(Page<BusinessFaLiao> page, BusinessFaLiao businessFaLiao) {
//		return super.findPage(page, businessFaLiao);
//	}
//
//	@Transactional(readOnly = false)
//	public synchronized void save(BusinessFaLiao businessFaLiao) {
//		if(StringUtils.isEmpty(businessFaLiao.getCode())){
//			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
//			businessFaLiao.setCode(code);
//		}
//		super.save(businessFaLiao);
//		for (BusinessFaLiaoMx businessFaLiaoMx : businessFaLiao.getBusinessFaLiaoMxList()){
//			if (businessFaLiaoMx.getId() == null){
//				continue;
//			}
//			if (BusinessFaLiaoMx.DEL_FLAG_NORMAL.equals(businessFaLiaoMx.getDelFlag())){
//				if (StringUtils.isBlank(businessFaLiaoMx.getId())){
//					businessFaLiaoMx.setPid(businessFaLiao);
//					businessFaLiaoMx.preInsert();
//					businessFaLiaoMxMapper.insert(businessFaLiaoMx);
//				}else{
//					businessFaLiaoMx.preUpdate();
//					businessFaLiaoMxMapper.update(businessFaLiaoMx);
//				}
//			}else{
//				businessFaLiaoMxMapper.delete(businessFaLiaoMx);
//			}
//		}
//	}
//	public String getCurrentCode(String ymd){
//		String maxcode  = mapper.getMaxCode(ymd);
//		String code = "";
//		if(StringUtils.isEmpty(maxcode)){
//			code = "FLD" +ymd + "00001";
//		}else {
//			code = maxcode.substring(0,11);
//			int c =  Integer.valueOf(maxcode.substring(11));
//			c = c+1;
//			if(c<10){
//				code = code +"0000"+c;
//			}else if(10<=c && c<100){
//				code = code +"000"+c;
//			}else if(100<=c && c<1000) {
//				code = code +"00"+c;
//			}else if(1000<=c && c<10000){
//				code = code +"0"+c;
//			}else {
//				code = code+c;
//			}
//		}
//		return code;
//	}
//	@Transactional(readOnly = false)
//	public void delete(BusinessFaLiao businessFaLiao) {
//		super.delete(businessFaLiao);
//		businessFaLiaoMxMapper.delete(new BusinessFaLiaoMx(businessFaLiao));
//	}
//	@Autowired
//	private BaseCangKuMapper cangKuMapper;
//	@Autowired
//	private BaseHuoWeiMapper huoWeiMapper;
//
//	@Transactional(readOnly = false)
//	public void faliao(String userid,String fromck,String tock,String mxJson){
//		BusinessFaLiao businessFaLiao = new BusinessFaLiao();
//		User user = UserUtils.get(userid);
//		businessFaLiao.setCreateBy(user);
//		businessFaLiao.setFromck(new BaseCangKu(fromck));
//		businessFaLiao.setTock(new BaseCangKu(tock));
//		JSONObject json = JSONObject.fromObject(mxJson);
//		JSONArray jsonArray = json.getJSONArray("list");
//		List<BusinessFaLiaoMx>  mxes = new ArrayList<>();
//		jsonArray.forEach(j->{
//			JSONObject o = JSONObject.fromObject(j);
//			BusinessFaLiaoMx mx = new BusinessFaLiaoMx();
//			mx.setPid(businessFaLiao);
//			mx.setNum(o.optDouble("num"));
//			mx.setNo(o.optInt("no"));
//			mx.setCinvcode(o.optString("cinvcode"));
//			mx.setCinvname(o.optString("cinvname"));
//			mx.setCinvstd(o.optString("cinvstd"));
//			mx.setUnit(o.optString("unit"));
//			mx.setBatchno(o.optString("batchno"));
//			mx.setScdate(o.optString("scdate"));
//			mx.setHuowei(new BaseHuoWei(o.optString("hwid")));
//			mx.setId("");mx.setDelFlag("0");
//			mxes.add(mx);
//			businessFaLiao.getBusinessFaLiaoMxList().add(mx);
//		});
//		save(businessFaLiao);
//		String ockcdoe = cangKuMapper.getCodeById(fromck);
//		String tckcode = cangKuMapper.getCodeById(tock);
//		String ddate = DateUtils.getDate();
//		// 改为其他出库
////		U8OtherOutCkMain m = new U8OtherOutCkMain();
////		m.setcDepCode(user.getOffice().getCode()).setcWhCode(ockcdoe).setcMaker(user.getName()).setcMemo("调拨单").setcRWhCode(tckcode)
////				.setCrdcode("25").setcCode(businessFaLiao.getCode()).setMesCode(businessFaLiao.getCode()).setdDate(ddate);
////		try{
////			mxes.forEach(d->{
////				U8OtherOutCkMx x = new U8OtherOutCkMx();
////				x.setcBatch(d.getBatchno()).setcPosition(d.getHuowei().getId()).setcInvCode(d.getCinvcode()).
////						setdMadeDate(d.getScdate()).setiQuantity(d.getNum()+"").setiRSRowNO(d.getNo()+"");
////				m.getDetails().add(x);
////			});
////			U8WebServiceResult rs = U8OtherOutCkWebService.outck(m);
////			if("1".equals(rs.getCount())){
////				throw new RuntimeException(rs.getMessage());
////			}
////		}catch (Exception e){
////			e.printStackTrace();
////			throw new RuntimeException("数据传U8出错(出库)，原因："+e.getMessage());
////		}
////		U8OtherInCkMain rm = new U8OtherInCkMain();
////		rm.setcDepCode(user.getOffice().getCode()).setcWhCode(tckcode).setcMaker(user.getName()).setcMemo("调拨单")
////				.setCrdcode("15").setcCode(businessFaLiao.getCode()).setMesCode(businessFaLiao.getCode()).setdDate(ddate);
////		try{
////			mxes.forEach(d->{
////				U8OtherInCkMx x = new U8OtherInCkMx();
////				x.setcBatch(d.getBatchno()).setcPosition("").setcInvCode(d.getCinvcode()).
////						setdMadeDate(d.getScdate()).setiQuantity(d.getNum()+"").setiRSRowNO(d.getNo()+"");
////				rm.getDetails().add(x);
////			});
////			U8WebServiceResult rs = U8OtherInCkWebService.inck(rm);
////			if("1".equals(rs.getCount())){
////				throw new RuntimeException(rs.getMessage());
////			}
////		}catch (Exception e){
////			e.printStackTrace();
////			throw new RuntimeException("数据传U8出错(入库)，原因："+e.getMessage());
////		}
//		try {
//			YT_Tran tr = new YT_Tran();
//			tr.setcTVCode(businessFaLiao.getCode());
//			tr.setdTVDate(DateUtils.getDate());
//			tr.setcOWhCode(ockcdoe);
//			tr.setcIWhCode(tckcode);
//			tr.setcIRdCode("15");
//			tr.setcORdCode("25");
//			List<YT_Trans> trans = Lists.newArrayList();
//			mxes.forEach(d->{
//				YT_Trans t = new YT_Trans();
//				t.setcInvCode(d.getCinvcode());
//				t.setiTVQuantity(d.getNum()+"");
//				t.setIrowno(d.getNo()+"");
//				t.setdMadeDate(d.getScdate());
//				t.setcTVBatch(d.getBatchno());
//				t.setCoutposcode(d.getHuowei().getId());
//				trans.add(t);
//			});
//			tr.setTrans(trans);
//			U8WebServiceResult rs = U8Post.TranPost(tr, U8Url.URL);
//			if("1".equals(rs.getCount())){
//				throw new RuntimeException(rs.getMessage());
//			}
//		}catch (Exception e){
//			e.printStackTrace();
//			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
//		}
//	}
//
//	public void u8in(String rid) throws Exception {
//		BusinessFaLiao businessFaLiao = get(rid);
//		String ockcdoe = cangKuMapper.getCodeById(businessFaLiao.getFromck().getId());
//		String tckcode = cangKuMapper.getCodeById(businessFaLiao.getTock().getId());
//		YT_Tran tr = new YT_Tran();
//		tr.setcTVCode(businessFaLiao.getCode());
//		tr.setdTVDate(DateUtils.getDate());
//		tr.setcOWhCode(ockcdoe);
//		tr.setcIWhCode(tckcode);
//		tr.setcIRdCode("15");
//		tr.setcORdCode("25");
//		List<YT_Trans> trans = Lists.newArrayList();
//		businessFaLiao.getBusinessFaLiaoMxList().forEach(d->{
//			YT_Trans t = new YT_Trans();
//			t.setcInvCode(d.getCinvcode());
//			t.setiTVQuantity(d.getNum()+"");
//			t.setIrowno(d.getNo()+"");
//			t.setdMadeDate(d.getScdate());
//			t.setcTVBatch(d.getBatchno());
//			t.setCoutposcode(d.getHuowei().getId());
//			trans.add(t);
//		});
//		tr.setTrans(trans);
//		U8WebServiceResult rs = U8Post.TranPost(tr, U8Url.URL);
//		if("1".equals(rs.getCount())){
//			throw new RuntimeException(rs.getMessage());
//		}
//	}
//}