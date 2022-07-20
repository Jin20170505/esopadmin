package com.jeeplus.modules.api.ruku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import com.jeeplus.modules.business.check.ipqc.mapper.BusinessCheckIPQCMapper;
import com.jeeplus.modules.business.ruku.product.service.BusinessRuKuProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 产品入库
 */
@RestController
@RequestMapping("api/ruku/product")
public class ApiProductController {

    @Autowired
    private BusinessBaoGongOrderService businessBaoGongOrderService;
    @Autowired
    private BusinessRuKuProductService businessRuKuProductService;
    @Autowired
    private BusinessCheckIPQCMapper businessCheckIPQCMapper;
    /**
     * 扫报工二维码 查询产品入库信息
     * @param bgcode 报工单号
     * @return
     */
    @RequestMapping("getProductRuKuInfo")
    public AjaxJson getProductRuKuInfo(String bgcode){
        AjaxJson json = new AjaxJson();
        try {
            json.put("info",businessBaoGongOrderService.getRuKuInfo(bgcode));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败,失败原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }

    @RequestMapping("do")
    public AjaxJson ruku(String bgid,String ckid,String hwid,String remarks,String userid,Double rukunum){
        AjaxJson json = new AjaxJson();
        try{
            businessRuKuProductService.ruku(bgid, ckid, hwid, remarks, userid, rukunum);
            json.setSuccess(true);
            json.setMsg("入库成功。");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("入库失败.原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }
}
