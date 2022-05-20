package com.jeeplus.modules.u8data.operation.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.operation.entity.U8Operation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface U8OperationMapper extends BaseMapper<U8Operation> {
}
