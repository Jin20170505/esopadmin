/**
 *
 */
package com.jeeplus.modules.business.check.ipqc.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.check.ipqc.entity.BusinessCheckIPQC;

/**
 * IPQC检验MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessCheckIPQCMapper extends BaseMapper<BusinessCheckIPQC> {
	
}