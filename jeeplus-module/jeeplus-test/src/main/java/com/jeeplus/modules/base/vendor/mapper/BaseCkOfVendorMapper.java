package com.jeeplus.modules.base.vendor.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.api.bean.ckandhw.CkBean;
import com.jeeplus.modules.base.vendor.entity.BaseCkOfVendor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BaseCkOfVendorMapper extends BaseMapper<BaseCkOfVendor> {

    List<CkBean> findCksByVendor(@Param("vendor") String vendor);
}
