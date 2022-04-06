package com.jeeplus.modules.qiyewx.daka.entity;

/**
 * @Auther: Jin
 * @Date: 2021/9/11
 * @Description:
 */
public class DaKaDate {
    private String date;
    private String dateType;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    @Override
    public String toString() {
        return "DaKaDate{" +
                "date='" + date + '\'' +
                ", dateType='" + dateType + '\'' +
                '}';
    }
}
