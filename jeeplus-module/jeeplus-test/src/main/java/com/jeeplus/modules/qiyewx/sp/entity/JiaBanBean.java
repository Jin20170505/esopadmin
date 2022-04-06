package com.jeeplus.modules.qiyewx.sp.entity;

import java.util.Date;

/**
 * @Auther: Jin
 * @Date: 2021/9/11
 * @Description:
 */
public class JiaBanBean {
    private String dayType;
    private Double hour;

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public Double getHour() {
        if (hour==null){
            hour=0.00;
        }
        return hour;
    }

    public void setHour(Double hour) {
        this.hour = hour;
    }
}
