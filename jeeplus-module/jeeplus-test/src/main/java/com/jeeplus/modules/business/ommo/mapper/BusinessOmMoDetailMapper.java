/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoDetail;

import java.util.List;

/**
 * 委外明细MAPPER接口
 * @author Jin
 * @version 2022-05-28
 */
@Mapper
@Repository
public interface BusinessOmMoDetailMapper extends BaseMapper<BusinessOmMoDetail> {

    void batchInsert(@Param("list") List<BusinessOmMoDetail> list);

    @Update("update business_om_modetail set printstatus = '已打印' where id = #{id}")
    void print(@Param("id") String id);
}