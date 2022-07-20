/**
 * 
 */
package com.jeeplus.modules.business.ommo.chaidan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommo.chaidan.entity.BusinessOmmoChaiDan;

/**
 * 委外拆单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessOmmoChaiDanMapper extends BaseMapper<BusinessOmmoChaiDan> {

    @Select("select sum(num) from business_ommo_chaidan where wwhid = #{wwhid}")
    Double getDoneNumByWwhid(@Param("wwhid") String wwhid);

    @Select("select 1 from business_ommo_chaidan where wwhid = #{wwhid} limit 1;")
    Integer hasByWwhid(@Param("wwhid") String wwhid);
    @Select("select 1 from business_ommo_chaidan where wwid = #{wwid} limit 1;")
    Integer hasByWwid(@Param("wwid") String wwid);

    @Select("select max(code) from business_ommo_chaidan where code like concat('WWCD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    @Update("update business_ommo_chaidan set printstatus = '已打印' where id = #{id}")
    void print(@Param("id") String id);
}