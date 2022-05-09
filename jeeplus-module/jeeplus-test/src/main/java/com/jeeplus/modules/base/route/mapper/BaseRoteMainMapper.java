/**
 * 
 */
package com.jeeplus.modules.base.route.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;

/**
 * 工艺路线MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseRoteMainMapper extends BaseMapper<BaseRoteMain> {
	
}