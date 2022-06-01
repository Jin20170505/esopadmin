/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommoarrivalvouch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalvouchMx;

import java.util.List;

/**
 * 委外到货明细MAPPER接口
 * @author Jin
 * @version 2022-05-31
 */
@Mapper
@Repository
public interface BusinessOmmoArrivalvouchMxMapper extends BaseMapper<BusinessOmmoArrivalvouchMx> {

    List<BusinessOmmoArrivalvouchMx> findMxList(BusinessOmmoArrivalvouchMx businessOmmoArrivalvouchMx);

    void batchInsert(List<BusinessOmmoArrivalvouchMx> list);

    @Update("update business_ommo_arrivalvouch_mx set printstatus = '已打印' where id = #{id}")
    void print(@Param("id") String id);

    BusinessOmmoArrivalvouchMx getMxByPidAndCinvcode(@Param("pid") String pid,@Param("cinvcode") String cinvcode,@Param("batchno") String batchno,@Param("scdate") String scdate);
}