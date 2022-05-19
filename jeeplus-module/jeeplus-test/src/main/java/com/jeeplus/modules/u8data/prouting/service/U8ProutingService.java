package com.jeeplus.modules.u8data.prouting.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.prouting.entity.U8Prouting;
import com.jeeplus.modules.u8data.prouting.entity.U8ProutingDetail;
import com.jeeplus.modules.u8data.prouting.mapper.U8ProutingDetailMapper;
import com.jeeplus.modules.u8data.prouting.mapper.U8ProutingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8ProutingService extends CrudService<U8ProutingMapper, U8Prouting> {
    @Autowired
    private U8ProutingDetailMapper u8ProutingDetailMapper;

    public List<U8Prouting> findList(U8Prouting u8Prouting){
        return mapper.findList(u8Prouting);
    }


    public List<U8ProutingDetail> findDetailList(U8ProutingDetail detail){
        return u8ProutingDetailMapper.findList(detail);
    }
}
