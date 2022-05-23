package com.jeeplus.modules.u8data.customer.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.customer.entity.U8Customer;
import com.jeeplus.modules.u8data.customer.mapper.U8CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8CustomerService extends CrudService<U8CustomerMapper, U8Customer> {

    public List<U8Customer> findList(U8Customer customer){
        return mapper.findList(customer);
    }
}
