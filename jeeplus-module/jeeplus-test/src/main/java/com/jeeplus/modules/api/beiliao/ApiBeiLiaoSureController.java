package com.jeeplus.modules.api.beiliao;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.shengchan.dingdan.service.BusinessShengChanDingDanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/beiliaosure")
public class ApiBeiLiaoSureController {
    @Autowired
    private BusinessShengChanDingDanService businessShengChanDingDanService;

    @RequestMapping("getBeiLiaoInfo")
    public AjaxJson getZhiJianInfo(String schid){
        AjaxJson json = new AjaxJson();
        try {
            json.put("info",businessShengChanDingDanService.getBeiLiaoInfo(schid));
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
    public AjaxJson sure(String schid){
        AjaxJson json = new AjaxJson();
        try{
            businessShengChanDingDanService.sureBeiLiao(schid);
            json.setSuccess(true);
            json.setMsg("确认成功。");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("确认失败.原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }
}
