/**
 * 
 */
package com.jeeplus.modules.base.classgroup.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.classgroup.entity.BaseClassGroup;

/**
 * 班组管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseClassGroupMapper extends BaseMapper<BaseClassGroup> {
	
}