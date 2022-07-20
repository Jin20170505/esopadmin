/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommo.chaidan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommo.chaidan.entity.BusinessOmmoChaiDanMx;

/**
 * 委外发料拆单明细MAPPER接口
 * @author Jin
 * @version 2022-07-18
 */
@Mapper
@Repository
public interface BusinessOmmoChaiDanMxMapper extends BaseMapper<BusinessOmmoChaiDanMx> {
    @Select("select sum(num) from business_ommo_chaidan_mx where wwbomid = #{wwbomid}")
    Double getSumnumByWwbomid(@Param("wwbomid") String wwbomid);

    @Select("select id from business_ommo_chaidan_mx where  wwbomid = #{wwbomid} order by create_date desc limit 1")
    String getIdByCreateDate(@Param("wwbomid") String wwbomid);

    @Select("select sum(num) from business_ommo_chaidan_mx where wwbomid = #{scyid} and id != #{id}")
    Double getSumnumByScYidCid(@Param("scyid") String scyid,@Param("id") String id);

    @Update("update business_ommo_chaidan_mx set num = #{num} where id = #{id}")
    void updateWeiCha(@Param("id") String id,@Param("num") double num);
}