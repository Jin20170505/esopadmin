package com.jeeplus.modules.qiyewx.base;

import com.google.common.collect.Lists;
import com.jeeplus.modules.qiyewx.QiYeWeiXinAccessTokenUtil;
import com.jeeplus.modules.qiyewx.base.entity.Employee;
import com.jeeplus.modules.util.HttpsUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/24
 * @Description: 人员
 */
public class EmployeeUtil {
    private static String  url = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token=";

    public final static List<Employee> findEmployees(String deptid,Boolean fetchFlag){
        List<Employee> list = Lists.newArrayList();
        try {
            String accessToken = QiYeWeiXinAccessTokenUtil.getTongXunLuAccessToken();
            String fc = fetchFlag?"1":"0";
            String reqUrl = url +accessToken+"&department_id="+deptid+"&fetch_child="+fc;
            Connection.Response response = HttpsUtil.get(reqUrl);
            String rs = response.body();
            JSONObject json = JSONObject.fromObject(rs);
            if(0!=json.getInt("errcode")){
                return list;
            }
            JSONArray array = json.getJSONArray("userlist");
            StringBuffer deptment = new StringBuffer();
            array.forEach(j->{
                JSONObject jo = JSONObject.fromObject(j);
                Employee e = new Employee();
                e.setAlias(jo.getString("alias")).setAvatar(jo.getString("avatar")).setEmail(jo.getString("email"))
                        .setGender(jo.getString("gender")).setMainDepartment(jo.getInt("main_department")).setMobile(jo.getString("mobile"))
                        .setName(jo.getString("name")).setPosition(jo.getString("position")).setThumbAvatar(jo.getString("thumb_avatar"))
                        .setTelephone(jo.getString("telephone")).setStatus(jo.getInt("status")).setUserid(jo.getString("userid"));
                JSONArray dept = jo.getJSONArray("department");

                dept.forEach(d->{
                    deptment.append(d.toString()).append(",");
                });
                e.setDeptment(deptment.toString());
                deptment.setLength(0);
                list.add(e);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(findEmployees("1",true));
    }
}
