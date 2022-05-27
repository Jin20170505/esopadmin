package com.jeeplus.modules.u8data.ommo.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.ommo.entity.U8MOMaterials;
import com.jeeplus.modules.u8data.ommo.mapper.U8MOMaterialsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8MOMaterialsService extends CrudService<U8MOMaterialsMapper, U8MOMaterials> {

    public List<U8MOMaterials> findList(U8MOMaterials u8MOMaterials){
        return mapper.findList(u8MOMaterials);
    }
}
