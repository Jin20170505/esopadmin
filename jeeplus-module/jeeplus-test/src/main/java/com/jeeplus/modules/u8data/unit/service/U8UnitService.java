package com.jeeplus.modules.u8data.unit.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.unit.entity.U8Unit;
import com.jeeplus.modules.u8data.unit.mapper.U8UnitMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8UnitService extends CrudService<U8UnitMapper, U8Unit> {

    public List<U8Unit> findList(U8Unit unit){
        return mapper.findList(unit);
    }
}
