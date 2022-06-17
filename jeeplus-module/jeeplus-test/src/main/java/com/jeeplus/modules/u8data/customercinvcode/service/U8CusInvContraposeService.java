package com.jeeplus.modules.u8data.customercinvcode.service;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.customercinvcode.entity.U8CusInvContrapose;
import com.jeeplus.modules.u8data.customercinvcode.mapper.U8CusInvContraposeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8CusInvContraposeService extends CrudService<U8CusInvContraposeMapper, U8CusInvContrapose> {

    public List<U8CusInvContrapose> findList(U8CusInvContrapose u8CusInvContrapose){
        return mapper.findList(u8CusInvContrapose);
    }

    public Page<U8CusInvContrapose> findPage(Page<U8CusInvContrapose> page, U8CusInvContrapose u8CusInvContrapose){
        return super.findPage(page,u8CusInvContrapose);
    }

    public String getCusCinvName(String customercinvcode){
        return mapper.getCusCinvName(customercinvcode);
    }
}
