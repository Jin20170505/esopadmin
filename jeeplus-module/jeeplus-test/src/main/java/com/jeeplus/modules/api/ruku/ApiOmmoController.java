package com.jeeplus.modules.api.ruku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
import com.jeeplus.modules.business.arrivalvouch.service.BusinessArrivalVouchService;
import com.jeeplus.modules.business.ommoarrivalvouch.entity.BusinessOmmoArrivalvouchMx;
import com.jeeplus.modules.business.ommoarrivalvouch.service.BusinessOmmoArrivalVouchService;
import com.jeeplus.modules.business.ruku.caigou.service.BusinessRukuCaiGouService;
import com.jeeplus.modules.business.ruku.ommo.service.BusinessRukuOmmoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 入库·委外
 */
@RestController
@RequestMapping("api/ruku/ommo")
public class ApiOmmoController {
    @Autowired
    private BusinessOmmoArrivalVouchService businessOmmoArrivalVouchService;
    @Autowired
    private BusinessRukuOmmoService businessRukuOmmoService;

    @RequestMapping("getMxDetail")
    public AjaxJson getMxDetail(String wdid,String cinvcode,String batchno,String scdate, Double num){
        AjaxJson json = new AjaxJson();
        try{
            cinvcode = cinvcode.trim();
            batchno = batchno.trim();
            BusinessOmmoArrivalvouchMx mx = businessOmmoArrivalVouchService.getMxByPidAndCinvcode(wdid,cinvcode,batchno,scdate);
            if(mx==null){
                json.setMsg("该存货不属于此委外订单。");
                json.setSuccess(false);
                return json;
            }
            Double rukuSum = businessRukuOmmoService.getRukuNum(mx.getId());
            if(rukuSum!=null){
                double yl = mx.getNum()  - rukuSum ;
                if(yl<=0){
                    json.setMsg("入库数量超出，不可出库");
                    json.setSuccess(false);
                    return json;
                }
                json.put("yl",yl);
            }else {
                json.put("yl",mx.getNum());
            }
            json.setSuccess(true);
            json.put("info",mx);
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("查询失败，原因："+e.getMessage());
        }
        return json;
    }

    @RequestMapping("doRuKu")
    public AjaxJson doRuKu(String wdid,String wdcode,String ckid,String userid,String mxJson){
        AjaxJson json = new AjaxJson();
        try {
            businessRukuOmmoService.doRuKu(wdid,wdcode,ckid,userid,mxJson);
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
