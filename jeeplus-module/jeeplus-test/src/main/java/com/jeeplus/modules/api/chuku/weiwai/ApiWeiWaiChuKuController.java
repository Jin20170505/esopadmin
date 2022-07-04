package com.jeeplus.modules.api.chuku.weiwai;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.chuku.ommo.service.BusinessChuKuWeiWaiService;
import com.jeeplus.modules.business.ommo.bom.entity.BussinessOmMoDetailOnly;
import com.jeeplus.modules.business.ommo.bom.service.BussinessOmMoDetailOnlyService;
import com.jeeplus.modules.u8infacerecord.service.U8InfaceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 委外材料出库
 */
@RestController
@RequestMapping("api/chuku/weiwai/")
public class ApiWeiWaiChuKuController {
    @Autowired
    private BussinessOmMoDetailOnlyService omMoDetailOnlyService;
    @Autowired
    private BusinessChuKuWeiWaiService businessChuKuWeiWaiService;
    @RequestMapping("getInfo")
    public AjaxJson getInfo(String wwhid){
        AjaxJson json  = new AjaxJson();
        try{
            if(businessChuKuWeiWaiService.hasByWwHid(wwhid)){
                json.setSuccess(false);
                json.setMsg("此单已发料：");
                return json;
            }
            BussinessOmMoDetailOnly info = omMoDetailOnlyService.get(wwhid);
            json.put("info",info);
            json.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败,原因："+e.getMessage());
        }
        return json;
    }

    @RequestMapping("do")
    public AjaxJson dochuku(String wwid,String wwhid,String ckid,String userid,String mxJson){
        AjaxJson json  = new AjaxJson();
        try{
            BussinessOmMoDetailOnly info = omMoDetailOnlyService.get(wwhid);
            businessChuKuWeiWaiService.weiwaichuku(wwid,wwhid,ckid,info,userid,mxJson);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("出库失败,原因："+e.getMessage());
        }
        return json;
    }
}
