/**
 *
 */
package com.jeeplus.modules.base.customer.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.customer.entity.BaseCustomer;

/**
 * 客户档案MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseCustomerMapper extends BaseMapper<BaseCustomer> {
    @Select("select 1 from base_customer where id = #{id}")
    Integer hasById(@Param("id") String id);
}