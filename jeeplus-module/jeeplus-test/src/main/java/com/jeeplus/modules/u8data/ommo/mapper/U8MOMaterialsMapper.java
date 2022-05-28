package com.jeeplus.modules.u8data.ommo.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.ommo.entity.U8MOMaterials;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface U8MOMaterialsMapper extends BaseMapper<U8MOMaterials> {

    List<U8MOMaterials> findByWid(@Param("wid") String wid);
}
