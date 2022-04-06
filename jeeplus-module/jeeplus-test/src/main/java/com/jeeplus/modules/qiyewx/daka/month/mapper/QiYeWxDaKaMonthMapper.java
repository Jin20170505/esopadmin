/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka.month.entity.QiYeWxDaKaMonth;

import java.util.List;

/**
 * 打卡月报MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxDaKaMonthMapper extends BaseMapper<QiYeWxDaKaMonth> {
    @Select("select id from qiyewx_daka_month  where ym = #{ym}")
    List<String> findIdsByYm(@Param("ym") String ym);
    @Select("select id from qiyewx_daka_month  where ym = #{ym} and acctid = #{userid}")
    String getIdByYmAndUserid(@Param("ym")String ym,@Param("userid")String userid);
}