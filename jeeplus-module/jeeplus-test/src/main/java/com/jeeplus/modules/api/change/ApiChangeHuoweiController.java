package com.jeeplus.modules.api.change;

import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.huowei.change.service.BusinessHuoWeiChangeService;
import com.jeeplus.modules.u8infacerecord.entity.U8InfaceRecord;
import com.jeeplus.modules.u8infacerecord.service.U8InfaceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/change/huowei")
public class ApiChangeHuoweiController {
    @Autowired
    private BusinessHuoWeiChangeService businessHuoWeiChangeService;
    @Autowired
    private U8InfaceRecordService u8InfaceRecordService;
    @RequestMapping("do")
    public AjaxJson huoweichange(String ckid,String userid,String mxJson){
        AjaxJson json  = new AjaxJson();
        U8InfaceRecord record = new U8InfaceRecord();
        record.setUrl("api/change/huowei/do");
        record.setTitle("货位调整");
        record.setParams("{\"ckid\":\""+ckid+"\",\"userid\":\""+userid+"\",\"mxJson\":"+mxJson);
        try{
            businessHuoWeiChangeService.change(ckid,userid,mxJson);
            record.setResult("成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setSuccess(false);
            json.setMsg("操作失败,原因："+e.getMessage());
            String rs ="";
            if(StringUtils.isNotEmpty(e.getMessage())||e.getMessage().length()>300){
                rs = e.getMessage().substring(0,300);
            }else {
                rs = e.getMessage();
            }
            record.setResult("失败，原因："+rs);
        }
        try{
            u8InfaceRecordService.save(record);
        }catch (Exception e){
        }
        return json;
    }
}
