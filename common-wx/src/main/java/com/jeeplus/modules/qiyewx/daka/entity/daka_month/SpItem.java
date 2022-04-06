package com.jeeplus.modules.qiyewx.daka.entity.daka_month;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: (打卡月报表数据)假勤统计信息
 */
public class SpItem {
    /** 假勤类型：1-请假；2-补卡；3-出差；4-外出；100-外勤 */
    private Integer type;
    /** 具体请假类型，当type为1请假时，具体的请假类型id，可通过审批相关接口获取假期详情  */
    private Integer vacation_id;
    /** 假勤次数，为统计周期内每日此假勤发生次数之和 */
    private Integer count;
    /** 假勤时长，为统计周期内每日此假勤发生时长之和，时长单位为天直接除以86400即为天数，单位为小时直接除以3600即为小时数 */
    private Long duration;
    /** 时长单位：0-按天 1-按小时  */
    private Integer time_type;
    /** 统计项名称 */
    private String name;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getVacation_id() {
        return vacation_id;
    }

    public void setVacation_id(Integer vacation_id) {
        this.vacation_id = vacation_id;
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

    public Integer getTime_type() {
        return time_type;
    }

    public void setTime_type(Integer time_type) {
        this.time_type = time_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
