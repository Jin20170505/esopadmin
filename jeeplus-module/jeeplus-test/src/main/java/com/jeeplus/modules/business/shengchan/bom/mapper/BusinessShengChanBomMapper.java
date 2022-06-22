/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.business.shengchan.bom.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.bom.entity.BusinessShengChanBom;

import java.util.List;

/**
 * 生产订单子件MAPPER接口
 * @author Jin
 * @version 2022-05-13
 */
@Mapper
@Repository
public interface BusinessShengChanBomMapper extends BaseMapper<BusinessShengChanBom> {

    @Delete("delete from business_shengchan_bom where schid = #{schid}")
    void deleteBySchid(@Param("schid") String schid);

    BusinessShengChanBom getBomPaiChan(@Param("schid") String schid);

    @Select("select id from business_shengchan_bom where schid = #{schid}")
    List<String> findBomIds(@Param("schid") String schid);

    @Delete("delete from business_shengchan_bom where id = #{id}")
    void deleteById(@Param("id") String id);
}