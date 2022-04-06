package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 假勤组件-请假组件（control参数为Vacation）
 */
public class Vacation {
    /** 	请假类型，所选选项与假期管理关联，为假期管理中的假期类型 */
    private Selector selector;
    /** 假勤组件 */
    private Attendance attendance;

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }
}
