package com.jeeplus.modules.qiyewx.daka;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaMonthData;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaRecord;
import com.jeeplus.modules.qiyewx.daka.entity.daka_month.*;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.*;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: 打卡记录接口
 */
public class DaKaRecordsUtils {

    private static String URI = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckindata?access_token=";
    /** 获取打卡月报数据 */
    private static String Month_uri = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckin_monthdata?access_token=";

    public final static List<DaKaMonthData> findDaKaMonthData(Date starttime,Date endtime,List<String> useridlist) {
        List<DaKaMonthData> list = Lists.newArrayList();
        int size = useridlist.size();
        int len = size/100;
        for(int i=0;i<len;++i){
            List<DaKaMonthData> temp = findDaKaMonthData(starttime.getTime()/1000,endtime.getTime()/1000,useridlist.subList(i*100,(i+1)*100));
            if(temp!=null&&temp.size()>0){
                list.addAll(temp);
            }
        }
        int index = 100*len;
        if(size>index){
            List<DaKaMonthData> temp = findDaKaMonthData(starttime.getTime()/1000,endtime.getTime()/1000,useridlist.subList(index,size));
            if(temp!=null&&temp.size()>0){
                list.addAll(temp);
            }
        }
        return list;
    }

    /**
     * 打卡月报统计数据 获取月报的开始时间
     * @param starttime 获取月报的开始时间。Unix时间戳 (秒)
     * @param endtime 	获取月报的结束时间。Unix时间戳（秒）
     * @param useridlist 需要获取月报的用户列表
     * @return
     */
    public final static List<DaKaMonthData> findDaKaMonthData(long starttime,long endtime,List<String> useridlist){
        List<DaKaMonthData> list = Lists.newArrayList();
        try {
            String url = Month_uri+ QiYeWeiXinAccessTokenUtil.getDaKaAccessToken();
            Map<String,Object> param = new HashMap<>();
            param.put("starttime",starttime);
            param.put("endtime",endtime);
            param.put("useridlist",useridlist);
            String paramjson  = JSONObject.fromObject(param).toString();
            Connection.Response response = HttpsUtil.post(url,paramjson);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return list;
            }
            JSONArray datas = json.getJSONArray("datas");
            datas.forEach(d->{
                JSONObject j = JSONObject.fromObject(d);
                DaKaMonthData data = new DaKaMonthData();
                JSONObject baseInfoJson  =  j.getJSONObject("base_info");
                /** 基础信息 */
                BaseInfo baseInfo = new BaseInfo();
                baseInfo.setAcctid(baseInfoJson.getString("acctid"));
                baseInfo.setDeparts_name(baseInfoJson.getString("departs_name"));
                baseInfo.setName(baseInfoJson.getString("name"));
                baseInfo.setRecord_type(baseInfoJson.getInt("record_type"));
                if(baseInfoJson.containsKey("rule_info")){
                    JSONObject ruleJson = baseInfoJson.getJSONObject("rule_info");
                    RuleInfo ruleInfo =new RuleInfo();
                    ruleInfo.setGroupid(ruleJson.getInt("groupid"));
                    ruleInfo.setGroupname(ruleJson.getString("groupname"));
                    baseInfo.setRule_nfo(ruleInfo);
                }
                data.setBase_info(baseInfo);
                /** 汇总信息 */
                JSONObject summary_infoJson = j.getJSONObject("summary_info");
                SummaryInfo summaryInfo = new SummaryInfo();
                summaryInfo.setWork_days(summary_infoJson.containsKey("work_days")?summary_infoJson.getInt("work_days"):0);
                summaryInfo.setRegular_days(summary_infoJson.containsKey("regular_days")?summary_infoJson.getInt("regular_days"):0);
                summaryInfo.setRegular_work_sec(summary_infoJson.containsKey("regular_work_sec")?summary_infoJson.getLong("regular_work_sec"):0);
                summaryInfo.setStandard_work_sec(summary_infoJson.containsKey("standard_work_sec")?summary_infoJson.getLong("standard_work_sec"):0);
                if(summary_infoJson.containsKey("except_days")){
                    summaryInfo.setExcept_days(summary_infoJson.getInt("except_days"));
                }
                data.setSummary_info(summaryInfo);
                /** 异常信息 */
                JSONArray exception_infosJson = j.getJSONArray("exception_infos");
                if(!exception_infosJson.isEmpty()){
                    List<ExceptionInfo> exceptionInfos= Lists.newArrayList();
                    exception_infosJson.forEach(e->{
                        JSONObject ej = JSONObject.fromObject(e);
                        if(ej.containsKey("exception")){
                            ExceptionInfo ei = new ExceptionInfo();
                            ei.setCount(ej.containsKey("count")?ej.getInt("count"):0);
                            ei.setDuration(ej.containsKey("duration")?ej.getLong("duration"):0);
                            ei.setException(ej.getInt("exception"));
                            exceptionInfos.add(ei);
                        }
                    });
                    data.setException_infos(exceptionInfos);
                }
                /** 假勤信息 */
                JSONArray sp_itemsJson = j.getJSONArray("sp_items");
                if(!sp_itemsJson.isEmpty()){
                    List<SpItem> spItems = Lists.newArrayList();
                    sp_itemsJson.forEach(s->{
                        JSONObject sj = JSONObject.fromObject(s);
                        if(sj.containsKey("type")){
                            SpItem si = new SpItem();
                            si.setCount(sj.getInt("count"));
                            si.setDuration(sj.getLong("duration"));
                            si.setName(sj.getString("name"));
                            si.setTime_type(sj.getInt("time_type"));
                            si.setType(sj.getInt("type"));
                            si.setVacation_id(sj.getInt("vacation_id"));
                            spItems.add(si);
                        }
                    });
                    data.setSp_items(spItems);
                }
                /** 加班信息 */
                JSONObject overwork_infoJson = j.getJSONObject("overwork_info");
                OverworkInfo overworkInfo = new OverworkInfo();
                if(overwork_infoJson.containsKey("workday_over_sec")){
                    overworkInfo.setWorkday_over_sec(overwork_infoJson.getLong("workday_over_sec"));
                }else {
                    overworkInfo.setWorkday_over_sec(0L);
                }
                if(overwork_infoJson.containsKey("holidays_over_sec")){
                    overworkInfo.setHolidays_over_sec(overwork_infoJson.getLong("holidays_over_sec"));
                }else {
                    overworkInfo.setHolidays_over_sec(0L);
                }
                if(overwork_infoJson.containsKey("restdays_over_sec")){
                    overworkInfo.setRestdays_over_sec(overwork_infoJson.getLong("restdays_over_sec"));
                }else {
                    overworkInfo.setRestdays_over_sec(0L);
                }
                data.setOverwork_info(overworkInfo);
                list.add(data);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    /** 不限制useridlist长度 */
    public final static List<DaKaRecord> findDaKaRecordsNoUseridListLimit(int opencheckindatatype, Date starttime, Date endtime, List<String> useridlist){
        List<DaKaRecord> list = Lists.newArrayList();
        int size = useridlist.size();
        int len = size/100;
        for(int i=0;i<len;++i){
            List<DaKaRecord> temp = findDaKaRecords(opencheckindatatype,starttime,endtime,useridlist.subList(i*100,(i+1)*100));
            if(temp!=null&&temp.size()>0){
                list.addAll(temp);
            }
        }
        int index = 100*len;
        if(size>index){
            List<DaKaRecord> temp = findDaKaRecords(opencheckindatatype,starttime,endtime,useridlist.subList(index,size));
            if(temp!=null&&temp.size()>0){
                list.addAll(temp);
            }
        }
        return list;
    }

    public final static List<DaKaRecord> findDaKaRecords(int opencheckindatatype, Date starttime, Date endtime, List<String> useridlist){
        return findDaKaRecords(opencheckindatatype,starttime.getTime()/1000,endtime.getTime()/1000,useridlist);
    }
    /**
     * 获取打卡记录数据(获取指定员工指定时间段内的打卡记录数据)
     * @param opencheckindatatype 打卡类型。1：上下班打卡；2：外出打卡；3：全部打卡
     * @param starttime 获取打卡记录的开始时间。Unix时间戳 (秒)
     * @param endtime 	获取打卡记录的结束时间。Unix时间戳（秒）
     * @param useridlist 需要获取打卡记录的用户列表
     * <p color="red">1.获取记录时间跨度不超过30天.  2.用户列表不超过100个。若用户超过100个，请分批获取</p>
     * @return
     */
    public final static List<DaKaRecord> findDaKaRecords(int opencheckindatatype,long starttime,long endtime,List<String> useridlist){
        List<DaKaRecord> list = Lists.newArrayList();
        try {
            String url = URI+ QiYeWeiXinAccessTokenUtil.getDaKaAccessToken();
            Map<String,Object> param = new HashMap<>();
            param.put("opencheckindatatype",opencheckindatatype);
            param.put("starttime",starttime);
            param.put("endtime",endtime);
            param.put("useridlist",useridlist);
            String paramjson  = JSONObject.fromObject(param).toString();
            Connection.Response response = HttpsUtil.post(url,paramjson);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return list;
            }
            JSONArray jsonArray = json.getJSONArray("checkindata");
            jsonArray.forEach(ja->{
                DaKaRecord record = new DaKaRecord();
                JSONObject j = JSONObject.fromObject(ja);
                record.setUserid(j.getString("userid")).setCheckin_time(j.getLong("checkin_time")).setCheckin_type(j.getString("checkin_type"))
                        .setDeviceid(j.getString("deviceid")).setException_type(j.getString("exception_type")).setGroupid(j.containsKey("groupid")?j.getInt("groupid"):-1)
                        .setGroupname(j.getString("groupname")).setLat(j.getLong("lat")).setLng(j.getLong("lng"))
                        .setLocation_detail(j.getString("location_detail")).setLocation_title(j.getString("location_title")).setNotes(j.getString("notes"))
                        .setSch_checkin_time(j.containsKey("sch_checkin_time")?j.getLong("sch_checkin_time"):null)
                        .setTimeline_id(j.containsKey("timeline_id")?j.getString("timeline_id"):"").setWifimac(j.getString("wifimac")).setWifiname(j.getString("wifiname"));
                if(j.containsKey("schedule_id")){
                    record.setSchedule_id(j.getInt("schedule_id"));
                }
                JSONArray mediaids = j.getJSONArray("mediaids");
                if(mediaids!=null && !mediaids.isEmpty()){
                        record.setMediaids(mediaids.join(",").replaceAll("\"",""));
                }
                list.add(record);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> userids = Arrays.asList("0878","0837");
        long start  = DateUtils.parseDate("2021-08-01 00:00:00").getTime()/1000;
        long end  = DateUtils.parseDate("2021-08-31 23:59:59").getTime()/1000;
        System.out.println(findDaKaMonthData(start,end,userids));
    }
}
