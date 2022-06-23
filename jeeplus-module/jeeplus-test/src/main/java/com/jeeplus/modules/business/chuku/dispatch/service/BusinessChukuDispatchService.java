/**
 * 
 */
package com.jeeplus.modules.business.chuku.dispatch.service;

import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;
import com.jeeplus.modules.base.cangku.mapper.BaseCangKuMapper;
import com.jeeplus.modules.base.huowei.entity.BaseHuoWei;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.jeeplus.u8.webservice.xiaoshouchuku.U8XiaoShouChuKuWebService;
import org.jeeplus.u8.webservice.xiaoshouchuku.entity.U8WebXiaoShouBean;
import org.jeeplus.u8.webservice.xiaoshouchuku.entity.U8WebXiaoShouMxBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.chuku.dispatch.entity.BusinessChukuDispatch;
import com.jeeplus.modules.business.chuku.dispatch.mapper.BusinessChukuDispatchMapper;
import com.jeeplus.modules.business.chuku.dispatch.entity.BusinessChukuDispatchMx;
import com.jeeplus.modules.business.chuku.dispatch.mapper.BusinessChukuDispatchMxMapper;

/**
 * 销售出库单Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessChukuDispatchService extends CrudService<BusinessChukuDispatchMapper, BusinessChukuDispatch> {

	@Autowired
	private BusinessChukuDispatchMxMapper businessChukuDispatchMxMapper;
	
	public BusinessChukuDispatch get(String id) {
		BusinessChukuDispatch businessChukuDispatch = super.get(id);
		businessChukuDispatch.setBusinessChukuDispatchMxList(businessChukuDispatchMxMapper.findList(new BusinessChukuDispatchMx(businessChukuDispatch)));
		return businessChukuDispatch;
	}
	
	public List<BusinessChukuDispatch> findList(BusinessChukuDispatch businessChukuDispatch) {
		return super.findList(businessChukuDispatch);
	}
	
	public Page<BusinessChukuDispatch> findPage(Page<BusinessChukuDispatch> page, BusinessChukuDispatch businessChukuDispatch) {
		return super.findPage(page, businessChukuDispatch);
	}
	
	@Transactional(readOnly = false)
	public synchronized void save(BusinessChukuDispatch businessChukuDispatch) {
		if(StringUtils.isEmpty(businessChukuDispatch.getCode())){
			String code = getCurrentCode(DateUtils.getDate("yyyyMMdd"));
			businessChukuDispatch.setCode(code);
			if("u8".equals(businessChukuDispatch.getRemarks())){
				businessChukuDispatch.setU8code(code);
			}
		}
		super.save(businessChukuDispatch);
		for (BusinessChukuDispatchMx businessChukuDispatchMx : businessChukuDispatch.getBusinessChukuDispatchMxList()){
			if (businessChukuDispatchMx.getId() == null){
				continue;
			}
			if (BusinessChukuDispatchMx.DEL_FLAG_NORMAL.equals(businessChukuDispatchMx.getDelFlag())){
				if (StringUtils.isBlank(businessChukuDispatchMx.getId())){
					businessChukuDispatchMx.setPid(businessChukuDispatch);
					businessChukuDispatchMx.preInsert();
					businessChukuDispatchMxMapper.insert(businessChukuDispatchMx);
				}else{
					businessChukuDispatchMx.preUpdate();
					businessChukuDispatchMxMapper.update(businessChukuDispatchMx);
				}
			}else{
				businessChukuDispatchMxMapper.delete(businessChukuDispatchMx);
			}
		}
	}
	public String getCurrentCode(String ymd){
		String maxcode  = mapper.getMaxCode(ymd);
		String code = "";
		if(StringUtils.isEmpty(maxcode)){
			code = "XSCK" +ymd + "00001";
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
	@Transactional(readOnly = false)
	public void delete(BusinessChukuDispatch businessChukuDispatch) {
		super.delete(businessChukuDispatch);
		businessChukuDispatchMxMapper.delete(new BusinessChukuDispatchMx(businessChukuDispatch));
	}
	@Autowired
	private BaseCangKuMapper cangKuMapper;
	@Transactional(readOnly = false)
    public void chuku(BusinessDispatch dispatch,String userid,String ckid,String mxJson) {
		BusinessChukuDispatch main = new BusinessChukuDispatch();
		main.setCustomer(dispatch.getCustomer());
		main.setDept(dispatch.getDept());
		main.setDispatchcode(dispatch.getCode());
		main.setFahuoDate(dispatch.getFahuodate());
		main.setRemarks("u8");
		JSONObject js = JSONObject.fromObject(mxJson);
		JSONArray array = js.getJSONArray("list");
		array.forEach(e->{
			JSONObject o = JSONObject.fromObject(e);
			BusinessChukuDispatchMx mx = new BusinessChukuDispatchMx();
			mx.setBatchno(o.optString("batchno"));
			mx.setCinvcode(o.optString("cinvcode"));
			mx.setCinvname(o.optString("cinvname"));
			mx.setCinvstd(o.optString("cinvstd"));
			mx.setNo(o.optInt("no"));
			mx.setFid(dispatch.getId()).setFhid(o.optString("id"));
			mx.setCk(new BaseCangKu(ckid));mx.setHw(new BaseHuoWei(o.optString("hwid")));
			mx.setNum(o.optDouble("num"));mx.setUnit(o.optString("unit"));
			mx.setScdate(o.optString("scdate"));
			mx.setDelFlag("0");mx.setId("");
			main.getBusinessChukuDispatchMxList().add(mx);
		});
		User user  = UserUtils.get(userid);
		main.setCreateBy(user);
		save(main);
		if(user==null){
			user = new User();
		}
		String code = cangKuMapper.getCodeById(ckid);
		// U8 销售出库 接口
		U8WebXiaoShouBean xiaoShouBean = new U8WebXiaoShouBean();
		try{
			xiaoShouBean.setCode(main.getCode()).setcWhCode(code);
			xiaoShouBean.setcRdCode("23").setcSTCode("01").setBredvouch("0").setcMemo("");
			xiaoShouBean.setdDate(DateUtils.formatDate(main.getFahuoDate())).setcCusCode(main.getCustomer().getId());
			xiaoShouBean.setcDepCode(main.getDept().getId()).setcMaker(user.getNo()).setcPersonCode(user.getName());

			main.getBusinessChukuDispatchMxList().forEach(d->{
				U8WebXiaoShouMxBean mx = new U8WebXiaoShouMxBean();
				mx.setcBatch(d.getBatchno()).setdMadeDate(d.getScdate()).setcInvCode(d.getCinvcode()).setiQuantity(d.getNum().toString()).setcPosition(d.getHw().getId())
						.setIrowno(d.getNo()+"").setiDLsID(d.getFhid()).setcDLCode(main.getDispatchcode());
				System.out.println(mx);
				xiaoShouBean.getDetails().add(mx);
			});
			U8WebServiceResult rs = U8XiaoShouChuKuWebService.xiaoshouchuku(xiaoShouBean);
			if("1".equals(rs.getCount())){
				throw new RuntimeException(rs.getMessage());
			}
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("数据传U8出错，原因："+e.getMessage());
		}
    }

    public void u8in(String rid) throws Exception {
		BusinessChukuDispatch main = get(rid);
		BusinessChukuDispatchMx ckmx = main.getBusinessChukuDispatchMxList().get(0);
		User user  = UserUtils.get(main.getCreateBy().getId());
		if(user==null){
			user = new User();
		}
		String code = cangKuMapper.getCodeById(ckmx.getCk().getId());
		// U8 销售出库 接口
		U8WebXiaoShouBean xiaoShouBean = new U8WebXiaoShouBean();
		xiaoShouBean.setCode(main.getCode()).setcWhCode(code);
		xiaoShouBean.setcRdCode("23").setcSTCode("01").setBredvouch("0").setcMemo("");
		xiaoShouBean.setdDate(DateUtils.formatDate(main.getFahuoDate())).setcCusCode(main.getCustomer().getId());
		xiaoShouBean.setcDepCode(main.getDept().getId()).setcMaker(user.getNo()).setcPersonCode(user.getName());

		main.getBusinessChukuDispatchMxList().forEach(d->{
			U8WebXiaoShouMxBean mx = new U8WebXiaoShouMxBean();
			mx.setcBatch(d.getBatchno()).setdMadeDate(d.getScdate()).setcInvCode(d.getCinvcode()).setiQuantity(d.getNum().toString()).setcPosition(d.getHw().getId())
					.setIrowno(d.getNo()+"").setiDLsID(d.getFhid()).setcDLCode(main.getDispatchcode());
			System.out.println(mx);
			xiaoShouBean.getDetails().add(mx);
		});
		U8WebServiceResult rs = U8XiaoShouChuKuWebService.xiaoshouchuku(xiaoShouBean);
		if("1".equals(rs.getCount())){
			throw new RuntimeException(rs.getMessage());
		}
	}
}