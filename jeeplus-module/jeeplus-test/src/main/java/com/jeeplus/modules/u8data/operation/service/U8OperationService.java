package com.jeeplus.modules.u8data.operation.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.operation.entity.U8Operation;
import com.jeeplus.modules.u8data.operation.mapper.U8OperationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8OperationService extends CrudService<U8OperationMapper, U8Operation> {

    public List<U8Operation> findList(U8Operation u8Operation){
        return mapper.findList(u8Operation);
    }
}
