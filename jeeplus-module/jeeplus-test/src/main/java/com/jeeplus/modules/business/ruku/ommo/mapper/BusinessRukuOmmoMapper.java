/**
 * 
 */
package com.jeeplus.modules.business.ruku.ommo.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ruku.ommo.entity.BusinessRukuOmmo;

/**
 * 委外入库MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessRukuOmmoMapper extends BaseMapper<BusinessRukuOmmo> {
	
}