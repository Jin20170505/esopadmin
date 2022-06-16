/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.base.route.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.route.entity.BaseRoute;

import java.util.List;

/**
 * 工艺路线MAPPER接口
 * @author Jin
 * @version 2022-05-09
 */
@Mapper
@Repository
public interface BaseRouteMapper extends BaseMapper<BaseRoute> {

    @Select("select 1 from base_route  where id = #{id}")
    Integer hasById(@Param("id") String id);


    void batchInsert(List<BaseRoute> list);

}