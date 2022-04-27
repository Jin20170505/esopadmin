/**
 *
 */
package com.jeeplus.modules.business.product.type.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.product.type.entity.BusinessProductType;

/**
 * 产品类型MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessProductTypeMapper extends BaseMapper<BusinessProductType> {
	
}