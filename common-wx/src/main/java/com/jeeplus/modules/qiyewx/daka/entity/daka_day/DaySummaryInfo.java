package com.jeeplus.modules.qiyewx.daka.entity.daka_day;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: 汇总信息
 */
public class DaySummaryInfo {
    /** 当日打卡次数 */
    private Integer checkin_count;
    /** 	当日实际工作时长，单位：秒 */
    private Integer regular_work_sec;
    /** 	当日标准工作时长，单位：秒 */
    private Integer standard_work_sec;
    /** 当日最早打卡时间 */
    private Long earliest_time;
    /** 	当日最晚打卡时间 */
    private Long lastest_time;

    public Integer getCheckin_count() {
        return checkin_count;
    }

    public void setCheckin_count(Integer checkin_count) {
        this.checkin_count = checkin_count;
    }

    public Integer getRegular_work_sec() {
        return regular_work_sec;
    }

    public void setRegular_work_sec(Integer regular_work_sec) {
        this.regular_work_sec = regular_work_sec;
    }

    public Integer getStandard_work_sec() {
        return standard_work_sec;
    }

    public void setStandard_work_sec(Integer standard_work_sec) {
        this.standard_work_sec = standard_work_sec;
    }

    public Long getEarliest_time() {
        return earliest_time;
    }

    public void setEarliest_time(Long earliest_time) {
        this.earliest_time = earliest_time;
    }

    public Long getLastest_time() {
        return lastest_time;
    }

    public void setLastest_time(Long lastest_time) {
        this.lastest_time = lastest_time;
    }
}
