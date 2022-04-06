/**
 *
 */
package com.jeeplus.modules.qiyewx.daka.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.daka.entity.QiYeWxDaKaRecord;

import java.util.Date;
import java.util.List;

/**
 * 打卡记录MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxDaKaRecordMapper extends BaseMapper<QiYeWxDaKaRecord> {

	void batchInsert(List<QiYeWxDaKaRecord> list);

	void deleteFromTo(@Param("beginCheckinTime") Date beginCheckinTime,@Param("endCheckinTime") Date endCheckinTime);

	@Select("select userid from qiyewx_daka_record where DATE_FORMAT(checkin_time,'%Y-%m') = #{ym} order by  checkin_time desc limit 1")
	String getUseridByYm(@Param("ym") String ym);

	Date getLastDaKaTime(@Param("userid") String userid,@Param("ymd") String ymd);

	List<String> findDaKaAdress(@Param("userid") String userid,@Param("ymd") String ymd);

}