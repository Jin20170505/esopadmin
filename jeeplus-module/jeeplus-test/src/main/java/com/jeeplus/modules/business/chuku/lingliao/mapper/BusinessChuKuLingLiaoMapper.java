/**
 * 
 */
package com.jeeplus.modules.business.chuku.lingliao.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.chuku.lingliao.entity.BusinessChuKuLingLiao;

/**
 * 领料单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessChuKuLingLiaoMapper extends BaseMapper<BusinessChuKuLingLiao> {
	
}