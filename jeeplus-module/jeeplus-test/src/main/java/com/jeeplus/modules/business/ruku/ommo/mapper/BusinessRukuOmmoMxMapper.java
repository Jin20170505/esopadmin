/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ruku.ommo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ruku.ommo.entity.BusinessRukuOmmoMx;

/**
 * 委外入库明细MAPPER接口
 * @author Jin
 * @version 2022-05-31
 */
@Mapper
@Repository
public interface BusinessRukuOmmoMxMapper extends BaseMapper<BusinessRukuOmmoMx> {
    @Select("select sum(rukunum) from business_ruku_ommo_mx where wdhid = #{wdhid}")
    Double sumRukuNumByWwhid(@Param("wdhid") String wdhid);

    @Select("select 1 from business_ruku_ommo_mx where wdid = #{wdid} limit 1")
    Integer hasWdid(@Param("wdid") String wdid);

    @Select("select 1 from business_ruku_ommo_mx where wdhid = #{wdhid} limit 1")
    Integer hasWdhid(@Param("wdhid") String wdhid);

}