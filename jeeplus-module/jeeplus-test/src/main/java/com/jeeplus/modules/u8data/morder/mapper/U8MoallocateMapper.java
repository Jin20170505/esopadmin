package com.jeeplus.modules.u8data.morder.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface U8MoallocateMapper extends BaseMapper<U8Moallocate> {

    List<U8Moallocate> findBomIdAndSyNum(@Param("moDId") String moDId);
}
