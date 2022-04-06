package com.jeeplus.modules.qiyewx;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.time.DateUtil;
import com.jeeplus.modules.util.FileCacheUttil;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONObject;
import org.jsoup.Connection.Response;

import java.io.IOException;
import java.util.Date;

/**
 * @Auther: Jin
 * @Date: 2021/8/23
 * @Description: 企业微信AccessToken获取
 */
public final class QiYeWeiXinAccessTokenUtil {
    private final static long  MAX_TIME = 7180*1000;
    /** 企业id */
    public static final String CORP_ID="wxef8ef7a549b5e40f";
    /** 通讯录Secret   */
    public static final String TongXunLuSecret = "895S926G45YT0eQTbuY4u_Kq92PVWsesD0F4y2arCwg";
    /** 打卡Secret */
    public static final String DaKaSecret  = "eIZbq2cxGc323HoPU31MOA-TFdCdbmjHAnvRalMA3Kg";
    /** 审批Secret */
    private static final String ShenPiSecret = "YyXK0i0S5zE0ow1MH2CXiVcQsUhxHV1-PX65I0NgHRo";
    /** AccessToken 获取请求地址 */
    private static String TOKENURL="https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ID&corpsecret=SECRET";
    /***
     * 获取通讯录AccessToken
     * @return
     */
    public final static String getTongXunLuAccessToken(){
        return getToken(CORP_ID,TongXunLuSecret);
    }

    /**
     * 获取打卡AccessToken
     * @return
     */
    public final static String getDaKaAccessToken(){
        return getToken(CORP_ID,DaKaSecret);
    }
    /**
     * 获取审批AccessToken
     * @return
     */
    public final static String getShenPiAccessToken(){
        return getToken(CORP_ID,ShenPiSecret);
    }

    public final static String getToken(String id,String secret){
        JSONObject json = FileCacheUttil.readJson(secret);
        if(json==null||json.isEmpty()){
            json = getAccessToken(id,secret);
            if(json==null){
                return null;
            }else {
                String token = json.getString("access_token");
                long saveTime = System.currentTimeMillis();
                FileCacheUttil.writeJson(secret,"{\"token\":\""+token+"\",\"save_time\":"+saveTime+"}");
                return token;
            }
        }else {
            long saveTime = json.getLong("save_time");
            long nowTime = System.currentTimeMillis();
            long remainTime = nowTime - saveTime;
            if(remainTime<MAX_TIME){
                return json.getString("token");
            }else{
                json = getAccessToken(id,secret);
                if(json==null){
                    return null;
                }else {
                    String token = json.getString("access_token");
                    saveTime = System.currentTimeMillis();
                    FileCacheUttil.writeJson(secret,"{\"token\":\""+token+"\",\"save_time\":"+saveTime+"}");
                    return token;
                }
            }
        }
    }
    /**
     * 获取AccessToken
     * @param corpid 企业id
     * @param secret 应用密钥
     * @return
     */
    private static final JSONObject getAccessToken(String corpid, String secret){
        JSONObject json  = null;
        if(StringUtils.isEmpty(corpid)){
            corpid = CORP_ID;
        }
        String url = TOKENURL.replaceAll("ID",corpid).replaceAll("SECRET",secret);
        try {
            Response response = HttpsUtil.get(url);
            String rs = response.body();
            // {"errcode":0,"errmsg":"ok","access_token":"wSRy-GiF7MUA9FrEQT2dxRrEq07I_QQN349t_VcLsTnxTccu59TCwr6ksLdOeE4TUtdSXs-vqk6_bWnLnIk0rfmV-fj4jnbYbzhaPtjJ-6MYawXpw5cGv0BgBRSPM5CN_d89xY2ycW7lt9gCewli9Q3oVc_tyRC05VUF6aQqahDLgFaep5G9OsCPQDqWnzxYjR7rePu2p-aohuimkZzxxQ","expires_in":7200}
            json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static void main(String[] args) {
        System.out.println(getShenPiAccessToken());
    }
}
