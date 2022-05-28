/**
 * 
 */
package com.jeeplus.modules.business.chuku.ommo.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.ommo.entity.BusinessChuKuWeiWai;

/**
 * 委外发料MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessChuKuWeiWaiMapper extends BaseMapper<BusinessChuKuWeiWai> {
	
}