/**
 * 
 */
package com.jeeplus.modules.business.filemanger.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.filemanger.entity.BusinessFileType;
import com.jeeplus.modules.business.filemanger.mapper.BusinessFileTypeMapper;

/**
 * 文件管理Service
 * @author Jin
 */
@Service
@Transactional(readOnly = true)
public class BusinessFileTypeService extends TreeService<BusinessFileTypeMapper, BusinessFileType> {

	public BusinessFileType get(String id) {
		return super.get(id);
	}
	
	public List<BusinessFileType> findList(BusinessFileType businessFileType) {
		if (StringUtils.isNotBlank(businessFileType.getParentIds())){
			businessFileType.setParentIds(","+businessFileType.getParentIds()+",");
		}
		return super.findList(businessFileType);
	}
	
	@Transactional(readOnly = false)
	public void save(BusinessFileType businessFileType) {
		super.save(businessFileType);
	}
	
	@Transactional(readOnly = false)
	public void delete(BusinessFileType businessFileType) {
		super.delete(businessFileType);
	}
	
}