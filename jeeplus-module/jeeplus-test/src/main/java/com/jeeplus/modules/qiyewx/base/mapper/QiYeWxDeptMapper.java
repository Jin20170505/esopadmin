/**
 *
 */
package com.jeeplus.modules.qiyewx.base.mapper;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.TreeMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.qiyewx.base.entity.QiYeWxDept;

import java.util.List;

/**
 * 部门员工MAPPER接口
 * @author Jin
 * @version 2021-08-25
 */
@Mapper
@Repository
public interface QiYeWxDeptMapper extends TreeMapper<QiYeWxDept> {

    @Delete("delete from qiyewx_dept")
    void deleteAll();

    void batchInsert(List<QiYeWxDept> list);
}