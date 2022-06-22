/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.service;

import java.util.*;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.api.bean.beiliao.BeiLiaoBean;
import com.jeeplus.modules.api.bean.beiliao.BeiLiaoItem;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.base.route.entity.BaseRoute;
import com.jeeplus.modules.base.route.service.BaseRoteMainService;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMapper;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMxMapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanBom;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanMingXi;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanBomMapper;
import com.jeeplus.modules.business.jihuadingdan.service.BusinessJiHuaGongDanService;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMapper;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMxMapper;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengChanBeiLiaoApply;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengchanBeiliaoApplyMx;
import com.jeeplus.modules.business.shengchan.beiliao.apply.service.BusinessShengChanBeiLiaoApplyService;
import com.jeeplus.modules.business.shengchan.beiliao.entity.BusinessShengChanBeiLiao;
import com.jeeplus.modules.business.shengchan.beiliao.entity.BusinessShengChanBeiLiaoMx;
import com.jeeplus.modules.business.shengchan.beiliao.service.BusinessShengChanBeiLiaoService;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.bom.mapper.BusinessShengChanBomMapper;
import com.jeeplus.modules.business.shengchan.bom.service.BusinessShengChanDingdanMxService;
import com.jeeplus.modules.business.shengchan.paichan.service.BusinessShengChanPaiChanService;
import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMapper;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;

