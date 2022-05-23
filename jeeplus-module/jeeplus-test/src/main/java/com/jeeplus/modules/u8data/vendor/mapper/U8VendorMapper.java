package com.jeeplus.modules.u8data.vendor.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.vendor.entity.U8Vendor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8VendorMapper extends BaseMapper<U8Vendor> {

}
