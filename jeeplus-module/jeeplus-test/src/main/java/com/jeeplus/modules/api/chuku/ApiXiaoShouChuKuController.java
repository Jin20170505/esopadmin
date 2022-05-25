package com.jeeplus.modules.api.chuku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.chuku.dispatch.service.BusinessChukuDispatchService;
import com.jeeplus.modules.business.dispatch.entity.BusinessDispatch;
import com.jeeplus.modules.business.dispatch.service.BusinessDispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/chuku/xiaoshou")
public class ApiXiaoShouChuKuController {
    @Autowired
    private BusinessDispatchService dispatchService;
    @Autowired
    private BusinessChukuDispatchService chukuDispatchService;
    @RequestMapping("getInfo")
    public AjaxJson getInfo(String xsfhid){
        AjaxJson json  = new AjaxJson();
        try{
            json.put("info",dispatchService.getInfo(xsfhid));
            json.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败,原因："+e.getMessage());
        }
        return json;
    }

    @RequestMapping("do")
    public AjaxJson dochuku(String xsfhid,String userid){
        AjaxJson json  = new AjaxJson();
        try{
            BusinessDispatch dispatch = dispatchService.get(xsfhid);
            chukuDispatchService.chuku(dispatch,userid);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("出库失败,原因："+e.getMessage());
        }
        return json;
    }


}
