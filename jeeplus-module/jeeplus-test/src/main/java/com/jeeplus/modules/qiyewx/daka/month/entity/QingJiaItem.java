package com.jeeplus.modules.qiyewx.daka.month.entity;

/**
 * @Auther: Jin
 * @Date: 2022/2/26
 * @Description: 请假项目
 */
public class QingJiaItem {
    private String userid;
    private String ym;
    private String name;
    private String timeType;
    private Double timeLen;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getYm() {
        return ym;
    }

    public void setYm(String ym) {
        this.ym = ym;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public Double getTimeLen() {
        if(timeLen==null){
            timeLen=0.00;
            return timeLen;
        }
        if("1".equals(getTimeType())){
            timeLen = timeLen/3600;
        }else {
            timeLen = timeLen/86400;
        }
        return timeLen;
    }

    public void setTimeLen(Double timeLen) {
        this.timeLen = timeLen;
    }
}
