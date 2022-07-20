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

	/** 用于排产 */
	List<BusinessShengChanDingDanMingXi> findShengChanDingDanMingXiByPaiChan(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi);
	/** 用于车间生产报表**/
	List<BusinessShengChanDingDanMingXi> findShengChanDingDanMingXiByShengChanBaoBiao(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi);

	List<BusinessShengChanDingDanMingXi> findShengChanDingDanMingXi(BusinessShengChanDingDanMingXi businessShengChanDingDanMingXi);

	@Update("update business_shengchan_dingdan_mingxi set status = '开立' where pid = #{pid} and status = '锁定'")
	void shenhe(@Param("pid") String pid);

	@Select("select distinct cinv_code from business_shengchan_dingdan_mingxi where pid = #{scid}")
	List<String> findCinvcodeByScid(@Param("scid") String scid);
	@Update("update business_shengchan_dingdan_mingxi set status = '锁定' where pid = #{pid} and status = '开立'")
	void fanshen(@Param("pid") String pid);

	@Select("select num from business_shengchan_dingdan_mingxi where id = #{id}")
	Double getScNum(@Param("id") String id);
	@Select("select id from business_shengchan_dingdan_mingxi where  pid = #{pid}")
	List<String> findIdsByPid(@Param("pid") String pid);

	@Select("select 1 from business_shengchan_dingdan_mingxi where id = #{id}")
	Integer hasById(@Param("id") String id);

	BusinessShengChanDingDanMingXi getInfo(@Param("id") String id);

	@Update("update business_shengchan_dingdan_mingxi set ischaidan = '已拆单',donenum=num where id = #{id}")
	void updateChaidan(@Param("id") String id);

	@Update("update business_shengchan_dingdan_mingxi set ischaidan = '未拆完',donenum = donenum+ #{num} where id = #{id}")
	void updateDoneNum(@Param("id") String id,@Param("num") Double num);

	@Update("update business_shengchan_dingdan_mingxi set ischaidan = '未拆完' where id = #{id}")
	void updateChaidanStatus(@Param("id") String id,@Param("status") String status);

	@Select("select status from business_shengchan_dingdan_mingxi where id = #{schid}")
	String getStatus(@Param("schid") String schid);

	@Select("select ischaidan from business_shengchan_dingdan_mingxi where id = #{schid}")
	String getChaidanStatus(@Param("schid") String schid);
}