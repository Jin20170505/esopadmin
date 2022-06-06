/**
 *
 */
package com.jeeplus.modules.business.shengchan.paichan.dept.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.business.shengchan.paichan.dept.entity.BusinessShengChanPaiChanDept;

/**
 * 排产部门MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BusinessShengChanPaiChanDeptMapper extends BaseMapper<BusinessShengChanPaiChanDept> {

    @Select("select 1 from business_shengchan_paichan_dept where dept = #{dept} limit 1")
    Integer hasPaichanDept(@Param("dept") String dept);

}