package com.jeeplus.modules.u8data.dispatch.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.dispatch.entity.U8Dispatch;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8DispatchMapper extends BaseMapper<U8Dispatch> {
}
