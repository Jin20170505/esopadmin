package com.jeeplus.modules.api.ruku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.api.bean.ruku.ProductRuKuBean;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.baogong.record.service.BusinessBaoGongRecordService;
import com.jeeplus.modules.business.check.ipqc.mapper.BusinessCheckIPQCMapper;
import com.jeeplus.modules.business.ruku.product.service.BusinessRuKuProductService;
import com.jeeplus.modules.u8data.morder.service.U8MorderService;
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
    @Autowired
    private U8MorderService u8MorderService;
    /**
     * 扫报工二维码 查询产品入库信息
     * @param bgcode 报工单号
     * @return
     */
    @RequestMapping("getProductRuKuInfo")
    public AjaxJson getProductRuKuInfo(String bgcode){
        AjaxJson json = new AjaxJson();
        try {
            ProductRuKuBean bean = businessBaoGongOrderService.getRuKuInfo(bgcode);
            String status = u8MorderService.getOrderStatusByCodeAndNo(bean.getSccode(),bean.getScline());
            if(!"3".equals(status)){
                json.setMsg("本单在ERP系统不是【审核】状态，不可操作");
                json.setSuccess(false);
                return json;
            }
            json.put("info",bean);
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
    public synchronized AjaxJson ruku(String bgid,String ckid,String hwid,String remarks,String userid,Double rukunum){
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
