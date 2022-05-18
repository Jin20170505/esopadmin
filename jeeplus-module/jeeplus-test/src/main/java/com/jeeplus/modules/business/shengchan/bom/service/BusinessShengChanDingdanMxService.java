/**
 * 
 */
package com.jeeplus.modules.business.shengchan.bom.service;

import java.util.List;

import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanDingdanMx;
import com.jeeplus.modules.business.shengchan.bom.mapper.BusinessShengChanDingdanMxMapper;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;
import com.jeeplus.modules.business.shengchan.bom.mapper.BusinessShengChanBomMapper;

/**
 * 生产子件Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessShengChanDingdanMxService extends CrudService<BusinessShengChanDingdanMxMapper, BusinessShengChanDingdanMx> {

	@Autowired
	private BusinessShengChanBomMapper businessShengChanBomMapper;
	
	public BusinessShengChanDingdanMx get(String id) {
		BusinessShengChanDingdanMx businessShengChanDingdanMx = new BusinessShengChanDingdanMx();
		return businessShengChanDingdanMx;
	}

	public List<BusinessShengChanBom> findBomList(String schid){
		BusinessShengChanBom bom = new BusinessShengChanBom();
		bom.setSchid(schid);
		return businessShengChanBomMapper.findList(bom);
	}

	public List<BusinessShengChanDingdanMx> findList(BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		return super.findList(businessShengChanDingdanMx);
	}
	
	public Page<BusinessShengChanDingdanMx> findPage(Page<BusinessShengChanDingdanMx> page, BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		return super.findPage(page, businessShengChanDingdanMx);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		for (BusinessShengChanBom businessShengChanBom : businessShengChanDingdanMx.getBusinessShengChanBomList()){
			if (businessShengChanBom.getId() == null){
				continue;
			}
			if (BusinessShengChanBom.DEL_FLAG_NORMAL.equals(businessShengChanBom.getDelFlag())){
				if (StringUtils.isBlank(businessShengChanBom.getId())){
					businessShengChanBom.setSchid(businessShengChanDingdanMx.getId());
					businessShengChanBom.preInsert();
					businessShengChanBomMapper.insert(businessShengChanBom);
				}else{
					businessShengChanBom.setSchid(businessShengChanDingdanMx.getId());
					businessShengChanBom.preUpdate();
					businessShengChanBomMapper.update(businessShengChanBom);
				}
			}else{
				businessShengChanBomMapper.delete(businessShengChanBom);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessShengChanDingdanMx businessShengChanDingdanMx) {
		super.delete(businessShengChanDingdanMx);
		BusinessShengChanBom bom = new BusinessShengChanBom();
		bom.setSchid(businessShengChanDingdanMx.getId());
		businessShengChanBomMapper.delete(bom);
	}

	@Transactional(readOnly = false)
	public void deleteBom(String schid){
		BusinessShengChanBom bom = new BusinessShengChanBom();
		bom.setSchid(schid);
		businessShengChanBomMapper.delete(bom);
	}

	@Transactional(readOnly = false)
	public void sychU8bom(List<U8Moallocate> list){
		list.forEach(d->{
			BusinessShengChanBom businessShengChanBom = new BusinessShengChanBom();
			businessShengChanBom.setSchid(d.getMoDId());
			businessShengChanBom.setAuxbaseqtyn(d.getAuxBaseQtyN());
			businessShengChanBom.setCinvcode(d.getInvcode());
			businessShengChanBom.setCinvname(d.getCinvname());
			businessShengChanBom.setCinvstd(d.getCinvstd());
			businessShengChanBom.setRemarks(d.getRemark());
			businessShengChanBom.setBaseqtyd(d.getBaseQtyD());
			businessShengChanBom.setBaseqtyn(d.getBaseQtyN());
			businessShengChanBom.setDonenum(d.getIssqty());
			businessShengChanBom.setNum(d.getQty());
			businessShengChanBom.setNo(d.getSortseq()+"");
			businessShengChanBom.setUnitcode(d.getcComUnitCode());
			businessShengChanBom.setUnitname(d.getcComUnitName());
			businessShengChanBom.setProducttype(d.getProductType());
			businessShengChanBom.setRate(d.getChangeRate());
			businessShengChanBom.setIsdaochong("0");
			businessShengChanBom.preInsert();
			businessShengChanBom.setId(d.getAllocateId());
			businessShengChanBomMapper.insert(businessShengChanBom);
		});
	}
}