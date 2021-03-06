/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.ommo.bom.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoYongItem;

import java.util.List;

/**
 * 委外用料明细MAPPER接口
 * @author Jin
 * @version 2022-05-28
 */
@Mapper
@Repository
public interface BussinessOmMoYongItemMapper extends BaseMapper<BussinessOmMoYongItem> {

    void batchInsert(List<BussinessOmMoYongItem> list);

    @Delete("delete from business_om_moyongliao_item where ommohid = #{omhid}")
    void deleteByOmHid(@Param("omhid") String omhid);

    List<BussinessOmMoYongItem> findYongLiaoItemsByWwhid(@Param("wwhid") String wwhid);
}