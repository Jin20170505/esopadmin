/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.dispatch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatchMx;

/**
 * 发货明细MAPPER接口
 * @author Jin
 * @version 2022-05-25
 */
@Mapper
@Repository
public interface BusinessDispatchMxMapper extends BaseMapper<BusinessDispatchMx> {
    @Select("select 1 from business_dispatch_mx where id = #{id}")
    Integer hasById(@Param("id") String id);
}