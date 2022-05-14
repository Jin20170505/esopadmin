/**
 *
 */
package com.jeeplus.modules.business.nengyuan.air.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.nengyuan.air.entity.BusinessNengYuanAir;

/**
 * 气能管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessNengYuanAirMapper extends BaseMapper<BusinessNengYuanAir> {
	
}