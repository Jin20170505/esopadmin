package com.jeeplus.modules.qiyewx.base;

import com.google.common.collect.Lists;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.base.entity.Tag;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.List;

/***
 * 通讯录--标签
 */
public class TagUtil {

    private static String LIST_URI = "https://qyapi.weixin.qq.com/cgi-bin/tag/list?access_token=";

    /**
     * 获取标签列表
     * @return
     */
    public static List<Tag> findTagList(){
        List<Tag> tags = Lists.newArrayList();
        try{
            String accessToken = QiYeWeiXinAccessTokenUtil.getTongXunLuAccessToken();
            String reqUrl = LIST_URI +accessToken;
            Connection.Response response = HttpsUtil.get(reqUrl);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return tags;
            }
            JSONArray array = json.getJSONArray("taglist");
            array.forEach(j->{
                JSONObject jo = JSONObject.fromObject(j);
                Tag tag = new Tag();
                tag.setId(jo.getString("tagid"));
                tag.setName(jo.getString("tagname"));
                tags.add(tag);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return tags;
    }

    /**
     * 根据标签获取人员列表
     * @param tagid 标签id
     * @return userid数组
     */
    public static List<String> findUseridByTag(String tagid){
        List<String> userids = Lists.newArrayList();
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/tag/get?access_token="+QiYeWeiXinAccessTokenUtil.getTongXunLuAccessToken()+"&tagid="+tagid;
            Connection.Response response = HttpsUtil.get(url);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return userids;
            }
            JSONArray array = json.getJSONArray("userlist");
            array.forEach(j->{
                JSONObject jo = JSONObject.fromObject(j);
                userids.add(jo.getString("userid"));
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return userids;
    }

    public static void main(String[] args) {
        System.out.println(findTagList());
    }
}
