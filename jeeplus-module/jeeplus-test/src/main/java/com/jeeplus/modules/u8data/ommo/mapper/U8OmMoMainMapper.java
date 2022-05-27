package com.jeeplus.modules.u8data.ommo.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.ommo.entity.U8OmMoMain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8OmMoMainMapper extends BaseMapper<U8OmMoMain> {
}
