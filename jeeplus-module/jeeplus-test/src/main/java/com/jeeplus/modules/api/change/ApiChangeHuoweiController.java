package com.jeeplus.modules.api.change;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.huowei.change.service.BusinessHuoWeiChangeService;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/change/huowei")
public class ApiChangeHuoweiController {
    @Autowired
    private BusinessHuoWeiChangeService businessHuoWeiChangeService;
    @RequestMapping("do")
    public AjaxJson huoweichange(String ckid,String userid,String mxJson){
        AjaxJson json  = new AjaxJson();
        try{
            businessHuoWeiChangeService.change(ckid,userid,mxJson);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("出库失败,原因："+e.getMessage());
        }
        return json;
    }
}
