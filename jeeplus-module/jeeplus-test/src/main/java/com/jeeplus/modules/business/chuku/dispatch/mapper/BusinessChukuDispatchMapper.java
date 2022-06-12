/**
 * 
 */
package com.jeeplus.modules.business.chuku.dispatch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.dispatch.entity.BusinessChukuDispatch;

/**
 * 销售出库单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessChukuDispatchMapper extends BaseMapper<BusinessChukuDispatch> {

    @Select("select max(code) from business_chuku_dispatch where code like concat('XSCK',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    @Select("select 1 from business_chuku_dispatch where dispatchcode = #{xsfcode} limit 1")
    Integer hasXSFHCode(@Param("xsfcode") String xsfcode);
}