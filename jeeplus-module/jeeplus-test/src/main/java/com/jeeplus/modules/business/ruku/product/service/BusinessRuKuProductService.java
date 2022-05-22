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
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONObject;
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
	
	@Transactional(readOnly = false)
	public void save(BusinessRuKuProduct businessRuKuProduct) {
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
	private BusinessShengChanDingDanMingXiMapper shengChanDingDanMingXiMapper;
	@Transactional(readOnly = false)
	public void ruku(String bgid,String ckid,String hwid,String remarks,String userid,Double rukunum){
		BusinessBaoGongOrder order  = businessBaoGongOrderService.get(bgid);
		if(order==null){
			throw new RuntimeException("报工单不存在啦");
		}
		if(!"1".equals(order.getComplate())){
			throw new RuntimeException("此单报工未完成,不可入库");
		}
		BusinessRuKuProduct product = new BusinessRuKuProduct();
		BusinessRuKuProductMx mx = new BusinessRuKuProductMx();
		product.setBatchno(getBatchno(DateUtils.getDate("yyMMdd")));
		product.setBgcode(order.getBgcode());
		product.setBgid(order.getId());
		product.setCangku(new BaseCangKu(ckid));
		product.setCinvcode(order.getCinvcode());
		product.setCinvname(order.getCinvname());
		product.setCinvstd(order.getCinvstd());
		product.setCode("CPRK"+ DateUtils.getDate("yyyyMMddHHmmss"));
		product.setNum(rukunum);
		product.setSych("0");
		product.setSccode(order.getOrdercode());
		product.setRemarks(remarks);
		product.preInsert();
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
		mx.preInsert();
		mx.setCreateBy(new User(userid));
		mapper.insert(product);
		businessRuKuProductMxMapper.insert(mx);
		User user = UserUtils.get(userid);
		// TODO U8成品入库
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
			r.setCbMemo("");
			r.setdMadeDate(DateUtils.getDate());
			BusinessShengChanDingDanMingXi shengChanDingDanMingXi = shengChanDingDanMingXiMapper.getInfo(mx.getSchid());
			if(shengChanDingDanMingXi!=null){
				r.setCmocode(shengChanDingDanMingXi.getP().getCode());
				r.setImoseq(shengChanDingDanMingXi.getNo()+"");
			}
			rd10s.add(r);
			rd10.setRd10s(rd10s);
			String rs = U8Post.Rd10Post(rd10, U8Url.URL);
			if(StringUtils.isEmpty(rs)){
				throw new RuntimeException("数据传U8出错,未有返回值。");
			}
			JSONObject rsjson = JSONObject.fromObject(rs);
			if("1".equals(rsjson.optString("count"))){
				throw new RuntimeException(rsjson.optString("message"));
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
		}
	}

}