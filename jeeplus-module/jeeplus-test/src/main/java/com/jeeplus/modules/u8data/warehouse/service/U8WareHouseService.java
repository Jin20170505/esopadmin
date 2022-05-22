package com.jeeplus.modules.u8data.warehouse.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.warehouse.entity.U8WareHouse;
import com.jeeplus.modules.u8data.warehouse.mapper.U8WareHouseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8WareHouseService extends CrudService<U8WareHouseMapper, U8WareHouse> {

    public List<U8WareHouse> findList(U8WareHouse wareHouse){
        return mapper.findList(wareHouse);
    }
}
