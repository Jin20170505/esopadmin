package com.jeeplus.modules.api.xiancunliang;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.u8data.invpostionsum.entity.U8InvPostionSum;
import com.jeeplus.modules.u8data.invpostionsum.service.U8InvPostionSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/invpostionsum")
public class ApiInvPostionSumController {
    @Autowired
    private U8InvPostionSumService u8InvPostionSumService;

    @RequestMapping("find")
    public AjaxJson find(String cinvcode){
        AjaxJson json = new AjaxJson();
        try{
            U8InvPostionSum sum = new U8InvPostionSum();
            sum.setCinvcode(cinvcode);
            List<U8InvPostionSum> list =  u8InvPostionSumService.findList(sum);
            if(list==null){
                list = new ArrayList<>(0);
            }
            double sumnum = list.stream().mapToDouble(U8InvPostionSum::getIquantity).sum();
            json.put("sum",sumnum);
            json.put("list",list);
            json.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败，原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }
}
