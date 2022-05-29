package com.jeeplus.modules.api.pandian;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.pandian.service.BusinessPanDianService;
import com.jeeplus.modules.u8data.invpostionsum.entity.U8InvPostionSum;
import com.jeeplus.modules.u8data.invpostionsum.service.U8InvPostionSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/pandian")
public class ApiPanDianController {
    @Autowired
    private U8InvPostionSumService u8InvPostionSumService;
    @Autowired
    private BusinessPanDianService panDianService;
    @RequestMapping("getInfo")
    public AjaxJson getInfo(String hwcode,String cinvcode){
        AjaxJson json = new AjaxJson();
        try {
            U8InvPostionSum sum = new U8InvPostionSum();
            sum.setCinvcode(cinvcode);
            sum.setcPosCode(hwcode);
            List<U8InvPostionSum> list =  u8InvPostionSumService.findList(sum);
            if(list==null||list.isEmpty()){
                json.setSuccess(false);
                json.setMsg("未查询到数据。");
                return json;
            }
            json.put("list",list);
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败");
            json.setSuccess(false);
        }
        return json;
    }

    @RequestMapping("doPandian")
    public AjaxJson doPandian(String userid,String ck,String hw,String mxJson){
        AjaxJson json = new AjaxJson();
        try {
            panDianService.doPandian(userid,ck,hw,mxJson);
            json.setSuccess(true);
            json.setMsg("盘点成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("盘点失败，原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }
}
