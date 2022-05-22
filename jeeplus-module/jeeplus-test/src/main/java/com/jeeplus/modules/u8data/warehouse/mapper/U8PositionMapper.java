package com.jeeplus.modules.u8data.warehouse.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.warehouse.entity.U8Position;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8PositionMapper extends BaseMapper<U8Position> {
}
