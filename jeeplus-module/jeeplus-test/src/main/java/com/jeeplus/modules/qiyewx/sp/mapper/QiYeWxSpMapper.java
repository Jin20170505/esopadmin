/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.mapper;

import com.jeeplus.modules.qiyewx.sp.entity.JiaBanItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.sp.entity.QiYeWxSp;

import java.util.Date;
import java.util.List;

/**
 * 申请审批MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxSpMapper extends BaseMapper<QiYeWxSp> {

    List<String> findIdsByYm(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime, @Param("record_type")String record_type);

    List<String> findPassJiaBanIdsByYmAndUserid(@Param("ym") String ym,@Param("userid")String userid);

    List<QiYeWxSp> findAllByYmAndType(@Param("ym") String ym,@Param("recordType")String recordType);

    List<JiaBanItem> findJiaBanTime(@Param("start") Date start,@Param("end") Date end);

    @Select("select sp_no from qiyewx_sp where sp_status = '1'")
    List<String> findSpNoOfSping();

    @Update("update qiyewx_sp set sp_status=#{status} where  sp_no = #{spNo} and DATE_SUB(CURDATE(), INTERVAL 60 DAY) <= date(apply_time)")
    void updateStatus(@Param("spNo") String spNo,@Param("status") String status);
}