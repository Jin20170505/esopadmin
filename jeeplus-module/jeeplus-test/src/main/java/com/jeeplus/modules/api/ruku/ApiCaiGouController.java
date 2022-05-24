package com.jeeplus.modules.api.ruku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.service.BusinessArrivalVouchService;
import com.jeeplus.modules.business.ruku.caigou.service.BusinessRukuCaiGouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 采购入库
 */
@RestController
@RequestMapping("api/ruku/caigou")
public class ApiCaiGouController {
    @Autowired
    private BusinessArrivalVouchService arrivalVouchService;
    @Autowired
    private BusinessRukuCaiGouService businessRukuCaiGouService;
    @RequestMapping("findNodoList")
    public AjaxJson findNodoList(String cgid){
        AjaxJson json = new AjaxJson();
        try {
            List<String> nodocghids = arrivalVouchService.findNoDoList(cgid);
            json.put("info",nodocghids);
            json.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败，原因："+e.getMessage());
        }
        return json;
    }

    @RequestMapping("doRuKu")
    public AjaxJson doRuKu(String cgid,String hwid,String mxJson){
        AjaxJson json = new AjaxJson();
        try {
            businessRukuCaiGouService.doRuKu(cgid,hwid,mxJson);
            json.setSuccess(true);
            json.setMsg("入库成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("入库失败，原因："+e.getMessage());
        }
        return json;
    }
}
