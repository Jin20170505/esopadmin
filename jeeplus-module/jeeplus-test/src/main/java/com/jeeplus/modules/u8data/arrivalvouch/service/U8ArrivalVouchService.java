package com.jeeplus.modules.u8data.arrivalvouch.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.arrivalvouch.entity.U8ArrivalVouch;
import com.jeeplus.modules.u8data.arrivalvouch.mapper.U8ArrivalVouchMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8ArrivalVouchService extends CrudService<U8ArrivalVouchMapper, U8ArrivalVouch> {

    public List<U8ArrivalVouch> findList(U8ArrivalVouch arrivalVouch){
        return mapper.findList(arrivalVouch);
    }
}
