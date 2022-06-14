package com.jeeplus.modules.u8data.morder.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8MorderMapper extends BaseMapper<U8Morder> {

    U8Morder getMainInfo(@Param("moId") String moId);
}
