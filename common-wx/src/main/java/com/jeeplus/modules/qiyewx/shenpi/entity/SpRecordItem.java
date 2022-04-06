package com.jeeplus.modules.qiyewx.shenpi.entity;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 审批节点详情
 */
public class SpRecordItem {
    /** 审批节点状态：1-审批中；2-已同意；3-已驳回；4-已转审；11-已退回 */
    private Integer sp_status;
    /** 	节点审批方式：1-或签；2-会签 */
    private Integer approverattr;
    /** 审批节点详情,一个审批节点有多个审批人 */
    private List<SpItemDetail> details;

    public Integer getSp_status() {
        return sp_status;
    }

    public void setSp_status(Integer sp_status) {
        this.sp_status = sp_status;
    }

    public Integer getApproverattr() {
        return approverattr;
    }

    public void setApproverattr(Integer approverattr) {
        this.approverattr = approverattr;
    }

    public List<SpItemDetail> getDetails() {
        return details;
    }

    public void setDetails(List<SpItemDetail> details) {
        this.details = details;
    }
}
