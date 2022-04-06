/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.sp.entity.QiYeWxSpRecord;

/**
 * 审批流程信息MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxSpRecordMapper extends BaseMapper<QiYeWxSpRecord> {
	
}