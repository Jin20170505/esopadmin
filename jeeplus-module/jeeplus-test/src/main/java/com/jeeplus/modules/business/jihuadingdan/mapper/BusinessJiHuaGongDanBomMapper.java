package com.jeeplus.modules.business.jihuadingdan.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDanBom;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BusinessJiHuaGongDanBomMapper extends BaseMapper<BusinessJiHuaGongDanBom> {

    @Select("select sum(num) from business_jihua_gongdan_bom where scyid = #{scyid}")
    Double getSumnumByScYid(@Param("scyid") String scyid);

    @Select("select id from business_jihua_gongdan_bom where  scyid = #{scyid} order by create_date desc limit 1")
    String getIdByCreateDate(@Param("scyid") String scyid);

    @Select("select sum(num) from business_jihua_gongdan_bom where scyid = #{scyid} and id != #{id}")
    Double getSumnumByScYidCid(@Param("scyid") String scyid,@Param("id") String id);

    @Update("update business_jihua_gongdan_bom set num = #{num} where id = #{id}")
    void updateWeiCha(@Param("id") String id,@Param("num") double num);

    @Select("select 1 from business_jihua_gongdan_bom where scyid = #{scyid} limit 1")
    Integer hasScYid(@Param("scyid") String scyid);

    @Delete("delete from business_jihua_gongdan_bom where scyid = #{scyid}")
    void deleteBomByScyid(@Param("scyid") String scyid);

    @Delete("delete from business_jihua_gongdan_bom where pid = #{planid}")
    void deleteBomByPlanid(@Param("planid") String scyid);

    List<BusinessJiHuaGongDanBom> findBomsByPlanid(@Param("planid") String planid);
}
