/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.dingdan.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.dingdan.entity.BusinessShengChanDingDanMingXi;

import java.util.List;

/**
 * 生产订单明细MAPPER接口
 * @author Jin
 * @version 2022-05-06
 */
@Mapper
@Repository
public interface BusinessShengChanDingDanMingXiMapper extends BaseMapper<BusinessShengChanDingDanMingXi> {

	List<BusinessShengChanDingDanMingXi> findShengChanDingDanMingXi(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi);
}