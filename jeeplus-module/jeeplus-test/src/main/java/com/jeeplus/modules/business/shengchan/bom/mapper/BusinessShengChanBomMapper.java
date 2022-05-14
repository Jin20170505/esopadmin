/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.bom.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;

/**
 * 生产订单子件MAPPER接口
 * @author Jin
 * @version 2022-05-13
 */
@Mapper
@Repository
public interface BusinessShengChanBomMapper extends BaseMapper<BusinessShengChanBom> {
	
}