/**
 * 生产订单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanDingDanService extends CrudService<BusinessShengChanDingDanMapper, BusinessShengChanDingDan> {

	@Autowired
	private BusinessShengChanDingDanMingXiMapper businessShengChanDingDanMingXiMapper;
	@Autowired
	private BusinessJiHuaGongDanService businessJiHuaGongDanService;
	@Autowired
	private BaseRoteMainService baseRoteMainService;
	@Autowired
	private BusinessShengChanDingdanMxService businessShengChanDingdanMxService;

	public BusinessShengChanDingDan get(String id) {
		BusinessShengChanDingDan businessShengChanDingDan = super.get(id);
		businessShengChanDingDan.setBusinessShengChanDingDanMingXiList(businessShengChanDingDanMingXiMapper
				.findList(new BusinessShengChanDingDanMingXi(businessShengChanDingDan)));
		return businessShengChanDingDan;
	}

	public BusinessShengChanDingDanMingXi getMxId(String xmid){
		return businessShengChanDingDanMingXiMapper.get(xmid);
	}

	@Autowired
	private BusinessShengChanBeiLiaoApplyService businessShengChanBeiLiaoApplyService;
	public BeiLiaoBean getBeiLiaoInfo(String blid){
		if(businessShengChanBeiLiaoService.isSure(blid)){
			throw new RuntimeException("该单已经确认");
		}
		BusinessShengChanBeiLiaoApply mx = businessShengChanBeiLiaoApplyService.get(blid);
		BeiLiaoBean bean = new BeiLiaoBean();
		bean.setBlid(mx.getId());
		bean.setCinvcode(mx.getCinvcode());
		bean.setSchid(mx.getSchid()).setUnit(mx.getUnit());
		bean.setCinvname(mx.getCinvname()).setCinvstd(mx.getCinvstd()).setRemarks(mx.getRemarks()).
				setNum(mx.getNum()).setSccode(mx.getSccode()).setScline(mx.getScline());
		List<BusinessShengchanBeiliaoApplyMx> boms = mx.getBusinessShengchanBeiliaoApplyMxList();
		if(boms!=null){
			boms.forEach(d->{
				BeiLiaoItem item = new BeiLiaoItem();
				item.setCinvcode(d.getCinvcode()).setCinvname(d.getCinvname()).setCinvstd(d.getCinvstd()).setNo(d.getNo()+"")
						.setNum(d.getNum()).setUnit(d.getUnit());
				bean.getBeiLiaoItems().add(item);
			});
		}
		return bean;
	}

	@Autowired
	private BusinessShengChanBeiLiaoService businessShengChanBeiLiaoService;
	@Transactional(readOnly = false)
	public void sureBeiLiao(String blid){
		BusinessShengChanBeiLiaoApply mx = businessShengChanBeiLiaoApplyService.get(blid);
		BusinessShengChanBeiLiao beiLiao = new BusinessShengChanBeiLiao();
		beiLiao.setBlid(blid);
		beiLiao.setCinvstd(mx.getCinvstd());beiLiao.setCinvcode(mx.getCinvcode());
		beiLiao.setCinvname(mx.getCinvname());beiLiao.setDept(mx.getDept());
		beiLiao.setNum(mx.getNum());beiLiao.setSchid(mx.getSchid());beiLiao.setSccode(mx.getSccode());
		beiLiao.setScid(mx.getScid());beiLiao.setScline(mx.getScline());
		beiLiao.setUnit(mx.getUnit());
		List<BusinessShengchanBeiliaoApplyMx> boms = mx.getBusinessShengchanBeiliaoApplyMxList();
		if(boms!=null){
			boms.forEach(d->{
				BusinessShengChanBeiLiaoMx b=new BusinessShengChanBeiLiaoMx();
				b.setId("");b.setDelFlag("0");
				b.setCinvcode(d.getCinvcode());b.setCinvname(d.getCinvname());
				b.setCinvstd(d.getCinvstd());b.setNo(Integer.valueOf(d.getNo()));
				b.setNum(d.getNum());b.setUnit(d.getUnit());
				b.setHw(d.getHw());
				beiLiao.getBusinessShengChanBeiLiaoMxList().add(b);
			});
		}
		businessShengChanBeiLiaoService.save(beiLiao);
	}

	public List<BusinessShengChanDingDan> findList(BusinessShengChanDingDan businessShengChanDingDan) {
		return super.findList(businessShengChanDingDan);
	}
	
	public Page<BusinessShengChanDingDan> findPage(Page<BusinessShengChanDingDan> page, BusinessShengChanDingDan businessShengChanDingDan) {
		return super.findPage(page, businessShengChanDingDan);
	}


	public Page<BusinessShengChanDingDanMingXi> findShengChanDingDanMingXiByPaiChanPage(Page<BusinessShengChanDingDanMingXi> page,
														 BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi){
		businessShengChanDingDanMingXi.setPage(page);
		List<BusinessShengChanDingDanMingXi> list = businessShengChanDingDanMingXiMapper.findShengChanDingDanMingXiByPaiChan(businessShengChanDingDanMingXi);
		if(list!=null){
			list.forEach(d->{
				BusinessShengChanBom bom = businessShengChanBomMapper.getBomPaiChan(d.getId());
				if(bom!=null){
					d.setIschaidan(bom.getCinvcode()).setSoseq(bom.getCinvname()).setType(bom.getCinvstd());
				}else {
					d.setIschaidan("").setSoseq("").setType("");
				}
			});
		}
		page.setList(list);
		return page;
	}

	public Page<BusinessShengChanDingDanMingXi> findShengChanDingDanMingXiByShengChanBaoBiaoPage(Page<BusinessShengChanDingDanMingXi> page,
																						BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi){
		businessShengChanDingDanMingXi.setPage(page);
		List<BusinessShengChanDingDanMingXi> list =
				businessShengChanDingDanMingXiMapper.findShengChanDingDanMingXiByShengChanBaoBiao(businessShengChanDingDanMingXi);
		page.setList(list);
		return page;
	}

	public Page<BusinessShengChanDingDanMingXi> findPage(Page<BusinessShengChanDingDanMingXi> page,
														 BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi){
		businessShengChanDingDanMingXi.setPage(page);
		page.setList(businessShengChanDingDanMingXiMapper.findShengChanDingDanMingXi(businessShengChanDingDanMingXi));
		return page;
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "SCDD" +ymd + "00001";
		}else {
			code = maxcode.substring(0,10);
			int c =  Integer.valueOf(maxcode.substring(10));
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
	@Autowired
	private BusinessShengChanBomMapper businessShengChanBomMapper;
	@Transactional(readOnly = false)
	public synchronized void save(BusinessShengChanDingDan businessShengChanDingDan) {
		if(StringUtils.isEmpty(businessShengChanDingDan.getCode())){
			businessShengChanDingDan.setCode(getCurrentCode(DateUtils.getDate("yyyyMMdd")));
		}
		super.save(businessShengChanDingDan);
		for (BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi :
				businessShengChanDingDan.getBusinessShengChanDingDanMingXiList()){
			if (businessShengChanDingDanMingXi.getId() == null){
				continue;
			}
			if (BusinessShengChanDingDanMingXi.DEL_FLAG_NORMAL.equals(businessShengChanDingDanMingXi.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanDingDanMingXi.getId())){
					businessShengChanDingDanMingXi.setP(businessShengChanDingDan);
					businessShengChanDingDanMingXi.preInsert();
					businessShengChanDingDanMingXiMapper.insert(businessShengChanDingDanMingXi);
				}else{
					businessShengChanDingDanMingXi.setP(businessShengChanDingDan);
					businessShengChanDingDanMingXi.preUpdate();
					businessShengChanDingDanMingXiMapper.update(businessShengChanDingDanMingXi);
				}
			}else{
				if(businessJiHuaGongDanService.hasScddLineid(businessShengChanDingDanMingXi.getId())){
					throw new RuntimeException("删除失败，原因：序号为【"+businessShengChanDingDanMingXi.getNo()+"】的明细有对应计划工单存在。");
				}
				businessShengChanDingDanMingXiMapper.delete(businessShengChanDingDanMingXi);
				businessShengChanBomMapper.deleteBySchid(businessShengChanDingDanMingXi.getId());
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanDingDan businessShengChanDingDan) {
		if(businessJiHuaGongDanService.hasScCode(businessShengChanDingDan.getCode())){
			throw new RuntimeException("删除失败，原因：该生产订单有对应计划工单存在。");
		}
		super.delete(businessShengChanDingDan);
		List<String> ids = businessShengChanDingDanMingXiMapper.findIdsByPid(businessShengChanDingDan.getId());
		if(ids!=null || ids.isEmpty()){
			ids.forEach(id->{
				businessShengChanBomMapper.deleteBySchid(id);
			});
		}
		businessShengChanDingDanMingXiMapper.delete(new BusinessShengChanDingDanMingXi(businessShengChanDingDan));
	}
	@Transactional(readOnly = false)
	public void shenhe(String ids){
		//TODO 是否可以审核 开立
		Arrays.asList(ids.split(",")).forEach(id->{
			businessShengChanDingDanMingXiMapper.shenhe(id);
			mapper.updateStatus(id,"已审核");
		});
	}

	@Transactional(readOnly = false)
	public void fanshen(String ids){
		//TODO 检查是否可以反审
		Arrays.asList(ids.split(",")).forEach(id->{
			businessShengChanDingDanMingXiMapper.fanshen(id);
			mapper.updateStatus(id,"未审核");
		});
	}
	@Autowired
	private BusinessShengChanPaiChanService businessShengChanPaiChanService;
	@Transactional(readOnly = false)
	public String doPlan(String rid){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanMingXiMapper.get(rid);
		if("完工".equals(mingXi.getStatus())){
			return mingXi.getP().getCode()+"-"+mingXi.getNo()+",此订单已完工";
		}
		if("锁定".equals(mingXi.getStatus())){
			return mingXi.getP().getCode()+"-"+mingXi.getNo()+",此订单已锁定，请审核后，再操作";
		}
		if("未拆完".equals(mingXi.getIschaidan())){
			throw new RuntimeException(mingXi.getP().getCode()+"-"+mingXi.getNo()+"，此订单已进行手工拆单，请进行手工继续拆单");
		}
		if(businessJiHuaGongDanService.hasScddLineid(rid)){
			return mingXi.getP().getCode()+"-"+mingXi.getNo()+",此订单的计划已存在，无法再次生成";
		}
		BaseRoteMain roteMain = baseRoteMainService.getRouteVersionByCinvCode(mingXi.getCinv().getCode());
		if(roteMain==null||StringUtils.isEmpty(roteMain.getId())){
			return mingXi.getP().getCode()+"-"+mingXi.getNo()+",此存货无工艺路线，需要添加对应的工艺路线";
		}
		List<BaseRoute> routes = baseRoteMainService.getRoutes(roteMain.getId());
		if(routes==null || routes.isEmpty()){
			return mingXi.getP().getCode()+"-"+mingXi.getNo()+",此存货无工艺路线，需要添加对应的工艺路线";
		}
		// TODO 排产检验
		businessShengChanPaiChanService.checckPaiChan(mingXi.getDept().getId(),mingXi.getP().getCode(),mingXi.getNo().toString(),new Date());
		List<BusinessJiHuaGongDan> jiHuaGongDans = Lists.newArrayList();
		double sum = mingXi.getNum();
		BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
		jiHuaGongDan.setRoute(roteMain);
		jiHuaGongDan.setDept(mingXi.getDept());
		jiHuaGongDan.setBatchno(mingXi.getBatchno());
		jiHuaGongDan.setDd(mingXi.getP());
		jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
		jiHuaGongDan.setOrderno(mingXi.getNo().toString());
		jiHuaGongDan.setCinvname(mingXi.getCinvname());
		jiHuaGongDan.setCinvstd(mingXi.getStd());
		jiHuaGongDan.setUnit(mingXi.getUnit());
		jiHuaGongDan.setStartdate(mingXi.getStartdate());
		jiHuaGongDan.setEnddate(mingXi.getEnddate());
		jiHuaGongDan.setGdnum(sum);
		jiHuaGongDan.setScnum(sum);
		jiHuaGongDan.setSynum(0.0);
		jiHuaGongDan.setStatus("未下发");
		routes.forEach(r->{
			BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
			mx.setRouteid(r.getId()).setDaynum(r.getDaynum()).setGprice(r.getGprice()).setGtime(r.getGtime());
			mx.setIncomplete("0");
			mx.setId("");
			mx.setNo(r.getNo());
			mx.setDelFlag("0");
			mx.setSite(r.getSite());
			mx.setNum(jiHuaGongDan.getGdnum());
			jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
		});
		jiHuaGongDans.add(jiHuaGongDan);
		// 子件
		bom(mingXi,jiHuaGongDan,1);
		businessShengChanDingDanMingXiMapper.updateChaidan(rid);
		jiHuaGongDans.forEach(d->businessJiHuaGongDanService.save(d));
		return "";
	}
	public void bom(BusinessShengChanDingDanMingXi mx,BusinessJiHuaGongDan jiHuaGongDan,double r) {
		List<BusinessShengChanBom> boms = businessShengChanDingdanMxService.findBomList(mx.getId());
		if(boms!=null){
			boms.forEach(d->{
				BusinessJiHuaGongDanBom b = new BusinessJiHuaGongDanBom();
				b.setId("");b.setDelFlag("0");
				b.setScyid(d.getId()).setCinvcode(d.getCinvcode()).setCinvname(d.getCinvname()).setCinvstd(d.getCinvstd()).setNo(Integer.valueOf(d.getNo()))
						.setAuxbaseqtyn(d.getAuxbaseqtyn()).setBaseqtyd(d.getBaseqtyd()).setIsdaochong(d.getIsdaochong()).setNum(d.getNum()*r)
						.setDonenum(0.00).setBaseqtyn(d.getBaseqtyn()).setProducttype(d.getProducttype()).setUnitcode(d.getUnitcode()).setHw(d.getHw())
				.setUnitname(d.getUnitname()).setRemarks(d.getRemarks());
				jiHuaGongDan.getBusinessJiHuaGongDanBomList().add(b);
			});
		}
	}

	@Transactional(readOnly = false)
	public void chaidan(String rid,Double num){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanMingXiMapper.get(rid);
		if("完工".equals(mingXi.getStatus())){
			throw new RuntimeException("此订单已完工");
		}
		if("锁定".equals(mingXi.getStatus())){
			throw new RuntimeException("此订单已锁定，请审核后，再操作");
		}
		if("未拆完".equals(mingXi.getIschaidan())){
			throw new RuntimeException("此订单已进行手工拆单，请进行手工继续拆单");
		}
		if("已拆单".equals(mingXi.getIschaidan())){
			throw new RuntimeException("此订单已拆单完成");
		}
		if(businessJiHuaGongDanService.hasScddLineid(rid)){
			throw new RuntimeException("此订单的计划已存在，无法再次生成");
		}
		BaseRoteMain roteMain = baseRoteMainService.getRouteVersionByCinvCode(mingXi.getCinv().getCode());
		if(roteMain==null||StringUtils.isEmpty(roteMain.getId())){
			throw new RuntimeException("此存货无工艺路线，需要添加对应的工艺路线");
		}
		List<BaseRoute> routes = baseRoteMainService.getRoutes(roteMain.getId());
		if(routes==null || routes.isEmpty()){
			throw new RuntimeException("此存货无工艺路线，需要添加对应的工艺路线");
		}
		// TODO 排产检验
		businessShengChanPaiChanService.checckPaiChan(mingXi.getDept().getId(),mingXi.getP().getCode(),mingXi.getNo().toString(),new Date());
		List<BusinessJiHuaGongDan> jiHuaGongDans = Lists.newArrayList();
		double sum = mingXi.getNum();
		if(sum<=num){
			BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
			jiHuaGongDan.setRoute(roteMain);
			jiHuaGongDan.setDept(mingXi.getDept());
			jiHuaGongDan.setBatchno(mingXi.getBatchno());
			jiHuaGongDan.setDd(mingXi.getP());
			jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
			jiHuaGongDan.setOrderno(mingXi.getNo().toString());
			jiHuaGongDan.setCinvname(mingXi.getCinvname());
			jiHuaGongDan.setCinvstd(mingXi.getStd());
			jiHuaGongDan.setUnit(mingXi.getUnit());
			jiHuaGongDan.setStartdate(mingXi.getStartdate());
			jiHuaGongDan.setEnddate(mingXi.getEnddate());
			jiHuaGongDan.setGdnum(num);
			jiHuaGongDan.setScnum(mingXi.getNum());
			jiHuaGongDan.setSynum(0.0);
			jiHuaGongDan.setStatus("未下发");
			routes.forEach(r->{
				BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
				mx.setRouteid(r.getId()).setDaynum(r.getDaynum()).setGprice(r.getGprice()).setGtime(r.getGtime());
				mx.setIncomplete("0");
				mx.setId("");
				mx.setNo(r.getNo());
				mx.setDelFlag("0");
				mx.setSite(r.getSite());
				mx.setNum(jiHuaGongDan.getGdnum());
				jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
			});
			bom(mingXi,jiHuaGongDan,1.0);
			jiHuaGongDans.add(jiHuaGongDan);
		}else {
			int idx =1;
			double s = sum;
			while (sum>num){
				BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
				jiHuaGongDan.setRoute(roteMain);
				jiHuaGongDan.setDept(mingXi.getDept());
				jiHuaGongDan.setBatchno(mingXi.getBatchno());
				jiHuaGongDan.setDd(mingXi.getP());
				jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
				jiHuaGongDan.setOrderno(mingXi.getNo().toString());
				jiHuaGongDan.setCinvname(mingXi.getCinvname());
				jiHuaGongDan.setCinvstd(mingXi.getStd());
				jiHuaGongDan.setUnit(mingXi.getUnit());
				jiHuaGongDan.setStartdate(mingXi.getStartdate());
				jiHuaGongDan.setEnddate(mingXi.getEnddate());
				jiHuaGongDan.setGdnum(num);
				jiHuaGongDan.setScnum(mingXi.getNum());
				jiHuaGongDan.setSynum(0.0);
				jiHuaGongDan.setStatus("未下发");
				routes.forEach(r->{
					BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
					mx.setRouteid(r.getId()).setDaynum(r.getDaynum()).setGprice(r.getGprice()).setGtime(r.getGtime());
					mx.setIncomplete("0");
					mx.setId("");
					mx.setNo(r.getNo());
					mx.setDelFlag("0");
					mx.setSite(r.getSite());
					mx.setNum(jiHuaGongDan.getGdnum());
					jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
				});
				// 子件
				bom(mingXi,jiHuaGongDan,num/s);
				jiHuaGongDans.add(jiHuaGongDan);
				sum = sum -num;
				idx++;
			}
			if(sum>0){
				BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
				jiHuaGongDan.setRoute(roteMain);
				jiHuaGongDan.setDept(mingXi.getDept());
				jiHuaGongDan.setBatchno(mingXi.getBatchno());
				jiHuaGongDan.setDd(mingXi.getP());
				jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
				jiHuaGongDan.setOrderno(mingXi.getNo().toString());
				jiHuaGongDan.setCinvname(mingXi.getCinvname());
				jiHuaGongDan.setCinvstd(mingXi.getStd());
				jiHuaGongDan.setUnit(mingXi.getUnit());
				jiHuaGongDan.setStartdate(mingXi.getStartdate());
				jiHuaGongDan.setEnddate(mingXi.getEnddate());
				jiHuaGongDan.setGdnum(Double.valueOf(sum));
				jiHuaGongDan.setScnum(mingXi.getNum());
				jiHuaGongDan.setSynum(0.0);
				jiHuaGongDan.setStatus("未下发");
				routes.forEach(r->{
					BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
					mx.setRouteid(r.getId()).setDaynum(r.getDaynum()).setGprice(r.getGprice()).setGtime(r.getGtime());
					mx.setIncomplete("0");
					mx.setId("");
					mx.setNo(r.getNo());
					mx.setDelFlag("0");
					mx.setSite(r.getSite());
					mx.setNum(jiHuaGongDan.getGdnum());
					jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
				});
				// 子件
				bom(mingXi,jiHuaGongDan,sum/s);
				jiHuaGongDans.add(jiHuaGongDan);
			}
		}
		businessShengChanDingDanMingXiMapper.updateChaidan(rid);
		jiHuaGongDans.forEach(d->businessJiHuaGongDanService.save(d));
	}
	@Autowired
	private BusinessJiHuaGongDanBomMapper jiHuaGongDanBomMapper;
	// 尾差计算
	@Transactional(readOnly = false)
	public void weichaCheck(String rid){
		List<BusinessShengChanBom> boms = businessShengChanDingdanMxService.findBomList(rid);
		if(boms!=null){
			boms.forEach(d->{
				Double sum = businessJiHuaGongDanService.getSumnumByScYid(d.getId());
				if((d.getNum()-sum) >0){
					String lastid = jiHuaGongDanBomMapper.getIdByCreateDate(d.getId());
					Double fnum = jiHuaGongDanBomMapper.getSumnumByScYidCid(d.getId(),lastid);
					if(fnum==null){
						fnum = 0.0;
					}
					jiHuaGongDanBomMapper.updateWeiCha(lastid,d.getNum()-fnum);
				}
			});
		}
	}
	// 手工车拆单
	@Transactional(readOnly = false)
	public void handlerPlan(String rid,Double gdnum,Double nonum,Double num){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanMingXiMapper.get(rid);
		if("完工".equals(mingXi.getStatus())){
			throw new RuntimeException("此订单已完工");
		}
		if("锁定".equals(mingXi.getStatus())){
			throw new RuntimeException("此订单已锁定，请审核后，再操作");
		}
		if("已拆单".equals(mingXi.getIschaidan())){
			throw new RuntimeException("此订单已拆单完成");
		}
		BaseRoteMain roteMain = baseRoteMainService.getRouteVersionByCinvCode(mingXi.getCinv().getCode());
		if(roteMain==null||StringUtils.isEmpty(roteMain.getId())){
			throw new RuntimeException("此存货无工艺路线，需要添加对应的工艺路线");
		}
		List<BaseRoute> routes = baseRoteMainService.getRoutes(roteMain.getId());
		if(routes==null || routes.isEmpty()){
			throw new RuntimeException("此存货无工艺路线，需要添加对应的工艺路线");
		}
		if("未拆单".equals(mingXi.getIschaidan())){
			// TODO 排产检验
			businessShengChanPaiChanService.checckPaiChan(mingXi.getDept().getId(),mingXi.getP().getCode(),mingXi.getNo().toString(),new Date());
		}
		List<BusinessJiHuaGongDan> jiHuaGongDans = Lists.newArrayList();
		if(num>=nonum){
			BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
			jiHuaGongDan.setRoute(roteMain);
			jiHuaGongDan.setDept(mingXi.getDept());
			jiHuaGongDan.setBatchno(mingXi.getBatchno());
			jiHuaGongDan.setDd(mingXi.getP());
			jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
			jiHuaGongDan.setOrderno(mingXi.getNo().toString());
			jiHuaGongDan.setCinvname(mingXi.getCinvname());
			jiHuaGongDan.setCinvstd(mingXi.getStd());
			jiHuaGongDan.setUnit(mingXi.getUnit());
			jiHuaGongDan.setStartdate(mingXi.getStartdate());
			jiHuaGongDan.setEnddate(mingXi.getEnddate());
			jiHuaGongDan.setGdnum(nonum);
			jiHuaGongDan.setScnum(gdnum);
			jiHuaGongDan.setSynum(0.0);
			jiHuaGongDan.setStatus("未下发");
			routes.forEach(r->{
				BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
				mx.setRouteid(r.getId()).setDaynum(r.getDaynum()).setGprice(r.getGprice()).setGtime(r.getGtime());
				mx.setIncomplete("0");
				mx.setId("");
				mx.setNo(r.getNo());
				mx.setDelFlag("0");
				mx.setSite(r.getSite());
				mx.setNum(jiHuaGongDan.getGdnum());
				jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
			});
			bom(mingXi,jiHuaGongDan,nonum/gdnum);
			jiHuaGongDans.add(jiHuaGongDan);
			businessShengChanDingDanMingXiMapper.updateChaidan(rid);
			jiHuaGongDans.forEach(d->businessJiHuaGongDanService.save(d));
		}else {
			BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
			jiHuaGongDan.setRoute(roteMain);
			jiHuaGongDan.setDept(mingXi.getDept());
			jiHuaGongDan.setBatchno(mingXi.getBatchno());
			jiHuaGongDan.setDd(mingXi.getP());
			jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
			jiHuaGongDan.setOrderno(mingXi.getNo().toString());
			jiHuaGongDan.setCinvname(mingXi.getCinvname());
			jiHuaGongDan.setCinvstd(mingXi.getStd());
			jiHuaGongDan.setUnit(mingXi.getUnit());
			jiHuaGongDan.setStartdate(mingXi.getStartdate());
			jiHuaGongDan.setEnddate(mingXi.getEnddate());
			jiHuaGongDan.setGdnum(num);
			jiHuaGongDan.setScnum(gdnum);
			jiHuaGongDan.setSynum(0.0);
			jiHuaGongDan.setStatus("未下发");
			routes.forEach(r->{
				BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
				mx.setRouteid(r.getId()).setDaynum(r.getDaynum()).setGprice(r.getGprice()).setGtime(r.getGtime());
				mx.setIncomplete("0");
				mx.setId("");
				mx.setNo(r.getNo());
				mx.setDelFlag("0");
				mx.setSite(r.getSite());
				mx.setNum(jiHuaGongDan.getGdnum());
				jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
			});
			bom(mingXi,jiHuaGongDan,num/gdnum);
			jiHuaGongDans.add(jiHuaGongDan);
			businessShengChanDingDanMingXiMapper.updateDoneNum(rid,num);
			jiHuaGongDans.forEach(d->businessJiHuaGongDanService.save(d));
		}

	}
	@Transactional(readOnly = false)
	public List<String> sychu8(List<U8Morder> data){
		List<BusinessShengChanDingDan> dingDans= new ArrayList<>();
		data.forEach(d->{
			BusinessShengChanDingDan dingDan = getShengChanDingDan(d.getMoCode(),dingDans);
			Office dept = new Office(d.getCdepcode());
			String startdate = DateUtils.formatDate(d.getStartdate());
			String enddate = DateUtils.formatDate(d.getDueDate());
			if(dingDan==null){
				dingDan = new BusinessShengChanDingDan();
				dingDan.setDept(dept);
				dingDan.setCode(d.getMoCode());
				dingDan.preInsert();
				dingDan.setId(d.getMoId());
				dingDan.setStartdate(startdate);
				dingDan.setEnddate(enddate);
				dingDan.setStatus("未审核");
				dingDans.add(dingDan);
			}
			BusinessShengChanDingDanMingXi mingXi = new BusinessShengChanDingDanMingXi();
			mingXi.setBatchno(d.getMoLotCode());
			BusinessProduct product = new BusinessProduct();
			product.setCode(d.getCinvcode());
			mingXi.setCinv(product);
			mingXi.setSocode(d.getSoCode()).setSoseq(d.getSoSeq());
			mingXi.setCinvname(d.getCinvname());
			mingXi.setStd(d.getCinvstd());
			mingXi.setNo(d.getSortSeq());
			mingXi.setDept(dept);
			mingXi.setStatus("锁定");
			mingXi.setNum(d.getQty());
			mingXi.setRemarks(d.getRemark());
			mingXi.setUnit(d.getcComUnitName());
			mingXi.setStartdate(startdate);
			mingXi.setEnddate(enddate);
			mingXi.setType(d.getMoClass());
			mingXi.setP(dingDan);
			mingXi.preInsert();
			mingXi.setId(d.getModid());
			mingXi.setCreateDate(d.getCreateTime());
			dingDan.getBusinessShengChanDingDanMingXiList().add(mingXi);
		});
		return saveU8Data(dingDans);
	}
	@Transactional(readOnly = false)
	public List<String> saveU8Data(List<BusinessShengChanDingDan> list){
		List<String> rs = new ArrayList<>();
		list.forEach(d->{
			mapper.insert(d);
			d.getBusinessShengChanDingDanMingXiList().forEach(e->{
				businessShengChanDingDanMingXiMapper.insert(e);
				rs.add(e.getId());
			});
		});
		return  rs;
	}

	public BusinessShengChanDingDan getShengChanDingDan(String code,List<BusinessShengChanDingDan> list){
		Optional<BusinessShengChanDingDan> optional = list.stream().filter(d->code.equals(d.getCode())).findAny();
		if(optional.isPresent()){
			return optional.get();
		}
		return null;
	}
	@Autowired
	private BusinessRuKuProductMapper businessRuKuProductMapper;
	@Autowired
	private BusinessRuKuProductMxMapper businessRuKuProductMxMapper;
	@Autowired
	private BusinessChuKuLingLiaoMapper businessChuKuLingLiaoMapper;

	/**  U8Api 修改 检验 **/
	public void editCheckMid(String mid){
		BusinessShengChanDingDan dd = mapper.get(mid);
		if(businessJiHuaGongDanService.hasScCode(dd.getCode())){
			throw new RuntimeException("不可修改，原因：该生产订单有对应计划工单存在。");
		}

	}

	public void editCheckMxid(String mxids){
		Arrays.asList(mxids.split(",")).forEach(mxid->{
			if(businessJiHuaGongDanService.hasScddLineid(mxid)){
				BusinessShengChanDingDanMingXi mx = businessShengChanDingDanMingXiMapper.get(mxid);
				throw new RuntimeException("不可修改，原因：序号为【"+mx.getNo()+"】的明细有对应计划工单存在。");
			}
		});
	}

	public void editCheckBomid(String bomids){
		Arrays.asList(bomids.split(",")).forEach(bomid->{
			Integer i = jiHuaGongDanBomMapper.hasScYid(bomid);
			if(i!=null && i==1){
				throw new RuntimeException("不可修改，原因：生产订单已经拆单，子件有对应计划工单子件存在。");
			}
		});
	}
	/**  U8Api 删除 检验 **/
	@Transactional(readOnly = false)
	public String deleteCheckMid(String mid){
		BusinessShengChanDingDan dd = mapper.get(mid);
		if(businessChuKuLingLiaoMapper.hasBySccode(dd.getCode())!=null){
			return "该订单已领料，不可删除";
		}
		if(businessRuKuProductMapper.hasBySccode(dd.getCode())!=null){
			return "该订单已有对应的入库，不可删除";
		}
		// 删除生产 计划 报工
		delete(dd);
		businessJiHuaGongDanService.deleteBySccode(dd.getCode());
		return "";
	}

	@Transactional(readOnly = false)
	public String deleteCheckMxid(String mxids){
		List<String> mxidArr = Arrays.asList(mxids.split(","));
		for (String mxid :mxidArr){
			BusinessShengChanDingDanMingXi mx = businessShengChanDingDanMingXiMapper.get(mxid);
			if(businessChuKuLingLiaoMapper.hasBySccodeAndLine(mx.getP().getCode(),mx.getNo().toString())!=null){
				return "序号为【"+mx.getNo()+"】的明细已经有对应的领料，无法删除。";
			}
			if(businessRuKuProductMxMapper.hasByScHid(mxid)!=null){
				return "序号为【"+mx.getNo()+"】的明细已经有对应的入库，无法删除。";
			}
		}
		Arrays.asList(mxids.split(",")).forEach(mxid->{
			businessShengChanDingDanMingXiMapper.delete(new BusinessShengChanDingDanMingXi(mxid));
			businessShengChanBomMapper.deleteBySchid(mxid);
			// 删除计划 报工
			businessJiHuaGongDanService.deleteBySchid(mxid);
		});
		return "";
	}

	@Transactional(readOnly = false)
	public void deleteCheckBomid(String bomids){
		// 删除子件不要校验
		Arrays.asList(bomids.split(",")).forEach(bomid->{
			// 删除计划子件和生产子件
			jiHuaGongDanBomMapper.deleteBomByScyid(bomid);
			businessShengChanBomMapper.delete(new BusinessShengChanBom(bomid));
		});
	}

	@Transactional(readOnly = false)
	public void closeMid(String mid) {
		mapper.closeByMid(mid);
		mapper.closeMxByMid(mid);
	}
	@Transactional(readOnly = false)
    public void closeMxid(String mxids) {
		Arrays.asList(mxids.split(",")).forEach(mxid->{
			mapper.closeMxByMxid(mxid);
		});
    }

}