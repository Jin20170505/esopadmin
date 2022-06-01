package com.jeeplus.modules.api.ruku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouch;
import com.jeeplus.modules.business.arrivalvouch.entity.BusinessArrivalVouchMx;
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

    @RequestMapping("getMxDetail")
    public AjaxJson getMxDetail(String cgid,String cinvcode,String batchno,Double num){
        AjaxJson json = new AjaxJson();
        try{
            BusinessArrivalVouchMx mx = arrivalVouchService.getMxByCinvcodeAndBatchno(cgid,cinvcode,batchno);
            if(mx==null|| StringUtils.isEmpty(mx.getId())){
                json.setMsg("此存货不在该采购单中。");
                json.setSuccess(false);
                return json;
            }
            Double rukuSum = businessRukuCaiGouService.getRukuNum(mx.getId());
            if(rukuSum!=null){
                double yl = mx.getNum()  - rukuSum ;
                if(yl<0){
                    json.setMsg("入库数量超出，不可出库");
                    json.setSuccess(false);
                    return json;
                }
                if(yl<num){
                    json.setMsg("此物品还可以入的数量为："+yl+"，此条码数量超出");
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
    public AjaxJson doRuKu(String cgid,String cgcode,String ckid,String hwid,String userid,String mxJson){
        AjaxJson json = new AjaxJson();
        try {
            businessRukuCaiGouService.doRuKu(cgid,cgcode,ckid,hwid,userid,mxJson);
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
