/**
 *
 */
package com.jeeplus.modules.base.factory.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.factory.entity.BaseFactory;

/**
 * 工厂管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseFactoryMapper extends BaseMapper<BaseFactory> {
	
}