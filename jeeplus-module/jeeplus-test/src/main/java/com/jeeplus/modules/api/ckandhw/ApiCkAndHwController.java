package com.jeeplus.modules.api.ckandhw;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.modules.api.bean.ckandhw.CkBean;
import com.jeeplus.modules.base.cangku.mapper.BaseCkUserMapper;
import com.jeeplus.modules.base.cangku.service.BaseCangKuService;
import com.jeeplus.modules.base.huowei.mapper.BaseHuoWeiMapper;
import com.jeeplus.modules.base.vendor.mapper.BaseCkOfVendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @Autowired
    private BaseCkUserMapper baseCkUserMapper;
    /**
     * 根据PDA使用者查询可见仓库
     * @param userid
     * @return
     */
    @RequestMapping("findCkOfUser")
    public AjaxJson findCkOfUser(String userid){
        AjaxJson json = new AjaxJson();
        try {
            List<CkBean> list = baseCkUserMapper.findCks(userid);
            if(list==null || list.isEmpty()){
                json.setSuccess(false);
                json.setMsg("您的可见仓库为空。请联系管理员进行设置.");
                return json;
            }
            json.put("list",list);
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询仓库失败");
            json.setSuccess(false);
        }
        return json;
    }
    @Autowired
    private BaseCkOfVendorMapper baseCkOfVendorMapper;
    @RequestMapping("findCkOVendor")
    public AjaxJson findCkOVendor(String vendorid){
        AjaxJson json = new AjaxJson();
        try {
            List<CkBean> list = baseCkOfVendorMapper.findCksByVendor(vendorid);
            if(list==null || list.isEmpty()){
                json.setSuccess(false);
                json.setMsg("该供应商对应的仓库为空。请联系管理员进行设置.");
                return json;
            }
            json.put("list",list);
            json.setSuccess(true);
            json.setMsg("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("查询仓库失败");
            json.setSuccess(false);
        }
        return json;
    }
}
