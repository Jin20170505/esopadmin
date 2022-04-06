package com.jeeplus.modules.qiyewx.daka;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaDayData;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.DayBaseInfo;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.OtInfo;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.DaySpItem;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.DaySummaryInfo;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.*;

/***
 * 打卡日报
 */
public final class DaKaDayRecordsUtils {
    private static String URI = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckin_daydata?access_token=";


    public final static List<DaKaDayData> findDaKaDayData(Date starttime, Date endtime, List<String> useridlist) {
        List<DaKaDayData> list = Lists.newArrayList();
        int size = useridlist.size();
        int len = size/100;
        for(int i=0;i<len;++i){
            List<DaKaDayData> temp = findDaKaDayRecords(starttime,endtime,useridlist.subList(i*100,(i+1)*100));
            if(temp!=null&&temp.size()>0){
                list.addAll(temp);
            }
        }
        int index = 100*len;
        if(size>index){
            List<DaKaDayData> temp = findDaKaDayRecords(starttime,endtime,useridlist.subList(index,size));
            if(temp!=null&&temp.size()>0){
                list.addAll(temp);
            }
        }
        return list;
    }
    public static final List<DaKaDayData> findDaKaDayRecords(Date starttime, Date endtime, List<String> useridlist){
        List<DaKaDayData> list = Lists.newArrayList();
        try {
            String url = URI+ QiYeWeiXinAccessTokenUtil.getDaKaAccessToken();
            Map<String,Object> param = new HashMap<>();
            param.put("starttime",starttime.getTime()/1000);
            param.put("endtime",endtime.getTime()/1000);
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
                DaKaDayData daKaDayData = new DaKaDayData();
                JSONObject base = j.getJSONObject("base_info");
                DayBaseInfo info = new DayBaseInfo();
                info.setDate(base.getLong("date")*1000);
                info.setAcctid(base.getString("acctid"));
                info.setDay_type(base.getString("day_type"));
                info.setName(base.getString("name"));
                info.setDeparts_name(base.getString("departs_name"));
                info.setRecord_type(base.getInt("record_type"));
                daKaDayData.setBase_info(info);
                JSONObject summary = j.getJSONObject("summary_info");
                DaySummaryInfo summaryInfo= new DaySummaryInfo();
                summaryInfo.setCheckin_count(summary.getInt("checkin_count"));
                summaryInfo.setRegular_work_sec(summary.getInt("regular_work_sec"));
                summaryInfo.setStandard_work_sec(summary.getInt("standard_work_sec"));
                summaryInfo.setEarliest_time(summary.getLong("earliest_time"));
                summaryInfo.setLastest_time(summary.getLong("lastest_time"));
                daKaDayData.setSummaryInfo(summaryInfo);
                OtInfo otInfo = new OtInfo();
                JSONObject ot = j.getJSONObject("ot_info");
                otInfo.setOt_status(ot.getInt("ot_status"));
                otInfo.setOt_duration(ot.getInt("ot_duration"));
                daKaDayData.setOtInfo(otInfo);
                JSONArray spList = j.getJSONArray("sp_items");
                List<DaySpItem> spItems = Lists.newArrayList();
                spList.forEach(s->{
                    JSONObject so = JSONObject.fromObject(s);
                    if(so.getInt("count")>0){
                        DaySpItem item = new DaySpItem();
                        item.setCount(so.getInt("count"));
                        item.setDuration(so.getLong("duration"));
                        item.setName(so.getString("name"));
                        item.setTime_type(so.getInt("time_type"));
                        spItems.add(item);
                    }
                });
                daKaDayData.setSpItems(spItems);
                list.add(daKaDayData);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> userids = Arrays.asList("0878");
        System.out.println(findDaKaDayRecords(DateUtils.parseDate("2022-01-01 00:00:00"),
                DateUtils.parseDate("2021-01-31 23:59:59"),userids).get(0));
    }

}
