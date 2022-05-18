/**
 * 
 */
package com.jeeplus.modules.business.product.type.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.product.type.entity.BusinessProductType;

/**
 * 存货分类MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessProductTypeMapper extends TreeMapper<BusinessProductType> {

    @Select("select 1 from business_product_type where id = #{code}")
    Integer hasByCode(String code);
}