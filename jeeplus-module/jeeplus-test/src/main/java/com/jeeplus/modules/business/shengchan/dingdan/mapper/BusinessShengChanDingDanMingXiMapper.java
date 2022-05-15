/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.dingdan.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

	@Update("update business_shengchan_dingdan_mingxi set status = '开立' where pid = #{pid} and status = '锁定'")
	void shenhe(@Param("pid") String pid);

	@Update("update business_shengchan_dingdan_mingxi set status = '锁定' where pid = #{pid} and status = '开立'")
	void fanshen(@Param("pid") String pid);

	@Select("select num from business_shengchan_dingdan_mingxi where id = #{id}")
	Double getScNum(@Param("id") String id);
	@Select("select id from business_shengchan_dingdan_mingxi where  pid = #{pid}")
	List<String> findIdsByPid(@Param("pid") String pid);
}