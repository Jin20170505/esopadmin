package com.jeeplus.modules.u8api;

import com.jeeplus.modules.u8api.bean.U8ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提供U8调用的接口
 */
@RestController
@RequestMapping("api/u8/shengchan")
public class ApiU8ShengchanController {

    @RequestMapping("add")
    public U8ApiResult add(String mid,String mxid,String bomid){
        U8ApiResult result = new U8ApiResult();
        try {
            result.setCode("1");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
    @RequestMapping("edit")
    public U8ApiResult edit(String mid,String mxid,String bomid){
        U8ApiResult result = new U8ApiResult();
        try {
            result.setCode("1");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }
    @RequestMapping("delete")
    public U8ApiResult delete(String mid,String mxid,String bomid){
        U8ApiResult result = new U8ApiResult();
        try {
            result.setCode("1");
            result.setSuccess(true);
        }catch (Exception e){
            e.printStackTrace();
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg(e.getMessage());
        }
        return result;
    }

}
