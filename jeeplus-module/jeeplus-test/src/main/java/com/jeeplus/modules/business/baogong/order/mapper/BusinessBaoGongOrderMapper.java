/**
 * 
 */
package com.jeeplus.modules.business.baogong.order.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.baogong.order.entity.BusinessBaoGongOrder;

/**
 * 报工单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessBaoGongOrderMapper extends BaseMapper<BusinessBaoGongOrder> {
	
}