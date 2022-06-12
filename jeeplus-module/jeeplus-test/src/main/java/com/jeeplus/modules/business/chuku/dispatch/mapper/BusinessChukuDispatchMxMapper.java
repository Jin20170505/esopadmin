/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.chuku.dispatch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.dispatch.entity.BusinessChukuDispatchMx;

/**
 * 销售出库单明细MAPPER接口
 * @author Jin
 * @version 2022-05-25
 */
@Mapper
@Repository
public interface BusinessChukuDispatchMxMapper extends BaseMapper<BusinessChukuDispatchMx> {

    @Select("select 1 from business_chuku_dispatch_mx where fid = #{xsfhid} limit 1")
    Integer hasChuKu(@Param("xsfhid") String xsfhid);
}