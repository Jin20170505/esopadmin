/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.paichan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.paichan.entity.BusinessShengChanPaiChaiMx;

import java.util.Date;

/**
 * 生产排产明细MAPPER接口
 * @author Jin
 * @version 2022-06-06
 */
@Mapper
@Repository
public interface BusinessShengChanPaiChaiMxMapper extends BaseMapper<BusinessShengChanPaiChaiMx> {

    @Select("select pdate from business_shengchan_paichan where id = " +
            "(select pid from business_shengchan_paichan_mx where sccode = #{sccode} and scline = #{scline} order by create_date desc limit 1)")
    Date getPanChanDate(@Param("sccode") String sccode,@Param("scline") String scline);
}