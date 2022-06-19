package com.jeeplus.modules.baobiao.entity;

import java.io.Serializable;

/**
 * 车间生产报工
 */
public class CheJianShengChanBaoBiao implements Serializable {
    private Integer no; // 序号
    private String cinvcode; // 产品编码
    private String cinvname; // 产品名称
    private Double nodonum; // 未做数量（系统订单）
    private String daynum; // 每日产能
    private Double days; // 消耗天数 大于7 则需要标红
    private String remarks;// 备注

    public String getRemarks() {
        return remarks;
    }

    public CheJianShengChanBaoBiao setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Integer getNo() {
        return no;
    }

    public CheJianShengChanBaoBiao setNo(Integer no) {
        this.no = no;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public CheJianShengChanBaoBiao setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public CheJianShengChanBaoBiao setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public Double getNodonum() {
        return nodonum;
    }

    public CheJianShengChanBaoBiao setNodonum(Double nodonum) {
        this.nodonum = nodonum;
        return this;
    }

    public String getDaynum() {
        return daynum;
    }

    public CheJianShengChanBaoBiao setDaynum(String daynum) {
        this.daynum = daynum;
        return this;
    }

    public Double getDays() {
        return days;
    }

    public CheJianShengChanBaoBiao setDays(Double days) {
        this.days = days;
        return this;
    }
}
