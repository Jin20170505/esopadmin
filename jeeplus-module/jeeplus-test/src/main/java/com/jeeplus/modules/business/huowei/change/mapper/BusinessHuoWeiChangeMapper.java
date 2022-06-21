/**
 * 
 */
package com.jeeplus.modules.business.huowei.change.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.huowei.change.entity.BusinessHuoWeiChange;

/**
 * 货位调整MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessHuoWeiChangeMapper extends BaseMapper<BusinessHuoWeiChange> {
    @Select("select max(code) from business_huowei_change where code like concat('HWTZ',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);
}