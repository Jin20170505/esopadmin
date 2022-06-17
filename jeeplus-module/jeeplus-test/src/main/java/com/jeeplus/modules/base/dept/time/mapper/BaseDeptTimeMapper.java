/**
 *
 */
package com.jeeplus.modules.base.dept.time.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.dept.time.entity.BaseDeptTime;

/**
 * 部门时间MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseDeptTimeMapper extends BaseMapper<BaseDeptTime> {
	
}