package com.jeeplus.modules.u8data.ommo.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.ommo.entity.U8OmMoMain;
import com.jeeplus.modules.u8data.ommo.mapper.U8OmMoMainMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8MoMoMainService extends CrudService<U8OmMoMainMapper, U8OmMoMain> {

    public List<U8OmMoMain> findList(U8OmMoMain u8OmMoMain){
        return mapper.findList(u8OmMoMain);
    }

}
