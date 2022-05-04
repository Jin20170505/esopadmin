/**
 *
 */
package com.jeeplus.modules.business.product.archive.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.product.archive.entity.BusinessProductTypeOnlyRead;

/**
 * 存货档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessProductTypeOnlyReadMapper extends TreeMapper<BusinessProductTypeOnlyRead> {
	
}