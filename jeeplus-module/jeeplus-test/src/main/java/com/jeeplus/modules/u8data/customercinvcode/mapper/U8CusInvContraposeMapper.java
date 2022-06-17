package com.jeeplus.modules.u8data.customercinvcode.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.customercinvcode.entity.U8CusInvContrapose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8CusInvContraposeMapper extends BaseMapper<U8CusInvContrapose> {

    @Select("select top(1) cCusInvName from YT_API_ERP_view_CusInvContrapose where cCusInvCode = #{customercinvcode}")
    String getCusCinvName(@Param("customercinvcode") String customercinvcode);
}
