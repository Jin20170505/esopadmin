package com.jeeplus.modules.u8data.inventory.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.inventory.entity.U8Inventory;
import com.jeeplus.modules.u8data.inventory.entity.U8InventoryClass;
import com.jeeplus.modules.u8data.inventory.mapper.U8InventoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8InventoryService extends CrudService<U8InventoryMapper, U8Inventory> {

    public List<U8InventoryClass> findTypes(){
        return mapper.findTypes();
    }

    public List<U8Inventory> findList(U8Inventory inventory){
        return mapper.findList(inventory);
    }
}
