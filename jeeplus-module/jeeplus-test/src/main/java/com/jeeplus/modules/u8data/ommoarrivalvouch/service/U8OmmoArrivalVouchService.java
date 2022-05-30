package com.jeeplus.modules.u8data.ommoarrivalvouch.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.ommoarrivalvouch.entity.U8OmmoArrivalVouch;
import com.jeeplus.modules.u8data.ommoarrivalvouch.mapper.U8OmmoArrivalVouchMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8OmmoArrivalVouchService extends CrudService<U8OmmoArrivalVouchMapper, U8OmmoArrivalVouch> {


    public List<U8OmmoArrivalVouch> findList(U8OmmoArrivalVouch vouch){
        return mapper.findList(vouch);
    }
}
