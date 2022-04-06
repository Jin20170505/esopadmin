/**
 *
 */
package com.jeeplus.modules.qiyewx.sp.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.sp.entity.QiYeWxSpApply;

import java.util.List;

/**
 * 审批申请详情MAPPER接口
 * @author Jin
 * @version 2021-08-31
 */
@Mapper
@Repository
public interface QiYeWxSpApplyMapper extends BaseMapper<QiYeWxSpApply> {

	List<QiYeWxSpApply> findJiaBanBean(@Param("mid") String mid);
}