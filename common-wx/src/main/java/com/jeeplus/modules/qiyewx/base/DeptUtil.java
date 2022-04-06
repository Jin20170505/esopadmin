package com.jeeplus.modules.qiyewx.base;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.base.entity.Dept;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/24
 * @Description: 部门
 */
public class DeptUtil {
    private static String URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=";

    public final static List<Dept> findDepts(String pid){
        if(StringUtils.isEmpty(pid)){
            pid="";
        }
        String url = URL+QiYeWeiXinAccessTokenUtil.getTongXunLuAccessToken()+"&id="+pid;
        List<Dept> list = Lists.newArrayList();
        try {
            Connection.Response response = HttpsUtil.get(url);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return list;
            }
            JSONArray array = json.getJSONArray("department");
            array.forEach(j->{
                Dept dept = new Dept();
                JSONObject jo = JSONObject.fromObject(j);
                dept.setId(jo.getInt("id")).setName(jo.getString("name"))
                .setOrder(jo.getInt("order")).setParentid(jo.getInt("parentid"));
                list.add(dept);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(findDepts(""));
    }
}
