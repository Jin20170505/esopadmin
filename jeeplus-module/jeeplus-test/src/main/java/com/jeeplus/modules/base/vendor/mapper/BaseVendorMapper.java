/**
 *
 */
package com.jeeplus.modules.base.vendor.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.vendor.entity.BaseVendor;

/**
 * 供应商档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseVendorMapper extends BaseMapper<BaseVendor> {

    @Select("select 1 from base_vendor where id = #{id}")
    Integer hasById(@Param("id") String id);
}