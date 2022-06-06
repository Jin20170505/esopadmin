/**
 * 
 */
package com.jeeplus.modules.business.shengchan.paichan.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.paichan.entity.BusinessShengChanPaiChan;

/**
 * 生产排产MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessShengChanPaiChanMapper extends BaseMapper<BusinessShengChanPaiChan> {
	
}