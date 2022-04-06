package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 日期 或 日期+时间控件
 */
public class DateTimeControl {
    /** 	时间展示类型：day-日期；hour-日期+时间 */
    private String type;
    /** 	时间戳，字符串类型 */
    private String s_timestamp;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getS_timestamp() {
        return s_timestamp;
    }

    public void setS_timestamp(String s_timestamp) {
        this.s_timestamp = s_timestamp;
    }
}
