package com.jeeplus.modules.u8data.arrivalvouch.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.arrivalvouch.entity.U8ArrivalVouch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8ArrivalVouchMapper extends BaseMapper<U8ArrivalVouch> {
}
