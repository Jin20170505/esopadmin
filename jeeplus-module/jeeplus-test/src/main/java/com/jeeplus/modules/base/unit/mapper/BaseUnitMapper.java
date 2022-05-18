/**
 *
 */
package com.jeeplus.modules.base.unit.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.jeeplus.core.persistence.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.jeeplus.modules.base.unit.entity.BaseUnit;

/**
 * 计量单位MAPPER接口
 * @author Jin
 */
@Mapper
@Repository
public interface BaseUnitMapper extends BaseMapper<BaseUnit> {
	@Select("select 1 from base_unit where code = #{code} limit 1")
    Integer hasByCode(@Param("code") String code);
}