/**
 *
 */
package com.jeeplus.modules.business.route.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.route.entity.BusinessRoute;

import java.util.List;

/**
 * 工艺路线MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessRouteMapper extends BaseMapper<BusinessRoute> {

    @Select("select distinct version from business_route where status='0' and product_id=#{productid}")
    List<String> findVersions(@Param("productid") String productid);
}