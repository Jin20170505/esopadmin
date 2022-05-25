/**
 * 
 */
package com.jeeplus.modules.business.chuku.dispatch.service;

import java.util.List;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;
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
    public void chuku(BusinessDispatch dispatch) {
		BusinessChukuDispatch main = new BusinessChukuDispatch();
		main.setCustomer(dispatch.getCustomer());
		main.setDept(dispatch.getDept());
		main.setDispatchcode(dispatch.getCode());
		main.setFahuoDate(dispatch.getFahuodate());
		main.setCode(DateUtils.getDate("yyyyMMddHHmmss"));
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
    }
}