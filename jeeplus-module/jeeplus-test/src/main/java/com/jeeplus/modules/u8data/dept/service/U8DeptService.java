package com.jeeplus.modules.u8data.dept.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.dept.entity.U8Dept;
import com.jeeplus.modules.u8data.dept.mapper.U8DeptMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8DeptService extends CrudService<U8DeptMapper, U8Dept> {

    public List<U8Dept> findList(U8Dept dept){
        return mapper.findList(dept);
    }

}
