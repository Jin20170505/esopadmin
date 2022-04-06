package com.jeeplus.modules.qiyewx.shenpi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.shenpi.entity.ShenPiDanHao;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: 审批数据接口
 */
public final class ShenPiDataUtil {
    /** 批量获取审批单号 */
    private static String ShenPiDanHaoURI  = "https://qyapi.weixin.qq.com/cgi-bin/oa/getapprovalinfo?access_token=";

    /**
     * 递归查询审批单号
     * @param starttime 审批单提交的时间范围，开始时间
     * @param endtime 审批单提交的时间范围，结束时间
     * @param cursor 分页查询游标，默认为0
     * @param template_id 模板类型/模板id  （1970324963002892_1688853781320138_429775872_1495181804  加班）
     * @param creator 申请人
     * @param department 审批单提单者所在部门
     * @param sp_status 审批单状态（1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付）
     * @param record_type record_type - 审批单类型属性，1-请假；2-打卡补卡；3-出差；4-外出；5-加班； 6- 调班；7-会议室预定；8-退款审批；9-红包报销审批
     *                    record_type筛选类型仅支持2021/05/31以后新提交的审批单，历史单不支持表单类型属性过滤
     * @param shenPiDanHao 单号实体
     * @return @class{com.jeeplus.modules.qiyewx.shenpi.entity.ShenPiDanHao} 单号实体
     */
    public static final ShenPiDanHao   findShenPiDanHao(Date starttime, Date endtime, Integer cursor,
                                                      String template_id, String creator, String department, String sp_status, String record_type, ShenPiDanHao shenPiDanHao){
        ShenPiDanHao temp = findShenPiDanHao(starttime.getTime()/1000,endtime.getTime()/1000,cursor,100,template_id,creator,department,sp_status,record_type);
        if(temp!=null){
            shenPiDanHao.getSp_no_list().addAll(temp.getSp_no_list());
            if(temp.getNext_cursor()!=-1){
                findShenPiDanHao(starttime,endtime,temp.getNext_cursor(),template_id,creator,department,sp_status,record_type,shenPiDanHao);
            }
        }
        return shenPiDanHao;
    }

    /***
     * 批量获取审批单号
     * @param starttime 审批单提交的时间范围，开始时间，UNix时间戳（秒）
     * @param endtime 审批单提交的时间范围，结束时间，Unix时间戳 起始时间跨度不能超过31天；
     * @param cursor 分页查询游标，默认为0，后续使用返回的next_cursor进行分页拉取
     * @param size 一次请求拉取审批单数量，默认值为100，上限值为100
     * @param template_id 模板类型/模板id
     * @param creator 申请人
     * @param department 审批单提单者所在部门
     * @param sp_status 审批单状态（1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付）
     * @param record_type record_type - 审批单类型属性，1-请假；2-打卡补卡；3-出差；4-外出；5-加班； 6- 调班；7-会议室预定；8-退款审批；9-红包报销审批
     *                    record_type筛选类型仅支持2021/05/31以后新提交的审批单，历史单不支持表单类型属性过滤
     * @return
     */
    private static final ShenPiDanHao findShenPiDanHao(Long starttime,Long endtime,Integer cursor,int size,
                                                      String template_id,String creator,String department,String sp_status,String record_type){
        try {
            String url = ShenPiDanHaoURI + QiYeWeiXinAccessTokenUtil.getShenPiAccessToken();
            Map<String,Object> param  = Maps.newHashMap();
            param.put("starttime",starttime);
            param.put("endtime",endtime);
            if(cursor==null){
                cursor = 0;
            }
            param.put("cursor",cursor);
            if(size>100){
                size = 100;
            }
            param.put("size",size);
            List<Map<String,String>> filters = Lists.newArrayList();
            if(StringUtils.isNotEmpty(template_id)){
                Map<String,String> tempMap = Maps.newHashMap();
                tempMap.put("key","template_id");
                tempMap.put("value",template_id);
                filters.add(tempMap);
            }
            if(StringUtils.isNotEmpty(creator)){
                Map<String,String> tempMap = Maps.newHashMap();
                tempMap.put("key","creator");
                tempMap.put("value",creator);
                filters.add(tempMap);
            }
            if(StringUtils.isNotEmpty(department)){
                Map<String,String> tempMap = Maps.newHashMap();
                tempMap.put("key","department");
                tempMap.put("value",department);
                filters.add(tempMap);
            }
            if(StringUtils.isNotEmpty(sp_status)){
                Map<String,String> tempMap = Maps.newHashMap();
                tempMap.put("key","sp_status");
                tempMap.put("value",sp_status);
                filters.add(tempMap);
            }
            if(StringUtils.isNotEmpty(record_type)){
                Map<String,String> tempMap = Maps.newHashMap();
                tempMap.put("key","record_type");
                tempMap.put("value",record_type);
                filters.add(tempMap);
            }
            param.put("filters",filters);
            String paramjson  = JSONObject.fromObject(param).toString();
            Connection.Response response = HttpsUtil.post(url,paramjson);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return null;
            }
            ShenPiDanHao shenPiDanHao = new ShenPiDanHao();
            JSONArray noList = json.getJSONArray("sp_no_list");
            List<String> spNoList = Lists.newArrayList();
            if(!noList.isEmpty()){
                noList.forEach(n->{
                    spNoList.add(n.toString());
                });
            }
            shenPiDanHao.setSp_no_list(spNoList);
            if(json.containsKey("next_cursor")){
                shenPiDanHao.setNext_cursor(json.getInt("next_cursor"));
            }else{
                shenPiDanHao.setNext_cursor(-1);
            }
            return shenPiDanHao;
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException("获取接口数据失败");
        }
    }

    public static void main(String[] args) {
        Date start  = DateUtils.parseDate("2021-08-01 00:00:00");
        Date end  = DateUtils.parseDate("2021-08-31 23:59:59");
        ShenPiDanHao shenPiDanHao  = new ShenPiDanHao();
        findShenPiDanHao(start,end,0,null,null,null,null,null,shenPiDanHao);
        System.out.println(shenPiDanHao.getSp_no_list());
        System.out.println(shenPiDanHao.getSp_no_list().size());
    }
}
