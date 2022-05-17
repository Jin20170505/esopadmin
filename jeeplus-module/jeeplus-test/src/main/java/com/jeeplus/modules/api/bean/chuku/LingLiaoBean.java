package com.jeeplus.modules.api.bean.chuku;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 领料出库 材料出库
 */
public class LingLiaoBean {
    private String bgid; // 报工ID
    private String bgcode; // 报工单号
    private String planid; // 计划ID
    private String plancode; // 计划单号
    private String sccode; // 生产订单号
    private String scline; // 生产订单行号
    private String cinvcode; // 产品编码
    private String cinvname;// 产品名称
    private String cinvstd; // 规格型号
    private Double num;// 数量
    private String unit; // 单位
    private String remarks;// 备注
    // 子件列表
    private List<BomBean> bomList = Lists.newArrayList();
    public String getBgid() {
        return bgid;
    }

    public LingLiaoBean setBgid(String bgid) {
        this.bgid = bgid;
        return this;
    }

    public String getBgcode() {
        return bgcode;
    }

    public LingLiaoBean setBgcode(String bgcode) {
        this.bgcode = bgcode;
        return this;
    }

    public String getPlanid() {
        return planid;
    }

    public LingLiaoBean setPlanid(String planid) {
        this.planid = planid;
        return this;
    }

    public String getPlancode() {
        return plancode;
    }

    public LingLiaoBean setPlancode(String plancode) {
        this.plancode = plancode;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public LingLiaoBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public LingLiaoBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public LingLiaoBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public LingLiaoBean setNum(Double num) {
        this.num = num;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public LingLiaoBean setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public LingLiaoBean setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public List<BomBean> getBomList() {
        return bomList;
    }

    public LingLiaoBean setBomList(List<BomBean> bomList) {
        this.bomList = bomList;
        return this;
    }

    public String getSccode() {
        return sccode;
    }

    public LingLiaoBean setSccode(String sccode) {
        this.sccode = sccode;
        return this;
    }

    public String getScline() {
        return scline;
    }

    public LingLiaoBean setScline(String scline) {
        this.scline = scline;
        return this;
    }
}
