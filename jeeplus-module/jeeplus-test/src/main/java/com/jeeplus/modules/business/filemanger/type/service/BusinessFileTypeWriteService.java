/**
 * 
 */
package com.jeeplus.modules.business.filemanger.type.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.filemanger.type.entity.BusinessFileTypeWrite;
import com.jeeplus.modules.business.filemanger.type.mapper.BusinessFileTypeWriteMapper;

/**
 * 文件类型Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessFileTypeWriteService extends TreeService<BusinessFileTypeWriteMapper, BusinessFileTypeWrite> {

	public BusinessFileTypeWrite get(String id) {
		return super.get(id);
	}
	
	public List<BusinessFileTypeWrite> findList(BusinessFileTypeWrite businessFileTypeWrite) {
		if (StringUtils.isNotBlank(businessFileTypeWrite.getParentIds())){
			businessFileTypeWrite.setParentIds(","+businessFileTypeWrite.getParentIds()+",");
		}
		return super.findList(businessFileTypeWrite);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessFileTypeWrite businessFileTypeWrite) {
		super.save(businessFileTypeWrite);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessFileTypeWrite businessFileTypeWrite) {
		super.delete(businessFileTypeWrite);
	}
	
}