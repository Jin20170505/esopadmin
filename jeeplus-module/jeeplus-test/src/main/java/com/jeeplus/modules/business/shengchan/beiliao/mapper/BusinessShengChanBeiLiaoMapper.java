/**
 * 
 */
package com.jeeplus.modules.business.shengchan.beiliao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.beiliao.entity.BusinessShengChanBeiLiao;

/**
 * 生产备料MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessShengChanBeiLiaoMapper extends BaseMapper<BusinessShengChanBeiLiao> {

    @Select("select 1 from business_shengchan_beiliao where blid = #{blid} limit 1")
    Integer isSure(@Param("blid") String blid);
}