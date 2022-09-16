package com.jeeplus.modules.u8data.employee.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.employee.entity.U8Employee;
import com.jeeplus.modules.u8data.employee.mapper.U8EmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8EmployeeService extends CrudService<U8EmployeeMapper,U8Employee> {

    public List<U8Employee> findList(U8Employee employee){
        return mapper.findList(employee);
    }
}
