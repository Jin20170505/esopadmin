package com.jeeplus.modules.u8data.vendor.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.u8data.vendor.entity.U8Vendor;
import com.jeeplus.modules.u8data.vendor.mapper.U8VendorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DS("u8")
@Service
@Transactional(readOnly = true)
public class U8VendorService extends CrudService<U8VendorMapper, U8Vendor> {

}
