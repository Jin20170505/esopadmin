package com.jeeplus.modules.u8data.employee.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.employee.entity.SysEmployee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysEmployeeMapper extends BaseMapper<SysEmployee> {

    void batchInsert(List<SysEmployee> list);
}
