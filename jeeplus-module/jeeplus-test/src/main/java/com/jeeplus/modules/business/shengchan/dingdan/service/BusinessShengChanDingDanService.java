/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.service;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.base.route.entity.BaseRoute;
import com.jeeplus.modules.base.route.service.BaseRoteMainService;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanMingXi;
import com.jeeplus.modules.business.jihuadingdan.service.BusinessJiHuaGongDanService;
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
	public BusinessShengChanDingDan get(String id) {
		BusinessShengChanDingDan businessShengChanDingDan = super.get(id);
		businessShengChanDingDan.setBusinessShengChanDingDanMingXiList(businessShengChanDingDanMingXiMapper.findList(new BusinessShengChanDingDanMingXi(businessShengChanDingDan)));
		return businessShengChanDingDan;
	}
	
	public List<BusinessShengChanDingDan> findList(BusinessShengChanDingDan businessShengChanDingDan) {
		return super.findList(businessShengChanDingDan);
	}
	
	public Page<BusinessShengChanDingDan> findPage(Page<BusinessShengChanDingDan> page, BusinessShengChanDingDan businessShengChanDingDan) {
		return super.findPage(page, businessShengChanDingDan);
	}

	public Page<BusinessShengChanDingDanMingXi> findPage(Page<BusinessShengChanDingDanMingXi> page,BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi){
		businessShengChanDingDanMingXi.setPage(page);
		page.setList(businessShengChanDingDanMingXiMapper.findShengChanDingDanMingXi(businessShengChanDingDanMingXi));
		return page;
	}


	@Transactional(readOnly = false)
	public void save(BusinessShengChanDingDan businessShengChanDingDan) {
		super.save(businessShengChanDingDan);
		for (BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi : businessShengChanDingDan.getBusinessShengChanDingDanMingXiList()){
			if (businessShengChanDingDanMingXi.getId() == null){
				continue;
			}
			if (BusinessShengChanDingDanMingXi.DEL_FLAG_NORMAL.equals(businessShengChanDingDanMingXi.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanDingDanMingXi.getId())){
					businessShengChanDingDanMingXi.setP(businessShengChanDingDan);
					businessShengChanDingDanMingXi.preInsert();
					businessShengChanDingDanMingXiMapper.insert(businessShengChanDingDanMingXi);
				}else{
					businessShengChanDingDanMingXi.preUpdate();
					businessShengChanDingDanMingXiMapper.update(businessShengChanDingDanMingXi);
				}
			}else{
				businessShengChanDingDanMingXiMapper.delete(businessShengChanDingDanMingXi);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanDingDan businessShengChanDingDan) {
		super.delete(businessShengChanDingDan);
		businessShengChanDingDanMingXiMapper.delete(new BusinessShengChanDingDanMingXi(businessShengChanDingDan));
	}
	@Transactional(readOnly = false)
	public void shenhe(String ids){
		Arrays.asList(ids.split(",")).forEach(id->businessShengChanDingDanMingXiMapper.shenhe(id));
	}

	@Transactional(readOnly = false)
	public void fanshen(String ids){
		Arrays.asList(ids.split(",")).forEach(id->businessShengChanDingDanMingXiMapper.fanshen(id));
	}
	@Transactional(readOnly = false)
	public void doPlan(String rid,int num){
		BusinessShengChanDingDanMingXi mingXi = businessShengChanDingDanMingXiMapper.get(rid);
		if("完工".equals(mingXi.getStatus())){
			throw new RuntimeException("此订单已完工");
		}
		if("锁定".equals(mingXi.getStatus())){
			throw new RuntimeException("此订单已锁定，请审核后，再操作");
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
		List<BusinessJiHuaGongDan> jiHuaGongDans = Lists.newArrayList();
		double sum = mingXi.getNum();
		String code = "JHGD"+ DateUtils.getDate("yyyyMMddHHmmss");
		if(sum<=num){
			BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
			jiHuaGongDan.setCode(code+"001");
			jiHuaGongDan.setRoute(roteMain);
			jiHuaGongDan.setDept(mingXi.getDept());
			jiHuaGongDan.setDd(mingXi.getP());
			jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
			jiHuaGongDan.setOrderno(mingXi.getNo().toString());
			jiHuaGongDan.setCinvname(mingXi.getCinvname());
			jiHuaGongDan.setCinvstd(mingXi.getStd());
			jiHuaGongDan.setUnit(mingXi.getUnit());
			jiHuaGongDan.setStartdate(mingXi.getStartdate());
			jiHuaGongDan.setEnddate(mingXi.getEnddate());
			jiHuaGongDan.setGdnum(Double.valueOf(num));
			jiHuaGongDan.setScnum(mingXi.getNum());
			jiHuaGongDan.setSynum(0.0);
			jiHuaGongDan.setStatus("未下发");
			routes.forEach(r->{
				BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
				mx.setIncomplete("0");
				mx.setId("");
				mx.setNo(r.getNo());
				mx.setDelFlag("0");
				mx.setSite(r.getSite());
				mx.setNum(jiHuaGongDan.getGdnum());
				jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
			});
			jiHuaGongDans.add(jiHuaGongDan);
		}else {
			int idx =1;
			while (sum>num){
				BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
				jiHuaGongDan.setCode(code+getcode(idx));
				jiHuaGongDan.setRoute(roteMain);
				jiHuaGongDan.setDept(mingXi.getDept());
				jiHuaGongDan.setDd(mingXi.getP());
				jiHuaGongDan.setCinvcode(mingXi.getCinv().getCode());
				jiHuaGongDan.setOrderno(mingXi.getNo().toString());
				jiHuaGongDan.setCinvname(mingXi.getCinvname());
				jiHuaGongDan.setCinvstd(mingXi.getStd());
				jiHuaGongDan.setUnit(mingXi.getUnit());
				jiHuaGongDan.setStartdate(mingXi.getStartdate());
				jiHuaGongDan.setEnddate(mingXi.getEnddate());
				jiHuaGongDan.setGdnum(Double.valueOf(num));
				jiHuaGongDan.setScnum(mingXi.getNum());
				jiHuaGongDan.setSynum(0.0);
				jiHuaGongDan.setStatus("未下发");
				routes.forEach(r->{
					BusinessJiHuaGongDanMingXi mx = new BusinessJiHuaGongDanMingXi();
					mx.setIncomplete("0");
					mx.setId("");
					mx.setNo(r.getNo());
					mx.setDelFlag("0");
					mx.setSite(r.getSite());
					mx.setNum(jiHuaGongDan.getGdnum());
					jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
				});
				jiHuaGongDans.add(jiHuaGongDan);
				sum = sum -num;
				idx++;
			}
			if(sum>0){
				BusinessJiHuaGongDan jiHuaGongDan = new BusinessJiHuaGongDan();
				jiHuaGongDan.setCode(code+getcode(idx));
				jiHuaGongDan.setRoute(roteMain);
				jiHuaGongDan.setDept(mingXi.getDept());
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
					mx.setIncomplete("0");
					mx.setId("");
					mx.setNo(r.getNo());
					mx.setDelFlag("0");
					mx.setSite(r.getSite());
					mx.setNum(jiHuaGongDan.getGdnum());
					jiHuaGongDan.getBusinessJiHuaGongDanMingXiList().add(mx);
				});
				jiHuaGongDans.add(jiHuaGongDan);
			}
		}
		jiHuaGongDans.forEach(d->businessJiHuaGongDanService.save(d));
	}

	private String getcode(int num){
		if(num<10){
			return "00"+num;
		}else if(num<100){
			return "0"+num;
		}else {
			return num+"";
		}
	}
}