package com.jeeplus.modules.u8data.prouting.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class U8ProutingDetail extends DataEntity<U8ProutingDetail> {
    private String proutingId; // 料品工艺路线资料ID
    private String proutingDId; // 料品工艺路线明细资料ID
    private Integer opSeq; // 工序序号
    private String operationId; // 标准工序ID
    private String description; // 标准工序说明
    private String wcId; // 工作中心代号
    private Date effBegDate; // 生效日期
    private Date effEndDate; // 失效日期
    private String subFlag; // 是否委外工序
    private String lastFlag; // 是否末道工序
    private String define26; // 工时
    private String define27; // 工价
    private String define34; // 日产量
    private String feeFlag; // 是否计费点
    private String bfflag; // 是否倒冲工序
    private Double changeRate; // 换算率
    private String splitFlag; // 允许拆分
    private String planSubFlag; // 是否计划委外工序
    private String deliveryDays; // 交货天数
    private String reportFlag; // 是否报工点
    private String auxUnitCode; // 辅助计量单位

    public String getProutingId() {
        return proutingId;
    }

    public U8ProutingDetail setProutingId(String proutingId) {
        this.proutingId = proutingId;
        return this;
    }

    public String getProutingDId() {
        return proutingDId;
    }

    public U8ProutingDetail setProutingDId(String proutingDId) {
        this.proutingDId = proutingDId;
        return this;
    }

    public Integer getOpSeq() {
        return opSeq;
    }

    public U8ProutingDetail setOpSeq(Integer opSeq) {
        this.opSeq = opSeq;
        return this;
    }

    public String getOperationId() {
        return operationId;
    }

    public U8ProutingDetail setOperationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public U8ProutingDetail setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getWcId() {
        return wcId;
    }

    public U8ProutingDetail setWcId(String wcId) {
        this.wcId = wcId;
        return this;
    }

    public Date getEffBegDate() {
        return effBegDate;
    }

    public U8ProutingDetail setEffBegDate(Date effBegDate) {
        this.effBegDate = effBegDate;
        return this;
    }

    public Date getEffEndDate() {
        return effEndDate;
    }

    public U8ProutingDetail setEffEndDate(Date effEndDate) {
        this.effEndDate = effEndDate;
        return this;
    }

    public String getSubFlag() {
        return subFlag;
    }

    public U8ProutingDetail setSubFlag(String subFlag) {
        this.subFlag = subFlag;
        return this;
    }

    public String getLastFlag() {
        return lastFlag;
    }

    public U8ProutingDetail setLastFlag(String lastFlag) {
        this.lastFlag = lastFlag;
        return this;
    }

    public String getDefine27() {
        return define27;
    }

    public U8ProutingDetail setDefine27(String define27) {
        this.define27 = define27;
        return this;
    }

    public String getDefine26() {
        return define26;
    }

    public U8ProutingDetail setDefine26(String define26) {
        this.define26 = define26;
        return this;
    }

    public String getDefine34() {
        return define34;
    }

    public U8ProutingDetail setDefine34(String define34) {
        this.define34 = define34;
        return this;
    }

    public String getFeeFlag() {
        return feeFlag;
    }

    public U8ProutingDetail setFeeFlag(String feeFlag) {
        this.feeFlag = feeFlag;
        return this;
    }

    public String getBfflag() {
        return bfflag;
    }

    public U8ProutingDetail setBfflag(String bfflag) {
        this.bfflag = bfflag;
        return this;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public U8ProutingDetail setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
        return this;
    }

    public String getSplitFlag() {
        return splitFlag;
    }

    public U8ProutingDetail setSplitFlag(String splitFlag) {
        this.splitFlag = splitFlag;
        return this;
    }

    public String getPlanSubFlag() {
        return planSubFlag;
    }

    public U8ProutingDetail setPlanSubFlag(String planSubFlag) {
        this.planSubFlag = planSubFlag;
        return this;
    }

    public String getDeliveryDays() {
        return deliveryDays;
    }

    public U8ProutingDetail setDeliveryDays(String deliveryDays) {
        this.deliveryDays = deliveryDays;
        return this;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public U8ProutingDetail setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
        return this;
    }

    public String getAuxUnitCode() {
        return auxUnitCode;
    }

    public U8ProutingDetail setAuxUnitCode(String auxUnitCode) {
        this.auxUnitCode = auxUnitCode;
        return this;
    }
}