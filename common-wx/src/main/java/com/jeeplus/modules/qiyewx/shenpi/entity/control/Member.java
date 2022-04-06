package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 成员控件 （control参数为Contact，且value参数为members）
 */
public class Member {
    /** 	成员的userid */
    private String userid;
    /** 	成员名 */
    private String name;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
