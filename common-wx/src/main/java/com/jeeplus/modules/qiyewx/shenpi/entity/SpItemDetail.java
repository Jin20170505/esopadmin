package com.jeeplus.modules.qiyewx.shenpi.entity;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 	审批节点详情,一个审批节点有多个审批人
 */
public class SpItemDetail {
    /** 分支审批人 approver.userid */
    private String approver;
    /** 审批意见 */
    private String speech;
    /** 分支审批人审批状态：1-审批中；2-已同意；3-已驳回；4-已转审；11-已退回 */
    private Integer sp_status;
    /** 节点分支审批人审批操作时间戳，0表示未操作  */
    private Long sptime;
    /** 节点分支审批人审批意见附件  */
    private String media_id;

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    public Integer getSp_status() {
        return sp_status;
    }

    public void setSp_status(Integer sp_status) {
        this.sp_status = sp_status;
    }

    public Long getSptime() {
        return sptime;
    }

    public void setSptime(Long sptime) {
        this.sptime = sptime;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}
