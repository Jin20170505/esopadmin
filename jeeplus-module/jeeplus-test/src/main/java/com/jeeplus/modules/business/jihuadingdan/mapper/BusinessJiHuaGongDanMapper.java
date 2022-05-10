/**
 * 
 */
package com.jeeplus.modules.business.jihuadingdan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;

/**
 * 计划工单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessJiHuaGongDanMapper extends BaseMapper<BusinessJiHuaGongDan> {

    @Select("select 1 from business_jihua_gongdan where order_id = #{scddlineid}  limit 1")
    Integer hasScdd(@Param("scddlineid") String scddlineid);

    @Select("select sum(gdnum) from business_jihua_gongdan where order_id = #{scddlineid}")
    Double getSumNum(@Param("scddlineid") String scddlineid);


    @Update("update business_jihua_gongdan set status = #{status} where id = #{id}")
    void updateSatus(@Param("id") String id,@Param("status") String status);
}