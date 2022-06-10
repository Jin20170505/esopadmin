/**
 * 
 */
package com.jeeplus.modules.business.faliao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.faliao.entity.BusinessFaLiao;

/**
 * 调拨单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessFaLiaoMapper extends BaseMapper<BusinessFaLiao> {
    @Select("select max(code) from business_faliao where code like concat('FLD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);
}