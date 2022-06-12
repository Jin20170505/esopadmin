/**
 * 
 */
package com.jeeplus.modules.business.ruku.caigou.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
    @Select("select max(code) from business_ruku_caigou where code like concat('CGRK',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    @Select("select 1 from business_ruku_caigou where arrival_code = #{ghdcode} limit 1")
    Integer hasDhdCode(@Param("ghdcode") String ghdcode);
}