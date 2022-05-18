package com.jeeplus.modules.u8data.inventory.mapper;

import com.jeeplus.core.persistence.BaseMapper;
import com.jeeplus.modules.u8data.inventory.entity.U8Inventory;
import com.jeeplus.modules.u8data.inventory.entity.U8InventoryClass;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface U8InventoryMapper extends BaseMapper<U8Inventory> {

    List<U8InventoryClass> findTypes();
}
