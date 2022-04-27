/**
 * 
 */
package com.jeeplus.modules.business.product.archive.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;

/**
 * 产品档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessProductMapper extends BaseMapper<BusinessProduct> {
	
}