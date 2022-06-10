/**
 * 
 */
package com.jeeplus.modules.business.arrivalvouch.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;

import java.util.List;

/**
 * 采购到货单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessArrivalVouchMapper extends BaseMapper<BusinessArrivalVouch> {
    @Select("select 1 from business_arrivalvouch where id = #{id}")
    Integer hasById(@Param("id") String id);

    @Update("update business_arrivalvouch set printstatus = '已打印' where id = #{id}")
    void print(@Param("id") String id);

    void batchInsert(List<BusinessArrivalVouch> list);

    @Select("select max(code) from business_arrivalvouch where code like concat('CGDH',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

}