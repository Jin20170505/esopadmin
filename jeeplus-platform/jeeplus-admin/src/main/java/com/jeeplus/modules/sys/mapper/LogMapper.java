/**
 *
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.sys.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;;

/**
 * 日志MAPPER接口
 * @author Jin
 * @version 2017-05-16
 */
@Mapper
@Repository
public interface LogMapper extends BaseMapper<Log> {

	public void empty();
}
