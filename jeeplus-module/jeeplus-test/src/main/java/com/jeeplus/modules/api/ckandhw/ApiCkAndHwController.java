package com.jeeplus.modules.api.ckandhw;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.base.cangku.service.BaseCangKuService;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仓库 货位
 */
@RestController
@RequestMapping("api/ckandhw")
public class ApiCkAndHwController {
    @Autowired
    private BaseCangKuService baseCangKuService;
    @Autowired
    private BaseHuoWeiMapper huoWeiMapper;
    @RequestMapping("findck")
    public AjaxJson findCk(){
        AjaxJson json = new AjaxJson();
        try {
            json.put("list",baseCangKuService.findAllCk());
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败");
            json.setSuccess(false);
        }
        return json;
    }

    @RequestMapping("findhw")
    public AjaxJson findHw(String ckid){
        AjaxJson json = new AjaxJson();
        try {
            json.put("list",huoWeiMapper.findHwItem(ckid));
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询失败");
            json.setSuccess(false);
        }
        return json;
    }
}
