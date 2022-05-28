/**
 * 
 */
package com.jeeplus.modules.business.ommo.bom.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;

/**
 * 委外用料MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BussinessOmMoDetailOnlyMapper extends BaseMapper<BussinessOmMoDetailOnly> {
	
}