package com.jeeplus.modules.qiyewx.sp.entity;

/**
 * @Auther: Jin
 * @Date: 2022/2/26
 * @Description: 某月的加班时长统计
 */
public class JiaBanItem {
    private String userid;
    private String jiabanLen;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getJiabanLen() {
        return jiabanLen;
    }

    public void setJiabanLen(String jiabanLen) {
        this.jiabanLen = jiabanLen;
    }
}
