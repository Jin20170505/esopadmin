/**
 *
 */
package com.jeeplus.modules.base.erp.updatetime.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.erp.updatetime.entity.BaseU8UpdateTime;

/**
 * ERP更新时间MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseU8UpdateTimeMapper extends BaseMapper<BaseU8UpdateTime> {

    @Select("select id,code,name,last_time AS lastTime from base_u8_update_time where code = #{code}")
    BaseU8UpdateTime getByCode(@Param("code") String code);

}