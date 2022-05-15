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
    public AjaxJson findLingLiaoDanInfo(){
        AjaxJson json = new AjaxJson();

        return json;
    }

    public AjaxJson lingliaochuku(String bghid,String mxJson){
        AjaxJson json = new AjaxJson();

        return json;
    }
}
