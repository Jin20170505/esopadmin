/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.qiyewx.daka_day.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka_day.entity.QiYewxDaKaDay;

import java.util.Date;

/**
 * 打卡日报MAPPER接口
 * @author Jin
 * @version 2021-11-25
 */
@Mapper
@Repository
public interface QiYewxDaKaDayMapper extends BaseMapper<QiYewxDaKaDay> {

    @Delete("delete from qiyewx_daka_day where date between #{start} and #{end}")
    void delByDate(@Param("start") Date start, @Param("end") Date end);
}