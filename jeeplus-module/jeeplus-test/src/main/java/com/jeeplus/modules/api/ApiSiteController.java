package com.jeeplus.modules.api;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.base.site.entity.BaseSite;
import com.jeeplus.modules.base.site.service.BaseSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 工作站
 */
@RestController
@RequestMapping("api/site")
public class ApiSiteController {
    @Autowired
    private BaseSiteService baseSiteService;

    @RequestMapping("findall")
    public AjaxJson findAllSites(){
        AjaxJson json = new AjaxJson();
        try{
            List<BaseSite> sites = baseSiteService.findAllSites();
            json.setSuccess(true);
            json.setMsg("查询成功");
            json.put("sites",sites);
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败");
            json.setSuccess(false);
        }
        return json;
    }

}
