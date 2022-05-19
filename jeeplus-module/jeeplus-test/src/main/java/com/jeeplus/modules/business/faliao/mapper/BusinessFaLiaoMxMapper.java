/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.faliao.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.faliao.entity.BusinessFaLiaoMx;

/**
 * 发料明细MAPPER接口
 * @author Jin
 * @version 2022-05-19
 */
@Mapper
@Repository
public interface BusinessFaLiaoMxMapper extends BaseMapper<BusinessFaLiaoMx> {
	
}