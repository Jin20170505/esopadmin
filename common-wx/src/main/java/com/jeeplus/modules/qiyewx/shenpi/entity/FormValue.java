package com.jeeplus.modules.qiyewx.shenpi.entity;

import com.jeeplus.modules.qiyewx.shenpi.entity.control.*;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 控件值 ，包含了申请人在各种类型控件中输入的值，不同控件有不同的值，
 */
public class FormValue {
    /**  人员  */
    private List<Member> members;
    /** 部门 */
    private List<Department> departments;
    /** 文件 */
    private List<FileItem> files;
    /** 假勤 */
    private Vacation vacation;
    private Attendance attendance;
    /** control为control为text 或  textarea */
    private String text;
    /** 补打卡 */
    private PunchCorrection punch_correction;

    private DateTimeControl date;

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<FileItem> getFiles() {
        return files;
    }

    public void setFiles(List<FileItem> files) {
        this.files = files;
    }

    public Vacation getVacation() {
        return vacation;
    }

    public void setVacation(Vacation vacation) {
        this.vacation = vacation;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PunchCorrection getPunch_correction() {
        return punch_correction;
    }

    public void setPunch_correction(PunchCorrection punch_correction) {
        this.punch_correction = punch_correction;
    }

    public DateTimeControl getDate() {
        return date;
    }

    public void setDate(DateTimeControl date) {
        this.date = date;
    }
}
