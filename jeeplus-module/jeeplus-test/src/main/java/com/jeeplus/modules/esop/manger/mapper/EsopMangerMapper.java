/**
 * 
 */
package com.jeeplus.modules.esop.manger.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.esop.manger.entity.EsopManger;

/**
 * ESOP工单MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface EsopMangerMapper extends BaseMapper<EsopManger> {

    @Update("update esop_manger set status = #{status} where id = #{id}")
    void updateStatus(@Param("id") String id,@Param("status") String status);
}