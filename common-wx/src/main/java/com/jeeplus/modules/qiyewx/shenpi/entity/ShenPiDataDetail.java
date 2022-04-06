package com.jeeplus.modules.qiyewx.shenpi.entity;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 审批详情
 */
public class ShenPiDataDetail {
    private String sp_no;
    private String sp_name;
    private Integer sp_status;
    private String template_id;
    /**  审批申请提交时间,Unix时间戳 */
    private Long apply_time;
    /** 申请人信息 */
    private String applyer; // applyer.userid
    /** 抄送信息，可能有多个抄送节点 */
    private List<String> notifyer; // notifyer.userid
    /** 审批申请数据 */
    private ApplyData apply_data;
    /** 审批流程信息，可能有多个审批节点。 */
    private List<SpRecordItem> sp_record;
    /** 备注信息 */
    private List<SpComment> comments;

    public String getSp_no() {
        return sp_no;
    }

    public void setSp_no(String sp_no) {
        this.sp_no = sp_no;
    }

    public String getSp_name() {
        return sp_name;
    }

    public void setSp_name(String sp_name) {
        this.sp_name = sp_name;
    }

    public Integer getSp_status() {
        return sp_status;
    }

    public void setSp_status(Integer sp_status) {
        this.sp_status = sp_status;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public Long getApply_time() {
        return apply_time;
    }

    public void setApply_time(Long apply_time) {
        this.apply_time = apply_time;
    }

    public String getApplyer() {
        return applyer;
    }

    public void setApplyer(String applyer) {
        this.applyer = applyer;
    }

    public List<String> getNotifyer() {
        return notifyer;
    }

    public void setNotifyer(List<String> notifyer) {
        this.notifyer = notifyer;
    }

    public ApplyData getApply_data() {
        return apply_data;
    }

    public void setApply_data(ApplyData apply_data) {
        this.apply_data = apply_data;
    }

    public List<SpRecordItem> getSp_record() {
        return sp_record;
    }

    public void setSp_record(List<SpRecordItem> sp_record) {
        this.sp_record = sp_record;
    }

    public List<SpComment> getComments() {
        return comments;
    }

    public void setComments(List<SpComment> comments) {
        this.comments = comments;
    }
}
