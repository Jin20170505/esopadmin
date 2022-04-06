/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.month.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka.month.entity.QiYeWxDaKaMonthOverwork;

/**
 * 加班情况MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxDaKaMonthOverworkMapper extends BaseMapper<QiYeWxDaKaMonthOverwork> {
    /** 查询加班时长  */
    QiYeWxDaKaMonthOverwork getDaysOfOverWork(@Param("mid") String mid);
}