/**
 * 
 */
package com.jeeplus.modules.business.shengchan.beiliao.apply.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.beiliao.apply.entity.BusinessShengChanBeiLiaoApply;

/**
 * 生产备料MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessShengChanBeiLiaoApplyMapper extends BaseMapper<BusinessShengChanBeiLiaoApply> {
    @Update("update business_shengchan_beiliao_apply set printstatus = '已打印' where id = #{id}")
    void print(@Param("id") String id);
    @Select("select sum(num) from business_shengchan_beiliao_apply where schid = #{schid}")
    Double getDoneNum(@Param("schid") String schid);
    @Select("select 1 from business_shengchan_beiliao_apply where schid = #{schid} limit 1;")
    Integer hasScOrder(@Param("schid") String schid);
}