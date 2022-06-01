/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.arrivalvouch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;

import java.util.List;

/**
 * 到货单明细MAPPER接口
 * @author Jin
 * @version 2022-05-24
 */
@Mapper
@Repository
public interface BusinessArrivalVouchMxMapper extends BaseMapper<BusinessArrivalVouchMx> {

    List<BusinessArrivalVouchMx> findMxList(BusinessArrivalVouchMx businessArrivalVouchMx);

    @Update("update business_arrivalvouch_mx set print_status = '已打印' where id = #{id}")
    void print(@Param("id") String id);

    @Select("select 1 from business_arrivalvouch_mx where id = #{id}")
    Integer hasById(@Param("id") String id);

    @Select("select id from business_arrivalvouch_mx where pid = #{cgid} and isrk = '0'")
    List<String> findNoDoList(@Param("cgid") String cgid);

    BusinessArrivalVouchMx getMxByCinvcodeAndBatchno(@Param("pid") String pid,@Param("cinvcode") String cinvcode,@Param("batchno") String batchno,@Param("scdate") String scdate);
}