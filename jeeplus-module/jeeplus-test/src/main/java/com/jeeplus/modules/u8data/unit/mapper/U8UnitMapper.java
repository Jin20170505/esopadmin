package com.jeeplus.modules.u8data.unit.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.unit.entity.U8Unit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8UnitMapper extends BaseMapper<U8Unit> {

}
