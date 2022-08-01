package com.jeeplus.modules.u8data.dept.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.dept.entity.U8Dept;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8DeptMapper extends BaseMapper<U8Dept> {
}
