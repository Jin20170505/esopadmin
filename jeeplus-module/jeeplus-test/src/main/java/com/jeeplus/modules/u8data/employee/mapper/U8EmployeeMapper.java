package com.jeeplus.modules.u8data.employee.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.employee.entity.U8Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface U8EmployeeMapper extends BaseMapper<U8Employee> {

}
