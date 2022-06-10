/**
 * 
 */
package com.jeeplus.modules.business.chuku.ommo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWai;

/**
 * 委外发料MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessChuKuWeiWaiMapper extends BaseMapper<BusinessChuKuWeiWai> {

    @Select("select max(code) from business_chuku_weiwai where code like concat('WWFL',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    @Select("select 1 from business_chuku_weiwai  where mohid = #{wwhid} limit 1")
    Integer hasByWwHid(@Param("wwhid") String wwhid);
}