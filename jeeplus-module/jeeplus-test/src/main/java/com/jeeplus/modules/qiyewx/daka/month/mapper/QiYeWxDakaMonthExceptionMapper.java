/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka.month.entity.QiYeWxDakaMonthException;

/**
 * 异常状态统计信息MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxDakaMonthExceptionMapper extends BaseMapper<QiYeWxDakaMonthException> {
    /***
     * 查询缺勤（矿工）天数
     * @param mid
     * @return
     */
    @Select("select sum(ifnull(count,0)) from qiyewx_daka_month_exception where month_id = #{mid} and exception ='4' ")
    Double getDaysOfKuangGong(@Param("mid") String mid);

}