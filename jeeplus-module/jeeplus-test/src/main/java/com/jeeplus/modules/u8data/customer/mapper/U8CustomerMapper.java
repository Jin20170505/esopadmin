package com.jeeplus.modules.u8data.customer.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.customer.entity.U8Customer;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8CustomerMapper extends BaseMapper<U8Customer> {
}
