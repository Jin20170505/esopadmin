/**
 *
 */
package com.jeeplus.modules.business.filemanger.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.filemanger.entity.BusinessFileType;

/**
 * 文件管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessFileTypeMapper extends TreeMapper<BusinessFileType> {
	
}