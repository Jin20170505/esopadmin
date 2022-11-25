/**
 * 
 */
package com.jeeplus.modules.business.ruku.product.service;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMingXiMapper;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.jeeplus.u8.webservice.U8Post;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.YT_Rd10;
import org.jeeplus.u8.webservice.YT_Rds10;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProduct;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMapper;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMxMapper;

/**
 * 产成品入库Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessRuKuProductService extends CrudService<BusinessRuKuProductMapper, BusinessRuKuProduct> {
	@Autowired
	private BusinessRuKuProductMxMapper businessRuKuProductMxMapper;
	public BusinessRuKuProduct get(String id) {
		BusinessRuKuProduct businessRuKuProduct = super.get(id);
		businessRuKuProduct.setBusinessRuKuProductMxList(businessRuKuProductMxMapper.findList(new BusinessRuKuProductMx(businessRuKuProduct)));
		return businessRuKuProduct;
	}
	
	public List<BusinessRuKuProduct> findList(BusinessRuKuProduct businessRuKuProduct) {
		return super.findList(businessRuKuProduct);
	}
	
	public Page<BusinessRuKuProduct> findPage(Page<BusinessRuKuProduct> page, BusinessRuKuProduct businessRuKuProduct) {
		return super.findPage(page, businessRuKuProduct);
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "CPRK" +ymd + "00001";
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
	public String getBuLuCurrentCode(String ymd){
		String maxcode  = mapper.getBLMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "BLRK" +ymd + "00001";
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
	public synchronized void save(BusinessRuKuProduct businessRuKuProduct) {
		if(StringUtils.isEmpty(businessRuKuProduct.getCode())){
			businessRuKuProduct.setCode(getCurrentCode(DateUtils.getDate("yyyyMMdd")));
		}
		super.save(businessRuKuProduct);
		for (BusinessRuKuProductMx businessRuKuProductMx : businessRuKuProduct.getBusinessRuKuProductMxList()){
			if (businessRuKuProductMx.getId() == null){
				continue;
			}
			if (BusinessRuKuProductMx.DEL_FLAG_NORMAL.equals(businessRuKuProductMx.getDelFlag())){
				if (StringUtils.isBlank(businessRuKuProductMx.getId())){
					businessRuKuProductMx.setP(businessRuKuProduct);
					businessRuKuProductMx.preInsert();
					businessRuKuProductMxMapper.insert(businessRuKuProductMx);
				}else{
					businessRuKuProductMx.preUpdate();
					businessRuKuProductMxMapper.update(businessRuKuProductMx);
				}
			}else{
				businessRuKuProductMxMapper.delete(businessRuKuProductMx);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessRuKuProduct businessRuKuProduct) {
		super.delete(businessRuKuProduct);
		businessRuKuProductMxMapper.delete(new BusinessRuKuProductMx(businessRuKuProduct));
	}

	public String getBatchno(String ymd){
		String batchno = mapper.getMaxBatchno(ymd);
		if(batchno==null){
			return ymd+"001";
		}
		String no = batchno.substring("yyyymmdd".length());
		int nx = Integer.valueOf(no) +1;
		if(nx<10){
			return ymd + "00"+nx;
		}else if(nx<100){
			return ymd + "0"+ nx;
		}else {
			return ymd + nx;
		}
	}

	@Autowired
	private BusinessBaoGongOrderService businessBaoGongOrderService;

	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Autowired
	private BaseHuoWeiMapper huoWeiMapper;
	@Autowired
	private BusinessRuKuProductMapper businessRuKuProductMapper;
	@Autowired
	private BusinessShengChanDingDanMingXiMapper shengChanDingDanMingXiMapper;
	@Autowired
	private BusinessBaoGongOrderMingXiMapper businessBaoGongOrderMingXiMapper;
	@Autowired
	private BusinessBaoGongRecordService businessBaoGongRecordService;
	@Transactional(readOnly = false)
	public void ruku(String bgid,String ckid,String hwid,String remarks,String userid,Double rukunum){
		BusinessBaoGongOrder order  = businessBaoGongOrderService.get(bgid);
		if(order==null){
			throw new RuntimeException("报工单不存在啦");
		}
		// TODO 根据报工的最后一条工序的合格数量进行入库
		Double donerukunum = businessRuKuProductMapper.getRuKuNumByBgid(order.getId());
		if(donerukunum==null){
			donerukunum = 0.0;
		}
		Double sumrknum = rukunum+donerukunum;
		if(order.getNum()<sumrknum){
			throw new RuntimeException("入库数量大于报工工单数量,无法入库。");
		}
		String lastBgHid = businessBaoGongOrderMingXiMapper.lastestGxHId(order.getId());
		// 最后一道工序 报工数量
		Double donebgnum = businessBaoGongRecordService.getDoneSumNum(order.getId(),lastBgHid);
		if(donebgnum<sumrknum){
			throw new RuntimeException("此报工单入库数量大于最后一道报工数量,无法入库。");
		}
		BusinessRuKuProduct product = new BusinessRuKuProduct();
		BusinessRuKuProductMx mx = new BusinessRuKuProductMx();
		product.setDept(new Office(order.getDept()));
		if(StringUtils.isNotEmpty(order.getBatchno())){
			product.setBatchno(order.getBatchno());
		}else {
			product.setBatchno(getBatchno(DateUtils.getDate("yyMMdd")));
		}
		product.setBgcode(order.getBgcode());
		product.setBgid(order.getId());
		product.setCangku(new BaseCangKu(ckid));
		product.setCinvcode(order.getCinvcode());
		product.setCinvname(order.getCinvname());
		product.setCinvstd(order.getCinvstd());
		product.setNum(rukunum);
		product.setSych("0");
		product.setSccode(order.getOrdercode());
		product.setRemarks(remarks);
		product.setCreateBy(new User(userid));
		mx.setP(product);
		mx.setHuowei(new BaseHuoWei(hwid));
		mx.setLinecode(order.getOrderline());
		mx.setSchid(order.getOrderlineid());
		mx.setSchid(order.getOrderlineid());
		mx.setUnit(order.getUnit());
		mx.setCinvcode(order.getCinvcode());
		mx.setCinvname(order.getCinvname());
		mx.setCinvstd(order.getCinvstd());
		mx.setNum(rukunum);
		mx.setSych("1");
		mx.setRemarks(remarks);
		mx.setCreateBy(new User(userid));
		mx.setId("");mx.setDelFlag("0");
		product.getBusinessRuKuProductMxList().add(mx);
		save(product);
		BusinessShengChanDingDanMingXi shengChanDingDanMingXi = shengChanDingDanMingXiMapper.getInfo(mx.getSchid());
		// TODO 判断入库数量 是否大于生产数量 关闭单据
		User user = UserUtils.get(userid);
		try{
			YT_Rd10 rd10 = new YT_Rd10();
			rd10.setcBusType("成品入库");
			rd10.setcSource("生产订单");
			rd10.setcCode(product.getCode());
			rd10.setcMaker(user.getName());
			rd10.setcRdcode("13");
			String ckcode = cangKuMapper.getCodeById(ckid);
			rd10.setcWhCode(ckcode);
			String hwcode = huoWeiMapper.getCodeById(hwid);
			rd10.setcDepCode(user.getOffice().getCode());
			rd10.setdDate(DateUtils.getDate());
			rd10.setRemarks(remarks);
			List<YT_Rds10> rd10s = Lists.newArrayList();
			YT_Rds10 r = new YT_Rds10();
			r.setcInvCode(mx.getCinvcode());
			r.setiQuantity(mx.getNum()+"");
			r.setcPosition(hwcode);
			r.setIrowno("1");
			r.setBatch(product.getBatchno());
			r.setCbMemo(order.getBgcode());
			r.setdMadeDate(DateUtils.getDate());
			if(shengChanDingDanMingXi!=null){
				r.setCmocode(shengChanDingDanMingXi.getP().getCode());
				r.setImoseq(shengChanDingDanMingXi.getNo()+"");
			}
			rd10s.add(r);
			rd10.setRd10s(rd10s);
			U8WebServiceResult rs = U8Post.Rd10Post(rd10, U8Url.URL);
			if("1".equals(rs.getCount())){
				throw new RuntimeException(rs.getMessage());
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
		}
	}


	@Transactional(readOnly = false)
	public void u8ruku(String rid) throws Exception {
		BusinessRuKuProduct product = get(rid);
		BusinessRuKuProductMx mx = product.getBusinessRuKuProductMxList().get(0);
		User user = UserUtils.get(product.getCreateBy().getId());
		YT_Rd10 rd10 = new YT_Rd10();
		rd10.setcBusType("成品入库");
		rd10.setcSource("生产订单");
		rd10.setcCode(product.getCode());
		rd10.setcMaker(user.getName());
		rd10.setcRdcode("13");
		String ckcode = cangKuMapper.getCodeById(product.getCangku().getId());
		rd10.setcWhCode(ckcode);
		String hwcode = huoWeiMapper.getCodeById(mx.getHuowei().getId());
		rd10.setcDepCode(product.getDept().getCode());
		rd10.setdDate(DateUtils.getDate());
		rd10.setRemarks(product.getRemarks());
		List<YT_Rds10> rd10s = Lists.newArrayList();
		YT_Rds10 r = new YT_Rds10();
		r.setcInvCode(mx.getCinvcode());
		r.setiQuantity(mx.getNum()+"");
		r.setcPosition(hwcode);
		r.setIrowno("1");
		r.setBatch(product.getBatchno());
		r.setCbMemo(product.getBgcode());
		r.setdMadeDate(DateUtils.getDate());
		r.setCmocode(product.getSccode());
		r.setImoseq(mx.getLinecode());
		rd10s.add(r);
		rd10.setRd10s(rd10s);
		U8WebServiceResult rs = U8Post.Rd10Post(rd10, U8Url.URL);
		if("1".equals(rs.getCount())){
			throw new RuntimeException(rs.getMessage());
		}
	}
	@Transactional(readOnly = false)
	public void buchong(String bgid,String batchno,Double rukunum,String ckid,String hw,String remarks){
		BusinessBaoGongOrder order  = businessBaoGongOrderService.getMain(bgid);
		if(order==null){
			throw new RuntimeException("报工单不存在啦");
		}
		Double donerukunum = businessRuKuProductMapper.getRuKuNumByBgid(order.getId());
		if(donerukunum==null){
			donerukunum = 0.0;
		}
		Double sumrknum =rukunum +donerukunum;
		if(order.getNum()<sumrknum){
			throw new RuntimeException("入库数量大于报工工单数量,无法入库。");
		}
		String lastBgHid = businessBaoGongOrderMingXiMapper.lastestGxHId(order.getId());
		// 最后一道工序 报工数量
		Double donebgnum = businessBaoGongRecordService.getDoneSumNum(order.getId(),lastBgHid);
		if(donebgnum<sumrknum){
			throw new RuntimeException("此报工单入库数量大于最后一道报工数量,无法入库。");
		}
		BusinessRuKuProduct product = new BusinessRuKuProduct();
		BusinessRuKuProductMx mx = new BusinessRuKuProductMx();
		product.setDept(new Office(order.getDept()));
		product.setBatchno(batchno);
		product.setBgcode(order.getBgcode());
		product.setBgid(order.getId());
		product.setCode(getBuLuCurrentCode(DateUtils.getDate("yyyyMMdd")));
		product.setCangku(new BaseCangKu(ckid));
		product.setCinvcode(order.getCinvcode());
		product.setCinvname(order.getCinvname());
		product.setCinvstd(order.getCinvstd());
		product.setNum(rukunum);
		product.setSych("0");
		product.setSccode(order.getOrdercode());
		product.setRemarks(remarks);
		mx.setP(product);
		mx.setHuowei(new BaseHuoWei(hw));
		mx.setLinecode(order.getOrderline());
		mx.setSchid(order.getOrderlineid());
		mx.setSchid(order.getOrderlineid());
		mx.setUnit(order.getUnit());
		mx.setCinvcode(order.getCinvcode());
		mx.setCinvname(order.getCinvname());
		mx.setCinvstd(order.getCinvstd());
		mx.setNum(rukunum);
		mx.setSych("1");
		mx.setRemarks(remarks);
		mx.setId("");mx.setDelFlag("0");
		product.getBusinessRuKuProductMxList().add(mx);
		save(product);
	}
}