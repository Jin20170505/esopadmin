package com.jeeplus.modules.u8data.morder.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import com.jeeplus.modules.u8data.morder.mapper.U8MorderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8MorderService extends CrudService<U8MorderMapper, U8Morder> {

    public List<U8Morder> findList(U8Morder morder){
        return mapper.findList(morder);
    }

    public U8Morder getMainInfo(String moId) {
        return mapper.getMainInfo(moId);
    }


    public Double getSumNum(String sccode,String scline){
        return mapper.getSumNum(sccode, scline);
    }

    public Double getRkNum(String sccode,String scline){
        return mapper.getRkNum(sccode, scline);
    }

}
