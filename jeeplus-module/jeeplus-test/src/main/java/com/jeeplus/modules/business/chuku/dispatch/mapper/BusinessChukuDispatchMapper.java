/**
 * 
 */
package com.jeeplus.modules.business.chuku.dispatch.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.dispatch.entity.BusinessChukuDispatch;

/**
 * 销售出库单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessChukuDispatchMapper extends BaseMapper<BusinessChukuDispatch> {
	
}