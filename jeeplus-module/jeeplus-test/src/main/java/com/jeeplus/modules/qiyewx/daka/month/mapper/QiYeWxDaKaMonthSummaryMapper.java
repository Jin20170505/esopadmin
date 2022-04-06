/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka.month.entity.QiYeWxDaKaMonthSummary;

/**
 * 汇总信息MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxDaKaMonthSummaryMapper extends BaseMapper<QiYeWxDaKaMonthSummary> {
    /**
     * 查询出勤天数
     * @param mid
     * @return
     */
    QiYeWxDaKaMonthSummary getDaysOfChuQin(@Param("mid") String mid);
}