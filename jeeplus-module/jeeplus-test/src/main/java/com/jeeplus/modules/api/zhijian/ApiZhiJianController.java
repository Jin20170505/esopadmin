package com.jeeplus.modules.api.zhijian;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.baogong.order.service.BusinessBaoGongOrderService;
import com.jeeplus.modules.business.check.ipqc.service.BusinessCheckIPQCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/zhijian")
public class ApiZhiJianController {
    @Autowired
    private BusinessCheckIPQCService businessCheckIPQCService;
    @Autowired
    private BusinessBaoGongOrderService businessBaoGongOrderService;

    @RequestMapping("getZhiJianInfo")
    public AjaxJson getZhiJianInfo(String bgcode){
        AjaxJson json = new AjaxJson();
        try {
            json.put("info",businessBaoGongOrderService.getZhiJianInfo(bgcode));
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
    public AjaxJson doZhiJian(String sccode,String scline,String userid,String userno,String username,String remarks,Double jynum,Double hgnum,Double bhgnum,Double blnum){
        AjaxJson json = new AjaxJson();
        try{
            businessCheckIPQCService.zhijian(sccode, scline, userid, userno, username, remarks, jynum, hgnum, bhgnum, blnum);
            json.setSuccess(true);
            json.setMsg("检验成功。");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("检验失败.原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }
}
