/**
 *
 */
package com.jeeplus.modules.business.baogong.change.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.baogong.change.entity.BusinessBaoGongChange;

/**
 * 报工修改MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessBaoGongChangeMapper extends BaseMapper<BusinessBaoGongChange> {
	
}