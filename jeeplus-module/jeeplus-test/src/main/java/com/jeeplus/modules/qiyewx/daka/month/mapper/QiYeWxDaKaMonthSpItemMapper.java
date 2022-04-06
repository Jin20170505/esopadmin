/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.mapper;

import com.jeeplus.modules.qiyewx.daka.month.entity.QingJiaItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka.month.entity.QiYeWxDaKaMonthSpItem;

import java.util.List;

/**
 * 假勤统计信息MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxDaKaMonthSpItemMapper extends BaseMapper<QiYeWxDaKaMonthSpItem> {
    List<QiYeWxDaKaMonthSpItem> findJiaQinDays(@Param("mid") String mid);

    @Select("select a.acctid as userid,b.duration as timeLen,b.time_type as timeType,b.`name` as name FROM qiyewx_daka_month a LEFT JOIN qiyewx_daka_month_sp_item b on a.id = b.month_id WHERE a.ym = #{ym} and b.type='1'")
    List<QingJiaItem> findQingJiaItems(@Param("ym") String ym);

    @Select("SELECT DISTINCT name FROM qiyewx_daka_month_sp_item WHERE month_id = (SELECT id FROM qiyewx_daka_month where ym = #{ym} LIMIT 1) and type ='1' ORDER BY name desc")
    List<String> findQingJiaName(@Param("ym") String ym);

}