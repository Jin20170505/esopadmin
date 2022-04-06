package com.jeeplus.modules.qiyewx.daka.entity.daka_month;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: (打卡月报表数据)汇总信息
 */
public class SummaryInfo {
    /** 应打卡天数 */
    private Integer work_days;
    /** 正常天数 */
    private Integer regular_days;
    /** 异常天数 */
    private Integer except_days;
    /** 实际工作时长，为统计周期每日实际工作时长之和  */
    private Long regular_work_sec;
    /** 标准工作时长，为统计周期每日标准工作时长之和 */
    private Long standard_work_sec;

    public Integer getWork_days() {
        return work_days;
    }

    public void setWork_days(Integer work_days) {
        this.work_days = work_days;
    }

    public Integer getRegular_days() {
        return regular_days;
    }

    public void setRegular_days(Integer regular_days) {
        this.regular_days = regular_days;
    }

    public Integer getExcept_days() {
        return except_days;
    }

    public void setExcept_days(Integer except_days) {
        this.except_days = except_days;
    }

    public Long getRegular_work_sec() {
        return regular_work_sec;
    }

    public void setRegular_work_sec(Long regular_work_sec) {
        this.regular_work_sec = regular_work_sec;
    }

    public Long getStandard_work_sec() {
        return standard_work_sec;
    }

    public void setStandard_work_sec(Long standard_work_sec) {
        this.standard_work_sec = standard_work_sec;
    }
}
