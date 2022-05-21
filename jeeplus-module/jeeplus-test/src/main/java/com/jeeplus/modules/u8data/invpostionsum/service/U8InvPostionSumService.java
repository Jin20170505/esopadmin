package com.jeeplus.modules.u8data.invpostionsum.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.invpostionsum.entity.U8InvPostionSum;
import com.jeeplus.modules.u8data.invpostionsum.mapper.U8InvPostionSumMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8InvPostionSumService extends CrudService<U8InvPostionSumMapper, U8InvPostionSum> {

    public List<U8InvPostionSum> findList(U8InvPostionSum sum){
        return mapper.findList(sum);
    }

    public Page<U8InvPostionSum> findPage(Page<U8InvPostionSum> page, U8InvPostionSum sum){
        return super.findPage(page,sum);
    }
}
