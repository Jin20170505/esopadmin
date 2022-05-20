package com.jeeplus.modules.u8data.operation.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 标准工序
 */
public class U8Operation extends DataEntity<U8Operation> {
    private String operationid; // ID
    private String opCode; // 编码
    private String description; // 名称
    private String remark; // 备注
    private String bffFlag;// 倒冲工序
    private String feeFlag;// 计费点
    private String reportFlag; // 报告点

    public String getOperationid() {
        return operationid;
    }

    public U8Operation setOperationid(String operationid) {
        this.operationid = operationid;
        return this;
    }

    public String getOpCode() {
        return opCode;
    }

    public U8Operation setOpCode(String opCode) {
        this.opCode = opCode;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public U8Operation setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public U8Operation setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getBffFlag() {
        return bffFlag;
    }

    public U8Operation setBffFlag(String bffFlag) {
        this.bffFlag = bffFlag;
        return this;
    }

    public String getFeeFlag() {
        return feeFlag;
    }

    public U8Operation setFeeFlag(String feeFlag) {
        this.feeFlag = feeFlag;
        return this;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public U8Operation setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
        return this;
    }
}
