package com.jeeplus.modules.api.bean.ruku;

public class ProductRuKuBean {
    private String bgid; // 报工单ID
    private String batchno; // 生产批号
    private String bgcode; // 报工单号
    private String sccode; // 生产订单号
    private String scline; // 生产订单行号
    private Double num; // 可报数量
    private String cinvcode;
    private String cinvname;
    private String cinvstd;
    private String unit;

    public String getBgid() {
        return bgid;
    }

    public ProductRuKuBean setBgid(String bgid) {
        this.bgid = bgid;
        return this;
    }

    public String getBatchno() {
        return batchno;
    }

    public ProductRuKuBean setBatchno(String batchno) {
        this.batchno = batchno;
        return this;
    }

    public String getBgcode() {
        return bgcode;
    }

    public ProductRuKuBean setBgcode(String bgcode) {
        this.bgcode = bgcode;
        return this;
    }

    public String getSccode() {
        return sccode;
    }

    public ProductRuKuBean setSccode(String sccode) {
        this.sccode = sccode;
        return this;
    }

    public String getScline() {
        return scline;
    }

    public ProductRuKuBean setScline(String scline) {
        this.scline = scline;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public ProductRuKuBean setNum(Double num) {
        this.num = num;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public ProductRuKuBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public ProductRuKuBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public ProductRuKuBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public ProductRuKuBean setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
