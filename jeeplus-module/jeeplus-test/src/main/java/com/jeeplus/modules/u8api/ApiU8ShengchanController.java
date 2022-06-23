package com.jeeplus.modules.u8api;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.business.shengchan.bom.service.BusinessShengChanDingdanMxService;
import com.jeeplus.modules.business.shengchan.dingdan.service.BusinessShengChanDingDanService;
import com.jeeplus.modules.u8api.bean.U8ApiResult;
import com.jeeplus.modules.u8data.morder.entity.U8Moallocate;
import com.jeeplus.modules.u8data.morder.entity.U8Morder;
import com.jeeplus.modules.u8data.morder.service.U8MoallocateService;
import com.jeeplus.modules.u8data.morder.service.U8MorderService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * 提供U8调用的接口
 */
@RestController
@RequestMapping("api/u8/shengchan")
public class ApiU8ShengchanController {

    @Autowired
    private U8MorderService u8MorderService;
    @Autowired
    private U8MoallocateService u8MoallocateService;
    @Autowired
    private BusinessShengChanDingDanService businessShengChanDingDanService;
    @Autowired
    private BusinessShengChanDingdanMxService businessShengChanDingdanMxService;
    @RequestMapping("add")
    public U8ApiResult add(HttpServletRequest request){
        U8ApiResult result = new U8ApiResult();
        String jsonStr = getPostBody(request);
        if(StringUtils.isEmpty(jsonStr)||!jsonStr.contains("{")){
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
            return result;
        }
        try {
            jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
            JSONObject json = JSONObject.fromObject(jsonStr);
            String mid =json.optString("mid");
            String mxids=json.optString("mxids"),bomids  = json.optString("bomids");
            U8Morder order = new U8Morder();
            if(StringUtils.isNotEmpty(mid)){
                order.setMoId(mid);
                List<U8Morder> data = u8MorderService.findList(order);
                if(data==null){
                    result.setCode("0");
                    result.setSuccess(false);
                    result.setMsg("未查到新增数据");
                    return result;
                }
                List<String> schids =businessShengChanDingDanService.sychu8(data);
                schids.forEach(schid->sychU8bom(schid));
            }else if(StringUtils.isNotEmpty(mxids)){
                List<U8Morder> data = Lists.newArrayList();
                Arrays.asList(mxids.split(",")).forEach(mxid->{
                    order.setModid(mxid);
                    List<U8Morder> dds =  u8MorderService.findList(order);
                    if(dds!=null){
                        data.addAll(dds);
                    }
                });
                if(data.isEmpty()){
                    result.setCode("0");
                    result.setSuccess(false);
                    result.setMsg("未查到新增数据");
                    return result;
                }
                List<String> schids =businessShengChanDingDanService.sychu8(data);
                schids.forEach(schid->sychU8bom(schid));
            }else if(StringUtils.isNotEmpty(bomids)){
                List<U8Moallocate> data = Lists.newArrayList();
                U8Moallocate moallocate = new U8Moallocate();
                Arrays.asList(bomids.split(",")).forEach(bomid->{
                    moallocate.setAllocateId(bomid);
                    List<U8Moallocate> list = u8MoallocateService.findList(moallocate);
                    if(list!=null && list.isEmpty()){
                        data.addAll(list);
                    }
                });
                businessShengChanDingdanMxService.sychU8bom(data);
            }else {
                result.setCode("0");
                result.setSuccess(false);
                result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
                return result;
            }
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
    public AjaxJson sychU8bom(String schid){
        AjaxJson json = new AjaxJson();
        try{
            U8Moallocate moallocate = new U8Moallocate();
            moallocate.setMoDId(schid);
            List<U8Moallocate> data = u8MoallocateService.findList(moallocate);
            if(data==null){
                json.setMsg("同步成功(u8数据空)");
                json.setSuccess(true);
                return json;
            }
            businessShengChanDingdanMxService.sychU8bom(data);
        }catch (Exception e){
            e.printStackTrace();
            json.setMsg("同步失败,原因："+e.getMessage());
        }
        return json;
    }
    @RequestMapping("editcheck")
    public U8ApiResult editcheck(HttpServletRequest request){
        U8ApiResult result = new U8ApiResult();
        String jsonStr = getPostBody(request);
        if(StringUtils.isEmpty(jsonStr)||!jsonStr.contains("{")){
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
            return result;
        }
        try {
            jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
            JSONObject json = JSONObject.fromObject(jsonStr);
            String mid =json.optString("mid");
            String mxids=json.optString("mxids"),bomids  = json.optString("bomids");
            if(StringUtils.isNotEmpty(mid)){
                businessShengChanDingDanService.editCheckMid(mid);
            }else if(StringUtils.isNotEmpty(mxids)){
                businessShengChanDingDanService.editCheckMxid(mxids);
            }else if(StringUtils.isNotEmpty(bomids)){
                businessShengChanDingDanService.editCheckBomid(bomids);
            }else {
                result.setCode("0");
                result.setSuccess(false);
                result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
                return result;
            }
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
    public U8ApiResult edit(HttpServletRequest request){
        U8ApiResult result = new U8ApiResult();
        String jsonStr = getPostBody(request);
        if(StringUtils.isEmpty(jsonStr)||!jsonStr.contains("{")){
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
            return result;
        }
        try {
            jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
            JSONObject json = JSONObject.fromObject(jsonStr);
            String mid =json.optString("mid");
            String mxids=json.optString("mxids"),bomids  = json.optString("bomids");
            U8Morder order = new U8Morder();
            if(StringUtils.isNotEmpty(mid)){
                order.setMoId(mid);
                List<U8Morder> data = u8MorderService.findList(order);
                if(data==null){
                    result.setCode("0");
                    result.setSuccess(false);
                    result.setMsg("未查到新增数据");
                    return result;
                }
                List<String> schids =businessShengChanDingDanService.sychu8(data);
                schids.forEach(schid->sychU8bom(schid));
            }else if(StringUtils.isNotEmpty(mxids)){
                List<U8Morder> data = Lists.newArrayList();
                Arrays.asList(mxids.split(",")).forEach(mxid->{
                    order.setModid(mxid);
                    List<U8Morder> dds =  u8MorderService.findList(order);
                    if(dds!=null){
                        data.addAll(dds);
                    }
                });
                if(data.isEmpty()){
                    result.setCode("0");
                    result.setSuccess(false);
                    result.setMsg("未查到新增数据");
                    return result;
                }
                List<String> schids =businessShengChanDingDanService.sychu8(data);
                schids.forEach(schid->sychU8bom(schid));
            }else if(StringUtils.isNotEmpty(bomids)){
                List<U8Moallocate> data = Lists.newArrayList();
                U8Moallocate moallocate = new U8Moallocate();
                Arrays.asList(bomids.split(",")).forEach(bomid->{
                    moallocate.setAllocateId(bomid);
                    List<U8Moallocate> list = u8MoallocateService.findList(moallocate);
                    if(list!=null && list.isEmpty()){
                        data.addAll(list);
                    }
                });
                businessShengChanDingdanMxService.sychU8bom(data);
            }else {
                result.setCode("0");
                result.setSuccess(false);
                result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
                return result;
            }
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
    public U8ApiResult delete(HttpServletRequest request){
        U8ApiResult result = new U8ApiResult();
        String jsonStr = getPostBody(request);
        if(StringUtils.isEmpty(jsonStr)||!jsonStr.contains("{")){
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
            return result;
        }
        String rs = "";
        try {
            jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
            JSONObject json = JSONObject.fromObject(jsonStr);
            String mid =json.optString("mid");
            String mxids=json.optString("mxids"),bomids  = json.optString("bomids");
            if(StringUtils.isNotEmpty(mid)){
               rs = businessShengChanDingDanService.deleteCheckMid(mid);
            }else if(StringUtils.isNotEmpty(mxids)){
                rs = businessShengChanDingDanService.deleteCheckMxid(mxids);
            }else if(StringUtils.isNotEmpty(bomids)){
                businessShengChanDingDanService.deleteCheckBomid(bomids);
            }else {
                result.setCode("0");
                result.setSuccess(false);
                result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID，生产子件ID]");
                return result;
            }
            if(StringUtils.isNotEmpty(rs)){
                result.setCode("0");
                result.setSuccess(false);
                result.setMsg(rs);
            }else {
                result.setCode("1");
                result.setSuccess(true);
            }
        }catch (Exception e){
            e.printStackTrace();
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg("MES服务器出错了");
        }
        return result;
    }

    public static String getPostBody(HttpServletRequest request){
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String line = "";
        BufferedReader reader = null;
        try{
            reader = request.getReader();
            while (null!=(line = reader.readLine())){
                sb.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    /**
     * 订单关闭
     * @return
     */
    @RequestMapping("close")
    public U8ApiResult close(HttpServletRequest request){
        U8ApiResult result = new U8ApiResult();
        String jsonStr = getPostBody(request);
        if(StringUtils.isEmpty(jsonStr)||!jsonStr.contains("{")){
            result.setCode("0");
            result.setSuccess(false);
            result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID]");
            return result;
        }
        try{
            jsonStr = jsonStr.substring(jsonStr.indexOf("{"));
            JSONObject json = JSONObject.fromObject(jsonStr);
            String mid =json.optString("mid");
            String mxids=json.optString("mxids");
            if(StringUtils.isNotEmpty(mid)){
                businessShengChanDingDanService.closeMid(mid);
            }else if(StringUtils.isNotEmpty(mxids)){
                businessShengChanDingDanService.closeMxid(mxids);
            }else {
                result.setCode("0");
                result.setSuccess(false);
                result.setMsg("缺少必要参数,[生产订单ID，生产订单明细ID]");
                return result;
            }
            result.setCode("1");
            result.setSuccess(true);
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
