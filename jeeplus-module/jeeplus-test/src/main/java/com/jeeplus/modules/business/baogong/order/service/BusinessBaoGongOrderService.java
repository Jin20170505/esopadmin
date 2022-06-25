/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.service;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.api.bean.baogong.BaoGongBean;
import com.jeeplus.modules.api.bean.baogong.BaoGongItem;
import com.jeeplus.modules.api.bean.chuku.BomBean;
import com.jeeplus.modules.api.bean.chuku.LingLiaoBean;
import com.jeeplus.modules.api.bean.ruku.ProductRuKuBean;
import com.jeeplus.modules.api.bean.zhijian.ZhiJianBean;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrderMingXi;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMapper;
import com.jeeplus.modules.business.baogong.order.mapper.BusinessBaoGongOrderMingXiMapper;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMapper;
import com.jeeplus.modules.business.chuku.lingliao.mapper.BusinessChuKuLingLiaoMxMapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanBom;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanBomMapper;
import com.jeeplus.modules.business.jihuadingdan.mapper.BusinessJiHuaGongDanMapper;
import com.jeeplus.modules.business.ruku.product.mapper.BusinessRuKuProductMapper;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.bom.mapper.BusinessShengChanBomMapper;
import com.jeeplus.modules.business.shengchan.dingdan.mapper.BusinessShengChanDingDanMingXiMapper;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报工单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessBaoGongOrderService extends CrudService<BusinessBaoGongOrderMapper, BusinessBaoGongOrder> {

	@Autowired
	private BusinessBaoGongOrderMingXiMapper businessBaoGongOrderMingXiMapper;
	@Autowired
	private BusinessBaoGongRecordService businessBaoGongRecordService;
	@Autowired
	private BusinessRuKuProductMapper businessRuKuProductMapper;
	@Autowired
	private BusinessShengChanDingDanMingXiMapper businessShengChanDingDanMingXiMapper;

	public BusinessBaoGongOrder get(String id) {
		BusinessBaoGongOrder businessBaoGongOrder = super.get(id);
		businessBaoGongOrder.setBusinessBaoGongOrderMingXiList(businessBaoGongOrderMingXiMapper.findList(new BusinessBaoGongOrderMingXi(businessBaoGongOrder)));
		return businessBaoGongOrder;
	}

	@Autowired
	private BusinessJiHuaGongDanMapper businessJiHuaGongDanMapper;
	@Autowired
	private BusinessJiHuaGongDanBomMapper businessJiHuaGongDanBomMapper;
	// 材料出库单
	public LingLiaoBean getLingLiaoInfo(String bgcode){
		BusinessBaoGongOrder order = mapper.getByCode(bgcode);
		if(order==null){
			throw new RuntimeException("没有找到对应的报工单.");
		}
		String status = businessShengChanDingDanMingXiMapper.getStatus(order.getOrderlineid());
		if("关闭".equals(status)){
			throw new RuntimeException("对应的生产订单已关闭.");
		}
		LingLiaoBean bean = new LingLiaoBean();
		bean.setPlancode(order.getPlancode()).setPlanid(order.getPlanid()).setBgid(order.getId())
				.setBgcode(bgcode);
		List<BusinessJiHuaGongDanBom> boms = businessJiHuaGongDanBomMapper.findList(new BusinessJiHuaGongDanBom(new BusinessJiHuaGongDan(bean.getPlanid())));
		if(boms==null || boms.isEmpty()){
			throw new RuntimeException("无子件信息，请在计划工单中维护子件信息.");
		}
		BusinessJiHuaGongDan jihua = businessJiHuaGongDanMapper.get(bean.getPlanid());
		bean.setCinvcode(jihua.getCinvcode()).setCinvname(jihua.getCinvname()).setCinvstd(jihua.getCinvstd())
				.setNum(jihua.getGdnum()).setUnit(jihua.getUnit()).setRemarks(jihua.getRemarks());
		bean.setSccode(order.getOrdercode()).setScline(order.getOrderline());
		boms.stream().forEach(d->{
			String yl = lingliaoComplete(d.getId(),d.getNum());
			if(!"0".equals(yl)){
				BomBean b = new BomBean();
				b.setNo(d.getNo()).setScyid(d.getScyid()).setBomid(d.getId()).setCinvcode(d.getCinvcode()).setCinvname(d.getCinvname()).setCinvstd(d.getCinvstd())
						.setNum(Double.valueOf(yl)).setUnit(d.getUnitname()).setRemarks(d.getRemarks());
				bean.getBomList().add(b);
			}
		});
		if(bean.getBomList().isEmpty()){
			throw new RuntimeException("该工单领料已完成，不可再领");
		}
		return bean;
	}
	@Autowired
	private BusinessChuKuLingLiaoMxMapper businessChuKuLingLiaoMxMapper;
	private String lingliaoComplete(String pbomid,Double sum){
		Double cknumSum = businessChuKuLingLiaoMxMapper.sumCkNum(pbomid);
		if(cknumSum==null){
			return sum+"";
		}
		double sy = sum - cknumSum;
		if(sy>0){
			return  sy+"";
		}
		return "0";
	}
	// 用于 报工
	public BusinessBaoGongOrder getBaoGongInfo(String bgid,String bghid,String bgcode){
		BusinessBaoGongOrder order = null;
		if(StringUtils.isNotEmpty(bgid)){
			order = super.get(bgid);
			order.getBusinessBaoGongOrderMingXiList().add(businessBaoGongOrderMingXiMapper.get(bghid));
		}else {
			order = mapper.getByCode(bgcode);
			if(order==null){
				return null;
			}else {
				findDoingBaoGaoItem(order,order.getId(),order.getNum());
			}
		}
		return order;
	}


	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "BGD" +ymd + "00001";
		}else {
			code = maxcode.substring(0,9);
			int c =  Integer.valueOf(maxcode.substring(9));
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

	public void findDoingBaoGaoItem(BusinessBaoGongOrder order,String bgid,double gdnum){
		BusinessBaoGongOrderMingXi mingXi = new BusinessBaoGongOrderMingXi();
		mingXi.setComplete("0");
		mingXi.setPid(new BusinessBaoGongOrder(bgid));
		List<BusinessBaoGongOrderMingXi> mingXis = businessBaoGongOrderMingXiMapper.findList(mingXi);
		if(mingXis==null || mingXis.isEmpty()){
			double donenum = businessBaoGongRecordService.getDoneSumNum(bgid,null);
			order.setDelFlag(donenum+"");
			return ;
		}
		mingXis.forEach(m->{
			double donenum = businessBaoGongRecordService.getDoneSumNum(bgid,m.getId());
			m.setNum(gdnum-donenum);
		});
		order.setBusinessBaoGongOrderMingXiList(mingXis);
	}
	public List<BusinessBaoGongOrder> findList(BusinessBaoGongOrder businessBaoGongOrder) {
		return super.findList(businessBaoGongOrder);
	}
	
	public Page<BusinessBaoGongOrder> findPage(Page<BusinessBaoGongOrder> page, BusinessBaoGongOrder businessBaoGongOrder) {
		return super.findPage(page, businessBaoGongOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessBaoGongOrder businessBaoGongOrder) {
		super.save(businessBaoGongOrder);
		for (BusinessBaoGongOrderMingXi businessBaoGongOrderMingXi : businessBaoGongOrder.getBusinessBaoGongOrderMingXiList()){
			if (businessBaoGongOrderMingXi.getId() == null){
				continue;
			}
			if (BusinessBaoGongOrderMingXi.DEL_FLAG_NORMAL.equals(businessBaoGongOrderMingXi.getDelFlag())){
				if (StringUtils.isBlank(businessBaoGongOrderMingXi.getId())){
					businessBaoGongOrderMingXi.setPid(businessBaoGongOrder);
					businessBaoGongOrderMingXi.preInsert();
					businessBaoGongOrderMingXiMapper.insert(businessBaoGongOrderMingXi);
				}else{
					businessBaoGongOrderMingXi.preUpdate();
					businessBaoGongOrderMingXiMapper.update(businessBaoGongOrderMingXi);
				}
			}else{
				businessBaoGongOrderMingXiMapper.delete(businessBaoGongOrderMingXi);
			}
		}
	}
	@Transactional(readOnly = false)
	public void delete(BusinessBaoGongOrder businessBaoGongOrder) {
		Integer rk = businessRuKuProductMapper.hasByBgid(businessBaoGongOrder.getId());
		if(rk!=null && 1 ==rk){
			throw new RuntimeException("已进行了入库，无法删除.");
		}
		Integer ll = businessChuKuLingLiaoMapper.isDoneLingLiao(businessBaoGongOrder.getId());
		if(ll!=null && 1 ==ll){
			throw new RuntimeException("已进行了领料，无法删除.");
		}
		businessBaoGongRecordService.deleteByBgid(businessBaoGongOrder.getId());
		super.delete(businessBaoGongOrder);
		businessJiHuaGongDanMapper.updateisshengcheng(businessBaoGongOrder.getPlanid(), "未生成");
		businessBaoGongOrderMingXiMapper.delete(new BusinessBaoGongOrderMingXi(businessBaoGongOrder));
	}
	@Transactional(readOnly = false)
	public void deleteByPlanid(String planid){
		List<String> bgids = mapper.findBgidByPlanid(planid);
		if(bgids!=null){
			bgids.forEach(id->{
				BusinessBaoGongOrder businessBaoGongOrder = new BusinessBaoGongOrder(id);
				businessBaoGongRecordService.deleteByBgid(businessBaoGongOrder.getId());
				super.delete(businessBaoGongOrder);
				businessBaoGongOrderMingXiMapper.delete(new BusinessBaoGongOrderMingXi(businessBaoGongOrder));
			});
		}
	}

	public boolean hasScOrderFromPlan(String planid){
		Integer rownum = mapper.hasScOrderFromPlan(planid);
		return rownum!=null;
	}
	
	public String getQrCode(String id){
		return mapper.getQrCodeById(id);
	}

	public ZhiJianBean getZhiJianInfo(String bgcode){
		BusinessBaoGongOrder order = mapper.getByCode(bgcode);
		if(order==null){
			throw new RuntimeException("没有找到对应的报工单.");
		}
		String status = businessShengChanDingDanMingXiMapper.getStatus(order.getOrderlineid());
		if("关闭".equals(status)){
			throw new RuntimeException("对应的生产订单已关闭.");
		}
		ZhiJianBean bean = new ZhiJianBean();
		bean.setBatchno(order.getBatchno()).setBgcode(order.getBgcode()).setUnit(order.getUnit()).setBgid(order.getId())
				.setCinvcode(order.getCinvcode()).setCinvname(order.getCinvname()).setCinvstd(order.getCinvstd()).setSccode(order.getOrdercode())
				.setScline(order.getOrderline());
		List<BaoGongItem> items = businessBaoGongOrderMingXiMapper.findBaoGongItemHgLv(order.getId());
		bean.setBaoGongItems(items);
		return bean;
	}

	public ProductRuKuBean getRuKuInfo(String bgcode){
		BusinessBaoGongOrder order = mapper.getByCode(bgcode);
		if(order==null){
			throw new RuntimeException("没有找到对应的报工单.");
		}
		String status = businessShengChanDingDanMingXiMapper.getStatus(order.getOrderlineid());
		if("关闭".equals(status)){
			throw new RuntimeException("对应的生产订单已关闭.");
		}
		Double rukunum = businessRuKuProductMapper.getRuKuNumByBgid(order.getId());
		if(rukunum==null){
			rukunum = 0.0;
		}
		if(order.getNum()<=rukunum){
			throw new RuntimeException("此报工单已入库数量等于报工工单数量,无法入库。");
		}
		String lastBgHid = businessBaoGongOrderMingXiMapper.lastestGxHId(order.getId());
		// 最后一道工序 报工数量
		Double donebgnum = businessBaoGongRecordService.getDoneSumNum(order.getId(),lastBgHid);
		if(donebgnum==null || donebgnum <0.000001){
			throw new RuntimeException("此报工单最后一道工序未报工,无法入库。");
		}
		if(donebgnum<=rukunum){
			throw new RuntimeException("此报工单已入库数量等于最后一道报工数量,无法入库。");
		}
		ProductRuKuBean bean = new ProductRuKuBean();
		bean.setBatchno(order.getBatchno()).setBgcode(order.getBgcode()).setUnit(order.getUnit()).setBgid(order.getId())
		.setCinvcode(order.getCinvcode()).setCinvname(order.getCinvname()).setCinvstd(order.getCinvstd()).setSccode(order.getOrdercode())
		.setScline(order.getOrderline());
		bean.setNum(donebgnum-rukunum);
		return bean;
	}

	@Autowired
	private BusinessChuKuLingLiaoMapper businessChuKuLingLiaoMapper;

	// 根据报工单号 获取 报工信息
	public BaoGongBean getBaoGongInfo(String bgcode){
		BusinessBaoGongOrder order = getBaoGongInfo(null,null,bgcode);
		if(order==null){
			throw new RuntimeException("没有找到对应的报工单.");
		}
		String status = businessShengChanDingDanMingXiMapper.getStatus(order.getOrderlineid());
		if("关闭".equals(status)){
			throw new RuntimeException("对应的生产订单已关闭.");
		}
		if("1".equals(order.getComplate())){
			throw new RuntimeException("此单已完成.");
		}
		Integer i = businessChuKuLingLiaoMapper.isDoneLingLiao(order.getId());
		if(i==null){
			throw new RuntimeException("还未领料，请先进行领料再报工.");
		}
		BaoGongBean bean = new BaoGongBean();
		bean.setBgcode(order.getBgcode()).setBgid(order.getId()).setGdnum(order.getNum()).setSccode(order.getOrdercode()).setScline(order.getOrderline());
		bean.setCinvcode(order.getCinvcode()).setCinvname(order.getCinvname()).setCinvstd(order.getCinvstd());
		if(order.getBusinessBaoGongOrderMingXiList()==null || order.getBusinessBaoGongOrderMingXiList().isEmpty()){
			bean.setSynum(Double.valueOf(order.getDelFlag()));
			return  bean;
		}
		order.getBusinessBaoGongOrderMingXiList().forEach(m->{
			BaoGongItem item = new BaoGongItem();
			item.setBghid(m.getId()).setSite(m.getSite()).setDbnum(m.getNum());
			bean.getBaoGongItems().add(item);
		});
		return bean;
	}

	@Transactional(readOnly = false)
	public void completeBg(String hid){
		businessBaoGongOrderMingXiMapper.completeBg(hid);
	}
	// 工单完成判断
	public void complete(String bgid,Double gdnum){
		// 工序是否存在未完成的
		List<String> completeids = mapper.findCompleteStatusByBgid(bgid);
		if(completeids==null && completeids.isEmpty()){
			// 如果没有工序则查询下报工数量与工单数量
			double donenum = businessBaoGongRecordService.getDoneSumNum(bgid,null);
			if(donenum>=gdnum){
				mapper.completeBg(bgid);
			}
		}else {
			long n = completeids.stream().filter(s->"0".equals(s)).count();
			if(n>0){

			}else {
				mapper.completeBg(bgid);
			}
		}
	}

	@Transactional(readOnly = false)
	public void print(String id){
		mapper.print(id);
	}

	@Transactional(readOnly = false)
	public void baogongchongzhi(String rid){
		Integer rk = businessRuKuProductMapper.hasByBgid(rid);
		if(rk!=null && 1 ==rk){
			throw new RuntimeException("此单已进行了入库，无法重置.");
		}
		businessBaoGongRecordService.deleteByBgid(rid);
		mapper.restOrder(rid);
		mapper.restOrderMx(rid);
	}

	/**
	 * 根据产品编码 查询 未做数量（最后一道工序未报工数量）
	 * @param cinvcode 产品存货编码
	 * @return
	 */
	public Double getNoDoneNumByCinvcode(String cinvcode){
		List<String> schids = mapper.getSchidByCinvcode(cinvcode);
		double sum = 0.0;
		if(schids!=null){
			for (String schid:schids){
				sum += mapper.getNoDoneNumBySchid(schid);
			}
		}
		return sum;
	}

	// 获取生产明细ID
	public String getSchidByOrderid(String id){
		return mapper.getSchidByOrderid(id);
	}

	@Autowired
	private BusinessShengChanBomMapper businessShengChanBomMapper;
	/**
	 * 此方法为 材料领料 数量不足的情况。处理方法：
	 * 根据报工单查询对应生产工单明细的子件信息（U8视图）
	 * 1.如果生产订单明细对应的子件有删除 则需要删除mes的生产子件和计划子件，
	 * 2.如果生产子件剩余领用数量为0，则删除对应生产子件和计划工单的对应子件，
	 * 3.如果生产子件剩余领用数量不为0 （暂不处理）
	 * a.查询是不是存在无对应报工单的领料出库单，则需要提示去U8删除对应的材料出库单后，然后删除mes的领料出库
	 * b.是否存在尾差，消除尾差
	 * @param bgid 报工ID
	 * @param schid 生产明细ID
	 * @param moallocates U8子件
	 * @return
	 */
	@Transactional(readOnly = false)
	public String lingliaodealwith(String bgid,String schid,List<U8Moallocate> moallocates){
		if(moallocates==null || moallocates.isEmpty()){
			return "对应ERP生产明细无子件，请确认下";
		}
		List<String> bomids = businessShengChanBomMapper.findBomIds(schid);
		List<String> delboms = Lists.newArrayList();
		if(bomids==null||bomids.isEmpty()){
			return "对应生产明细无子件，请确认下";
		}
		bomids.forEach(bomid->{
			long c = moallocates.stream().filter(e->bomid.equals(e.getAllocateId())).count();
			if(c==0){
				delboms.add(bomid);
			}
		});
		List<String> nolingids = Lists.newArrayList();
		// erp剩余量为0 需要删除mes的删除子件和计划子件
		moallocates.forEach(d->{
			if(d.getQty()<=d.getIssqty()||(d.getQty()-d.getIssqty())<0.000000001){
				delboms.add(d.getAllocateId());
			}else {
				nolingids.add(d.getAllocateId());
			}
		});
		if(delboms.size()>0){
			// ERP子件有删除 需要删除mes的删除子件和计划子件
			delboms.forEach(bomid->{
				businessShengChanBomMapper.deleteById(bomid);
				businessJiHuaGongDanBomMapper.deleteBomByScyid(bomid);
			});
			return "";
		}
		BusinessShengChanBom bom = new BusinessShengChanBom();
		bom.setSchid(schid);
		// 尾差处理
		List<BusinessShengChanBom> boms = businessShengChanBomMapper.findList(bom);
		if(boms!=null){
			boms.forEach(d->{
				Double sum = businessJiHuaGongDanBomMapper.getSumnumByScYid(d.getId());
				if((d.getNum()-sum) >0 ||(d.getNum()-sum)<0){
					String lastid = businessJiHuaGongDanBomMapper.getIdByCreateDate(d.getId());
					Double fnum = businessJiHuaGongDanBomMapper.getSumnumByScYidCid(d.getId(),lastid);
					if(fnum==null){
						fnum = 0.0;
					}
					businessJiHuaGongDanBomMapper.updateWeiCha(lastid,d.getNum()-fnum);
				}
			});
		}
		return "";
	}
}