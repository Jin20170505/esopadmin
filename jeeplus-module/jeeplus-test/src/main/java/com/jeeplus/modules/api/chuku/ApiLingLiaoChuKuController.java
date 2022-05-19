package com.jeeplus.modules.api.chuku;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.api.bean.chuku.LingLiaoBean;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.chuku.lingliao.service.BusinessChuKuLingLiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 材料出库单 出库
 */
@RestController
@RequestMapping("api/chuku/lingliao")
public class ApiLingLiaoChuKuController {

    @Autowired
    private BusinessBaoGongOrderService businessBaoGongOrderService;
    @Autowired
    private BusinessChuKuLingLiaoService businessChuKuLingLiaoService;
    @RequestMapping("findLingLiaoDanInfo")
    public AjaxJson findLingLiaoDanInfo(String bgcode){
        AjaxJson json = new AjaxJson();
        try {
            LingLiaoBean info = businessBaoGongOrderService.getLingLiaoInfo(bgcode);
            json.put("info",info);
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
    public AjaxJson lingliaochuku(String bgid,String bgcode,String sccode,String scline,String plancode,String planid,
                                  String cinvcode,String cinvname,String cinvstd,String unit,Double num,
                                  String ckid,String remarks,String userid, String mxJson){
        AjaxJson json = new AjaxJson();
        try{
            businessChuKuLingLiaoService.lingliao(bgid,bgcode,sccode,scline,plancode, planid,
                    cinvcode,cinvname,cinvstd,unit,num,ckid,remarks, userid,mxJson);
            json.setSuccess(true);
            json.setMsg("材料出库成功。");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("材料出库失败.原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }
}
