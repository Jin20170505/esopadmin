package com.jeeplus.modules.u8data.dept.service;

import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.base.route.entity.BaseRoteMain;
import com.jeeplus.modules.base.route.entity.BaseRoute;
import com.jeeplus.modules.sys.utils.UserUtils;
import com.jeeplus.modules.u8data.dept.entity.SysDeptBean;
import com.jeeplus.modules.u8data.dept.entity.U8Dept;
import com.jeeplus.modules.u8data.dept.mapper.SysDeptBeanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SysDeptBeanService extends CrudService<SysDeptBeanMapper, SysDeptBean> {

    @Transactional(readOnly = false)
    public void sychu8(List<U8Dept> list){
        List<SysDeptBean> data = new ArrayList<>(list.size());
        SysDeptBean company = new SysDeptBean();
        company.setId("1");
        company.setCode("00").setName("公司").setType("1").setGrade("1");
        company.setParentid("0").setParentids("0,").setSort(0);
        data.add(company);
        list.forEach(d->{
            SysDeptBean bean = new SysDeptBean();
            bean.setId(d.getCode());
            bean.setCode(d.getCode()).setName(d.getName()).setRemarks(d.getMemo());
            bean.setParentid(getPid(d.getCode())).setParentids(getPids(d.getCode())).setSort(d.getNo()).setType("2").setGrade("2");
            data.add(bean);
        });
        saveU8Data(data);
        UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
    }

    private String getPid(String code){
        if(code.length()==2){
            return "1";
        }else {
            return code.substring(0,code.length()-2);
        }
    }

    private String getPids(String code){
        if(code.length()==2){
            return "0,1,";
        }else {
            StringBuilder sb = new StringBuilder("0,1,");
            code = code.substring(0,code.length()-2);
            int len = code.length();
            while (len>0){
                sb.append(code.substring(len-2,len)).append(",");
                len = len -2;
            }
            return sb.toString();
        }
    }

    @Transactional(readOnly = false)
    public void saveU8Data(List<SysDeptBean> sysDeptBeans){
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
