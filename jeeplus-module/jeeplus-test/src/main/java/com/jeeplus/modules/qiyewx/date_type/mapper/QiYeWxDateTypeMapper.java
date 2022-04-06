/**
 *
 */
package com.jeeplus.modules.qiyewx.date_type.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.qiyewx.date_type.entity.QiYeWxDateType;

import java.util.List;

/**
 * 日期所属MAPPER接口
 * @author Jin
 * @version 2021-09-11
 */
@Mapper
@Repository
public interface QiYeWxDateTypeMapper extends BaseMapper<QiYeWxDateType> {

    void batchInsert(List<QiYeWxDateType> list);

    @Delete("delete from qiyewx_date_type where ymd  like concat(#{ym},'%')")
    void deleteByYm(@Param("ym") String ym);

    @Update("update qiyewx_date_type set dtype=#{type} where id = #{id}")
    void updateType(@Param("id") String id, @Param("type") String type);

    @Select("select dtype  from qiyewx_date_type where ymd = #{ymd} limit 1")
    String getTypeByYmd(@Param("ymd") String ymd);

    @Select("select dtype as type,ymd from qiyewx_date_type where  ymd  like concat(#{ym},'%')")
    List<QiYeWxDateType> findByYm(@Param("ym") String ym);

    @Select("select count(*) from qiyewx_date_type where  ymd  like concat(#{ym},'%') and dtype = '0' ")
    int getMaxWorkDay(@Param("ym") String ym);
}