package com.jeeplus.modules.qiyewx.shenpi;

import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.shenpi.entity.TemplateDetail;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: 审批模板接口
 */
public class ShenPiTemplateUtil {

    /** 审批模板详情 */
    private static String uri = "https://qyapi.weixin.qq.com/cgi-bin/oa/gettemplatedetail?access_token=";

    /**
     *
     * @param templateid 模板id （1970324963002892_1688853781328324_594545520_1499686725：请假）
     * @return
     */
    public final static TemplateDetail getTemplateDetail(String templateid){
        try {
            String url  = uri + QiYeWeiXinAccessTokenUtil.getShenPiAccessToken();
            String paramJson  = "{\"template_id\":\""+templateid+"\"}";
            Connection.Response response = HttpsUtil.post(url,paramJson);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return null;
            }
            TemplateDetail detail = new TemplateDetail();

            return detail;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
