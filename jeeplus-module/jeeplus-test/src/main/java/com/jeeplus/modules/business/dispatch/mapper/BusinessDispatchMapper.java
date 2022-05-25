/**
 * 
 */
package com.jeeplus.modules.business.dispatch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;

/**
 * 销售发货单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessDispatchMapper extends BaseMapper<BusinessDispatch> {
    @Select("select 1 from business_dispatch where id = #{id}")
    Integer hasById(@Param("id") String id);
}