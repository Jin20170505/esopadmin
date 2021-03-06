/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ruku.product.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ruku.product.entity.BusinessRuKuProductMx;

/**
 * 产成品入库单明细MAPPER接口
 * @author Jin
 * @version 2022-05-13
 */
@Mapper
@Repository
public interface BusinessRuKuProductMxMapper extends BaseMapper<BusinessRuKuProductMx> {

    @Select("select 1 from business_ruku_product_mx where schid = #{schid} limit 1")
    Integer hasByScHid(@Param("schid") String schid);
}