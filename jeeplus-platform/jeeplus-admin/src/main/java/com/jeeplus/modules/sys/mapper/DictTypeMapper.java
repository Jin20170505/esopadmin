/**
 *
 */
package com.jeeplus.modules.sys.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.sys.entity.DictType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;;

/**
 * 数据字典MAPPER接口
 * @author lgf
 * @version 2017-01-16
 */
@Mapper
@Repository
public interface DictTypeMapper extends BaseMapper<DictType> {

	
}