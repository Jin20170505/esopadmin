/**
 * 
 */
package com.jeeplus.modules.business.arrivalvouch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;

/**
 * 采购到货单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessArrivalVouchMapper extends BaseMapper<BusinessArrivalVouch> {
    @Select("select 1 from business_arrivalvouch where id = #{id}")
    Integer hasById(@Param("id") String id);
}