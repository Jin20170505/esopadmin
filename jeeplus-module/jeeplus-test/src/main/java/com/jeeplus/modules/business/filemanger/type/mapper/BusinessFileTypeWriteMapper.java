/**
 * 
 */
package com.jeeplus.modules.business.filemanger.type.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.filemanger.type.entity.BusinessFileTypeWrite;

/**
 * 文件类型MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessFileTypeWriteMapper extends TreeMapper<BusinessFileTypeWrite> {
	
}