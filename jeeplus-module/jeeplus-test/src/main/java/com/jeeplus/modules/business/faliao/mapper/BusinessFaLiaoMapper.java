/**
 * 
 */
package com.jeeplus.modules.business.faliao.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.faliao.entity.BusinessFaLiao;

/**
 * 调拨单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessFaLiaoMapper extends BaseMapper<BusinessFaLiao> {
	
}