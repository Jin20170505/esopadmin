/**
 * 
 */
package com.jeeplus.modules.business.dispatch.mapper;

import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;

import java.util.List;

/**
 * 销售发货单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessDispatchMapper extends BaseMapper<BusinessDispatch> {
    @Select("select 1 from business_dispatch where id = #{id}")
    Integer hasById(@Param("id") String id);

    @Update("update business_dispatch set printstatus='已打印' where id = #{id}")
    void print(@Param("id") String id);

    @Select("select max(code) from business_dispatch where code like concat('XSFH',#{ymd},'%')")
    String getMaxCode(@Param("ymd") String ymd);

    void batchInsert(List<BusinessDispatch> list);
}