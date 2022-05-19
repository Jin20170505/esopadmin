package com.jeeplus.modules.api.faliao;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.business.faliao.service.BusinessFaLiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调拨单
 */
@RestController
@RequestMapping("api/faliao")
public class ApiFaLiaoController {
    @Autowired
    private BusinessFaLiaoService businessFaLiaoService;

    @RequestMapping("do")
    public AjaxJson faliao(String userid,String fromck,String tock,String mxJson){
        AjaxJson json = new AjaxJson();
        try{
            businessFaLiaoService.faliao(userid, fromck, tock, mxJson);
            json.setSuccess(true);
            json.setMsg("调拨成功。");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("调拨失败，原因："+e.getMessage());
        }
        return json;
    }
}
