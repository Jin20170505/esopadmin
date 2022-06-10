/**
 * 
 */
package com.jeeplus.modules.business.pandian.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.pandian.entity.BusinessPanDian;

/**
 * 盘点单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessPanDianMapper extends BaseMapper<BusinessPanDian> {
    @Select("select max(code) from business_pandian where code like concat('PDD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);
}