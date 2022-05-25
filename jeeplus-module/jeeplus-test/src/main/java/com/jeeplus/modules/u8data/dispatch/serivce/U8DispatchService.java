package com.jeeplus.modules.u8data.dispatch.serivce;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.dispatch.entity.U8Dispatch;
import com.jeeplus.modules.u8data.dispatch.mapper.U8DispatchMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8DispatchService extends CrudService<U8DispatchMapper, U8Dispatch> {

    public List<U8Dispatch> findList(U8Dispatch dispatch){
        return mapper.findList(dispatch);
    }
}
