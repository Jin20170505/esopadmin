package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 补卡组件
 */
public class PunchCorrection {
    /** 异常状态说明 */
    private String state;
    /** 补卡时间，Unix时间戳  */
    private Long time;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
