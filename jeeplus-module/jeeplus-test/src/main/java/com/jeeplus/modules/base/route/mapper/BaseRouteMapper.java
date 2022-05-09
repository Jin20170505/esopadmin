/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.base.route.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.route.entity.BaseRoute;

/**
 * 工艺路线MAPPER接口
 * @author Jin
 * @version 2022-05-09
 */
@Mapper
@Repository
public interface BaseRouteMapper extends BaseMapper<BaseRoute> {
	
}