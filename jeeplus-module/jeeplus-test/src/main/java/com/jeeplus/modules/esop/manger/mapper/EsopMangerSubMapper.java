/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.esop.manger.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.esop.manger.entity.EsopMangerSub;

/**
 * 路线MAPPER接口
 * @author Jin
 * @version 2022-05-04
 */
@Mapper
@Repository
public interface EsopMangerSubMapper extends BaseMapper<EsopMangerSub> {
	
}