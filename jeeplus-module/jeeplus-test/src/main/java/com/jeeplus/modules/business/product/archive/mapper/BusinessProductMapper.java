/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.product.archive.entity.BusinessProduct;

import java.util.List;

/**
 * 存货档案MAPPER接口
 * @author Jin
 * @version 2022-05-04
 */
@Mapper
@Repository
public interface BusinessProductMapper extends BaseMapper<BusinessProduct> {

    @Select("select id from business_product where code = #{code}")
    String getIdByCode(@Param("code") String code);

    @Select("select 1 from business_product where id = #{code} limit 1")
    Integer hasByCode(@Param("code")String code);

    void batchInsert(List<BusinessProduct> list);

    @Select("select kezhong from business_product where id = #{code}")
    String getKeZhongOfCinvcode(String code);

    @Select("select daynum from business_product where id = #{code}")
    String getDayNumOfCinvcode(String code);
}