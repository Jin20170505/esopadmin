/**
 *
 */
package com.jeeplus.modules.base.workshop.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.workshop.entity.BaseWorkShop;

/**
 * 车间管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseWorkShopMapper extends BaseMapper<BaseWorkShop> {
	
}