package com.jeeplus.modules.qiyewx.daka.entity.daka_month;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: (打卡月报表数据)	异常状态统计信息
 */
public class ExceptionInfo {
    /** 异常类型：1-迟到；2-早退；3-缺卡；4-旷工；5-地点异常；6-设备异常 */
    private Integer exception;
    /** 	异常次数，为统计周期内每日此异常次数之和  */
    private Integer count;
    /** 异常时长（迟到/早退/旷工才有值），为统计周期内每日此异常时长之和  */
    private Long duration;

    public Integer getException() {
        return exception;
    }

    public void setException(Integer exception) {
        this.exception = exception;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
}
