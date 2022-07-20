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
    public AjaxJson getMxDetail(String cgid,String cinvcode,String batchno,String scdate,Double num){
        AjaxJson json = new AjaxJson();
        try{
            cinvcode = cinvcode.trim();
            batchno = batchno.trim();
            List<BusinessArrivalVouchMx> mxList = arrivalVouchService.findMxByCinvcodeAndBatchno(cgid,cinvcode,batchno,scdate);
            if(mxList==null){
                json.setMsg("此存货不在该采购单中。");
                json.setSuccess(false);
                return json;
            }
            if(mxList.isEmpty()){
                json.setMsg("此存货已经入库。");
                json.setSuccess(false);
                return json;
            }
            Double yl = mxList.stream().mapToDouble(BusinessArrivalVouchMx::getNum).sum();
            json.put("yl",yl);
            json.setSuccess(true);
            json.put("info",mxList);
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
