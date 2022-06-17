package com.jeeplus.modules.u8data.invpostionsum.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.invpostionsum.entity.U8InvPostionSum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8InvPostionSumMapper extends BaseMapper<U8InvPostionSum> {
    @Select("select sum(iQuantity) from YT_API_ERP_view_InvPositionSum where cInvCode = #{cinvcode}")
    Double getSumNumByCinvcode(@Param("cinvcode") String cinvcode);
}
