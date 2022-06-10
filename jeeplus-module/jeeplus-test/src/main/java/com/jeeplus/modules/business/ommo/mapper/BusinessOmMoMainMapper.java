/**
 * 
 */
package com.jeeplus.modules.business.ommo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommo.entity.BusinessOmMoMain;

import java.util.List;

/**
 * 委外订单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessOmMoMainMapper extends BaseMapper<BusinessOmMoMain> {

    void batchInsert(@Param("list") List<BusinessOmMoMain> list);

    @Select("select max(code) from business_om_momain where code like concat('WWDD',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);
}