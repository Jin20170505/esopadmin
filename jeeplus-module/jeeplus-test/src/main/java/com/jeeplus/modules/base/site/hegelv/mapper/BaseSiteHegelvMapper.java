/**
 *
 */
package com.jeeplus.modules.base.site.hegelv.mapper;

import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.site.hegelv.entity.BaseSiteHegelv;

/**
 * 工序合格率MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseSiteHegelvMapper extends BaseMapper<BaseSiteHegelv> {
	
}