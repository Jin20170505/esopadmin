package com.jeeplus.modules.u8data.warehouse.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.warehouse.entity.U8Position;
import com.jeeplus.modules.u8data.warehouse.mapper.U8PositionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8PositionService extends CrudService<U8PositionMapper, U8Position> {

    public List<U8Position> findList(U8Position position){
        return mapper.findList(position);
    }
}
