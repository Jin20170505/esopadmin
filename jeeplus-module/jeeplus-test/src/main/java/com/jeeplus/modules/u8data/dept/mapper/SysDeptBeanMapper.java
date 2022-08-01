package com.jeeplus.modules.u8data.dept.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.dept.entity.SysDeptBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysDeptBeanMapper extends BaseMapper<SysDeptBean> {

    void batchInsert(List<SysDeptBean> list);
}
