package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 时长组件
 */
public class DateRange {
    /** 时间展示类型：halfday-日期；hour-日期+时间 */
    private String type;
    /** 开始时间,unix时间戳 */
    private Long  new_begin;
    /** 结束时间,unix时间戳 */
    private Long new_end;
    /** 	时长范围， 单位秒  */
    private Long new_duration;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNew_begin() {
        return new_begin;
    }

    public void setNew_begin(Long new_begin) {
        this.new_begin = new_begin;
    }

    public Long getNew_end() {
        return new_end;
    }

    public void setNew_end(Long new_end) {
        this.new_end = new_end;
    }

    public Long getNew_duration() {
        return new_duration;
    }

    public void setNew_duration(Long new_duration) {
        this.new_duration = new_duration;
    }
}
