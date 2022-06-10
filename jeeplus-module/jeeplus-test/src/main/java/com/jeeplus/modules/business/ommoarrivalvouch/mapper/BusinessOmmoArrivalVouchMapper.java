/**
 * 
 */
package com.jeeplus.modules.business.ommoarrivalvouch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalVouch;

import java.util.List;

/**
 * 委外到货单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessOmmoArrivalVouchMapper extends BaseMapper<BusinessOmmoArrivalVouch> {

    @Update("update business_ommo_arrivalvouch set printstatus = '已打印' where id = #{id}")
    void print(@Param("id") String id);

    void batchInsert(List<BusinessOmmoArrivalVouch> list);

    @Select("select max(code) from business_ommo_arrivalvouch where code like concat('WWDH',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);
}