/**
 *
 */
package com.jeeplus.modules.base.productionline.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.productionline.entity.BaseProductionLine;

/**
 * 产线管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseProductionLineMapper extends BaseMapper<BaseProductionLine> {
	
}