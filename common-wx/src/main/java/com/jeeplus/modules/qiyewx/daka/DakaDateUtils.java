package com.jeeplus.modules.qiyewx.daka;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaDate;
import com.jeeplus.modules.qiyewx.daka.entity.DaKaMonthData;
import com.jeeplus.modules.qiyewx.daka.entity.daka_month.*;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.*;

/**
 * @Auther: Jin
 * @Date: 2021/9/11
 * @Description:
 */
public class DakaDateUtils {
    private static String URI = "https://qyapi.weixin.qq.com/cgi-bin/checkin/getcheckin_daydata?access_token=";

    public static final List<DaKaDate> findDaKaDates(Date starttime, Date endtime, List<String> useridlist){
        List<DaKaDate> list = Lists.newArrayList();
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
                JSONObject info = j.getJSONObject("base_info");
                DaKaDate daKaDate = new DaKaDate();
                daKaDate.setDate(DateUtils.formatDate(new Date(info.getLong("date")*1000)));
                daKaDate.setDateType(info.getString("day_type"));
                list.add(daKaDate);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> userids = Arrays.asList("0878");
        System.out.println(findDaKaDates(DateUtils.parseDate("2021-08-01 00:00:00"),
                DateUtils.parseDate("2021-08-31 23:59:59"),userids).size());
    }
}
