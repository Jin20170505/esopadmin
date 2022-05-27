/**
 *
 */
package com.jeeplus.modules.business.baogong.record.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.baogong.record.entity.BusinessBaoGongRecord;

/**
 * 员工报工MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessBaoGongRecordMapper extends BaseMapper<BusinessBaoGongRecord> {

    Double getDoneSumNum(@Param("bgid") String bgid,@Param("bghid") String bghid);

}