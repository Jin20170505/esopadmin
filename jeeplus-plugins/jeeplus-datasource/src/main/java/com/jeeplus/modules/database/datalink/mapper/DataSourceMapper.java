/**
 *
 */
package com.jeeplus.modules.database.datalink.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.database.datalink.entity.DataSource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 数据库连接MAPPER接口
 *
 * @author 刘高峰
 * @version 2018-08-06
 */
@Mapper
@Repository
public interface DataSourceMapper extends BaseMapper<DataSource> {

}
