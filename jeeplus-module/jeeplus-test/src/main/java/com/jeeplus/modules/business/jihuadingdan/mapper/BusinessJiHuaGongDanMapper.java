/**
 * 
 */
package com.jeeplus.modules.business.jihuadingdan.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.jihuadingdan.entity.BusinessJiHuaGongDan;

/**
 * 计划工单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessJiHuaGongDanMapper extends BaseMapper<BusinessJiHuaGongDan> {
	
}