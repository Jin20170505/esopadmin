/**
 * 
 */
package com.jeeplus.modules.base.vendor.service;

import java.util.List;

import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.base.route.entity.BaseRoute;
import com.jeeplus.modules.base.vendor.entity.BaseCkOfVendor;
import com.jeeplus.modules.base.vendor.mapper.BaseCkOfVendorMapper;
import com.jeeplus.modules.u8data.vendor.entity.U8Vendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;
import com.jeeplus.modules.base.vendor.mapper.BaseVendorMapper;

/**
 * 供应商档案Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BaseVendorService extends CrudService<BaseVendorMapper, BaseVendor> {

	@Autowired
	private BaseCkOfVendorMapper baseCkOfVendorMapper;

	public BaseVendor get(String id) {
		BaseVendor vendor = super.get(id);
		vendor.setCkOfVendors(baseCkOfVendorMapper.findList(new BaseCkOfVendor(vendor)));
		return vendor;
	}
	
	public List<BaseVendor> findList(BaseVendor baseVendor) {
		return super.findList(baseVendor);
	}
	
	public Page<BaseVendor> findPage(Page<BaseVendor> page, BaseVendor baseVendor) {
		return super.findPage(page, baseVendor);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseVendor baseVendor) {
		super.save(baseVendor);
		for (BaseCkOfVendor ckOfVendor : baseVendor.getCkOfVendors()){
			if (ckOfVendor.getId() == null){
				continue;
			}
			if (BaseCkOfVendor.DEL_FLAG_NORMAL.equals(ckOfVendor.getDelFlag())){
				if (StringUtils.isBlank(ckOfVendor.getId())){
					ckOfVendor.setVendor(baseVendor);
					ckOfVendor.preInsert();
					baseCkOfVendorMapper.insert(ckOfVendor);
				}else{
					ckOfVendor.setVendor(baseVendor);
					ckOfVendor.preUpdate();
					baseCkOfVendorMapper.update(ckOfVendor);
				}
			}else{
				baseCkOfVendorMapper.delete(ckOfVendor);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseVendor baseVendor) {
		super.delete(baseVendor);
		baseCkOfVendorMapper.delete(new BaseCkOfVendor(baseVendor));
	}

	@Transactional(readOnly = false)
	public void sychU8(List<U8Vendor> data) {
		data.stream().filter(d->null==mapper.hasById(d.getcVenCode())).forEach(d->{
			BaseVendor vendor = new BaseVendor();
			vendor.preInsert();
			vendor.setId(d.getcVenCode());
			vendor.setName(d.getcVenName());
			vendor.setAddress(d.getcVenAddress());
			vendor.setTelephone(d.getcVenPhone());
			vendor.setPerson(d.getcVenPerson());
			vendor.setRemarks(d.getcMemo());
			vendor.setEnddate(d.getdEndDate());
			vendor.setCode(d.getcVenCode());
			mapper.insert(vendor);
		});
	}
}