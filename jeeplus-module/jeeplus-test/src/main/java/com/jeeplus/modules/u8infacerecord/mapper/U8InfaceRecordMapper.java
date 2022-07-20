/**
 *
 */
package com.jeeplus.modules.u8infacerecord.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.u8infacerecord.entity.U8InfaceRecord;

/**
 * ERP接口记录MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface U8InfaceRecordMapper extends BaseMapper<U8InfaceRecord> {
	
}