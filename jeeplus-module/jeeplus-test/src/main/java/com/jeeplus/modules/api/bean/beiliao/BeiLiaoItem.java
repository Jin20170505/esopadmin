package com.jeeplus.modules.api.bean.beiliao;

public class BeiLiaoItem {
    private String no;
    private String cinvcode;
    private String cinvname;
    private String cinvstd;
    private Double num;
    private String unit;

    public String getNo() {
        return no;
    }

    public BeiLiaoItem setNo(String no) {
        this.no = no;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public BeiLiaoItem setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public BeiLiaoItem setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public BeiLiaoItem setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public BeiLiaoItem setNum(Double num) {
        this.num = num;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public BeiLiaoItem setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
