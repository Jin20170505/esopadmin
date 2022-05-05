/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.esop.manger.mapper;

import com.jeeplus.modules.api.bean.ApiFileViewBean;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.esop.manger.entity.EsopMangerSub;

import java.util.List;

/**
 * 路线MAPPER接口
 * @author Jin
 * @version 2022-05-04
 */
@Mapper
@Repository
public interface EsopMangerSubMapper extends BaseMapper<EsopMangerSub> {

    List<ApiFileViewBean> findFile(@Param("productid") String productid, @Param("site")String site, @Param("name") String name, int from, int size);

    int countFile(@Param("productid") String productid,@Param("site") String site,@Param("name") String name);

    @Select("select file_url from esop_manger_sub where id = #{id}")
    String getFileUrl(@Param("id") String id);
}