/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.product.archive.mapper;

import com.jeeplus.modules.api.bean.ApiFileViewBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.product.archive.entity.BusinessRoute;

import java.util.List;

/**
 * 工艺路线MAPPER接口
 * @author Jin
 * @version 2022-04-27
 */
@Mapper
@Repository
public interface BusinessRouteMapper extends BaseMapper<BusinessRoute> {

    List<ApiFileViewBean> findFileBySite(@Param("siteid") String siteid, @Param("filename") String filename, @Param("from") int from,@Param("size") int size);

    int countFileBySite(@Param("siteid") String siteid, @Param("filename") String filename);
}