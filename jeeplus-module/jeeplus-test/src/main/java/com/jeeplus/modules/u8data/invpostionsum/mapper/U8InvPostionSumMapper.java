package com.jeeplus.modules.u8data.invpostionsum.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.invpostionsum.entity.U8InvPostionSum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8InvPostionSumMapper extends BaseMapper<U8InvPostionSum> {
}
