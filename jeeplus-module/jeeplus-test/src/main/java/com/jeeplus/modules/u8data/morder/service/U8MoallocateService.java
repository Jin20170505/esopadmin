package com.jeeplus.modules.u8data.morder.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import com.jeeplus.modules.u8data.morder.mapper.U8MoallocateMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 生产子件
 */
@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8MoallocateService  extends CrudService<U8MoallocateMapper, U8Moallocate> {

   public List<U8Moallocate> findList(U8Moallocate moallocate){
        return mapper.findList(moallocate);
    }
}
