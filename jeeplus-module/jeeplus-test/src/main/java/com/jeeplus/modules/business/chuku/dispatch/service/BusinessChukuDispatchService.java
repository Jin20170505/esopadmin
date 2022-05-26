/**
 * 
 */
package com.jeeplus.modules.business.chuku.dispatch.service;

import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;
import com.jeeplus.modules.sys.entity.User;
import com.jeeplus.modules.sys.utils.UserUtils;
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
	public void save(BusinessChukuDispatch businessChukuDispatch) {
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
	
	@Transactional(readOnly = false)
	public void delete(BusinessChukuDispatch businessChukuDispatch) {
		super.delete(businessChukuDispatch);
		businessChukuDispatchMxMapper.delete(new BusinessChukuDispatchMx(businessChukuDispatch));
	}
	@Transactional(readOnly = false)
    public void chuku(BusinessDispatch dispatch,String userid) {
		BusinessChukuDispatch main = new BusinessChukuDispatch();
		main.setCustomer(dispatch.getCustomer());
		main.setDept(dispatch.getDept());
		main.setDispatchcode(dispatch.getCode());
		main.setFahuoDate(dispatch.getFahuodate());
		main.setCode("XSCK"+DateUtils.getDate("yyyyMMddHHmmss"));
		if(dispatch.getBusinessDispatchMxList()!=null){
			dispatch.getBusinessDispatchMxList().forEach(d->{
				BusinessChukuDispatchMx mx = new BusinessChukuDispatchMx();
				mx.setBatchno(d.getBatchno());
				mx.setCinvcode(d.getCinvcode());
				mx.setCinvname(d.getCinvname());
				mx.setCinvstd(d.getCinvstd());
				mx.setNo(d.getNo());
				mx.setFid(dispatch.getId()).setFhid(d.getId());
				mx.setCk(d.getCk());mx.setHw(d.getHw());
				mx.setNum(d.getNum());mx.setUnit(d.getUnit());
				mx.setScdate(d.getScdate());
				mx.setDelFlag("0");mx.setId("");
				main.getBusinessChukuDispatchMxList().add(mx);
			});
		}
		save(main);
		User user  = UserUtils.get(userid);
		if(user==null){
			user = new User();
		}
		// U8 销售出库 接口
		U8WebXiaoShouBean xiaoShouBean = new U8WebXiaoShouBean();
		try{
			xiaoShouBean.setCode(main.getCode()).setcWhCode(main.getBusinessChukuDispatchMxList().get(0).getCk().getId());
			xiaoShouBean.setcRdCode("23").setcSTCode("01").setBredvouch("0").setcMemo("");
			xiaoShouBean.setdDate(DateUtils.formatDate(main.getFahuoDate())).setcCusCode(main.getCustomer().getId());
			xiaoShouBean.setcDepCode(main.getDept().getId()).setcMaker(user.getNo()).setcPersonCode("");

			main.getBusinessChukuDispatchMxList().forEach(d->{
				U8WebXiaoShouMxBean mx = new U8WebXiaoShouMxBean();
				mx.setcBatch(d.getBatchno()).setcInvCode(d.getCinvcode()).setiQuantity(d.getNum().toString()).setcPosition(d.getHw().getId())
						.setIrowno(d.getNo()+"").setcDLCode(d.getFhid()).setcDLCode(main.getDispatchcode());
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
}