package com.jeeplus.modules.qiyewx.daka.entity.daka_day;

/**
 * 加班信息
 */
public class OtInfo {
    /**
     * 状态：0-无加班；1-正常；2-缺时长
     */
    private Integer ot_status;
    /**
     * 	加班时长(单位s)
     */
    private Integer ot_duration;

    public Integer getOt_status() {
        return ot_status;
    }

    public void setOt_status(Integer ot_status) {
        this.ot_status = ot_status;
    }

    public Integer getOt_duration() {
        return ot_duration;
    }

    public void setOt_duration(Integer ot_duration) {
        this.ot_duration = ot_duration;
    }
}
