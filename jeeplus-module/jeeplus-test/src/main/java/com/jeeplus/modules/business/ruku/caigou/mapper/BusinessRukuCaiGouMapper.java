/**
 * 
 */
package com.jeeplus.modules.business.ruku.caigou.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ruku.caigou.entity.BusinessRukuCaiGou;

/**
 * 采购入库MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessRukuCaiGouMapper extends BaseMapper<BusinessRukuCaiGou> {
	
}