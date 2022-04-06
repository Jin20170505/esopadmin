package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 假勤组件-出差/外出/加班组件（control参数为Attendance）
 */
public class Attendance {
    /** 时间选择 */
    private DateRange date_range;
    /** 1-请假；2-补卡；3-出差；4-外出；5-加班 */
    private Integer type;

    public DateRange getDate_range() {
        return date_range;
    }

    public void setDate_range(DateRange date_range) {
        this.date_range = date_range;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
