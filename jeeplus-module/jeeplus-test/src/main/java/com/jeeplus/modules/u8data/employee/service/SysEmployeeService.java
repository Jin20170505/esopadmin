package com.jeeplus.modules.u8data.employee.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.sys.service.SystemService;
import com.jeeplus.modules.u8data.employee.entity.SysEmployee;
import com.jeeplus.modules.u8data.employee.entity.U8Employee;
import com.jeeplus.modules.u8data.employee.mapper.SysEmployeeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SysEmployeeService extends CrudService<SysEmployeeMapper, SysEmployee> {

    @Transactional(readOnly = false)
    public void sychu8(List<U8Employee> list){
        List<SysEmployee> data = new ArrayList<>(list.size());
        list.forEach(d->{
            SysEmployee bean = new SysEmployee();
            bean.setId(d.getCode());
            bean.setPhone(d.getPhone()).setEmail(d.getEmail()).setDeptid(d.getDeptid()).setPassword(SystemService.entryptPassword("123456"));
            bean.setCode(d.getCode()).setName(d.getName());
            data.add(bean);
        });
        saveU8Data(data);
    }
    @Transactional(readOnly = false)
    public void saveU8Data(List<SysEmployee> sysDeptBeans){
        if(!sysDeptBeans.isEmpty()){
            int i = 0;
            int j = 0;
            int mlen = sysDeptBeans.size();
            while (i<mlen){
                j = i;
                i = i+300;
                if(i>=mlen){
                    mapper.batchInsert(sysDeptBeans.subList(j,mlen));
                }else {
                    mapper.batchInsert(sysDeptBeans.subList(j,i));
                }
            }
        }
    }
}
