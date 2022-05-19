/**
 * 
 */
package com.jeeplus.modules.base.route.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;

import java.util.List;

/**
 * 工艺路线MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseRoteMainMapper extends BaseMapper<BaseRoteMain> {

    @Select("select id, version from base_route_main where product_id = #{productid} and enable = '0' order by version desc")
    List<BaseRoteMain> findVersion(@Param("productid") String productid);

    @Select("select id, version from base_route_main where product_id = (select id from business_product where code = #{cinvcode} limit 1) and enable = '0' order by version desc limit 1")
    BaseRoteMain getRouteVersionByCinvCode(@Param("cinvcode") String cinvcode);

    @Select("select 1 from base_route_main where id = #{id}")
    Integer hasById(@Param("id") String id);
}