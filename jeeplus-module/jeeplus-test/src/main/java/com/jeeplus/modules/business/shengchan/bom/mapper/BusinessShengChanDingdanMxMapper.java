/**
 * 
 */
package com.jeeplus.modules.business.shengchan.bom.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanDingdanMx;

/**
 * 生产子件MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessShengChanDingdanMxMapper extends BaseMapper<BusinessShengChanDingdanMx> {
	
}