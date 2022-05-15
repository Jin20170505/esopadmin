package com.jeeplus.modules.api.chuku;

import com.jeeplus.common.json.AjaxJson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 领料单 出库
 */
@RestController
@RequestMapping("api/chuku/lingliao")
public class ApiLingLiaoChuKuController {


    @RequestMapping("findLingLiaoDanInfo")
    public AjaxJson findLingLiaoDanInfo(String bgcode){
        AjaxJson json = new AjaxJson();
        try {

        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败,失败原因："+e.getMessage());
            json.setSuccess(false);
        }
        return json;
    }

    public AjaxJson lingliaochuku(String bghid,String mxJson){
        AjaxJson json = new AjaxJson();

        return json;
    }
}
