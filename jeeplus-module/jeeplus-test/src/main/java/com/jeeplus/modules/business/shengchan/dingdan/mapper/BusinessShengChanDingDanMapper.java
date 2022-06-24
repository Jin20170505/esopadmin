/**
 * 
 */
package com.jeeplus.modules.business.shengchan.dingdan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Update("update business_shengchan_dingdan set status = #{status} where id = #{id}")
    void updateStatus(@Param("id") String id,@Param("status") String status);

    @Select("select max(code) from business_shengchan_dingdan where code like concat('SCDD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    @Update("update business_shengchan_dingdan set status = '关闭' where id = #{mid}")
    void closeByMid(@Param("mid") String mid);

    @Update("update business_shengchan_dingdan_mingxi set status = '关闭' where pid = #{mid}")
    void closeMxByMid(@Param("mid") String mid);
    @Update("update business_shengchan_dingdan_mingxi set status = '关闭' where id = #{mxid}")
    void closeMxByMxid(@Param("mxid") String mxid);

    @Update("update business_shengchan_dingdan_mingxi set status = '开立' where id = #{mxid}")
    void recoverByMxid(@Param("mxid") String mxid);

    @Update("update business_shengchan_dingdan_mingxi set status = '开立' where pid = #{mid}")
    void recoverMxByMid(@Param("mid") String mid);

    @Update("update business_shengchan_dingdan set status = '未审核' where id = #{mid}")
    void recoverByMid(@Param("mid") String mid);

}