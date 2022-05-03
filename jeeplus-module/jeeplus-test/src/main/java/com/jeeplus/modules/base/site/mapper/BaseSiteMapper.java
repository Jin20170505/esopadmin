/**
 *
 */
package com.jeeplus.modules.base.site.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.site.entity.BaseSite;

import java.util.List;

/**
 * 工作站管理MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseSiteMapper extends BaseMapper<BaseSite> {

    @Select("select id,name from base_site where del_flag = '0'")
    List<BaseSite> findAllSites();
}