/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDan;

/**
 * 生产订单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessShengChanDingDanMapper extends BaseMapper<BusinessShengChanDingDan> {

    @Select("select 1 from business_shengchan_dingdan where id = #{id}")
    Integer hasById(@Param("id") String id);
}