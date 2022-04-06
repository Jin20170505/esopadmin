package com.jeeplus.modules.qiyewx.daka.entity.daka_month;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: (打卡月报表数据)加班情况
 */
public class OverworkInfo {
    /** 工作日加班时长 */
    private Long workday_over_sec;
    /** 节假日加班时长 */
    private Long holidays_over_sec;
    /** 休息日加班时长 */
    private Long restdays_over_sec;

    public Long getWorkday_over_sec() {
        return workday_over_sec;
    }

    public void setWorkday_over_sec(Long workday_over_sec) {
        this.workday_over_sec = workday_over_sec;
    }

    public Long getHolidays_over_sec() {
        return holidays_over_sec;
    }

    public void setHolidays_over_sec(Long holidays_over_sec) {
        this.holidays_over_sec = holidays_over_sec;
    }

    public Long getRestdays_over_sec() {
        return restdays_over_sec;
    }

    public void setRestdays_over_sec(Long restdays_over_sec) {
        this.restdays_over_sec = restdays_over_sec;
    }
}
