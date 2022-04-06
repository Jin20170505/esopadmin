/**
 *
 */
package com.jeeplus.modules.qiyewx.base.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxEmployee;

import java.util.List;

/**
 * 企业微信_员工MAPPER接口
 * @author Jin
 * @version 2021-08-25
 */
@Mapper
@Repository
public interface QiYeWxEmployeeMapper extends BaseMapper<QiYeWxEmployee> {
    void batchInsert(List<QiYeWxEmployee> list);

    @Select("select id from qiyewx_employee where status = 1 ")
    List<String> findIdOfAll();

    String getIdByNameLast(@Param("name") String name,@Param("dept") String dept);

    @Update("update qiyewx_employee set status = #{status} where id = #{id}")
    void updateStatus(@Param("id") String id,@Param("status") Integer status);
}