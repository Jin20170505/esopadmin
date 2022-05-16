package com.jeeplus.modules.api.bean.chuku;

public class BomBean {
    private String bomid;
    private String remarks;
    private Double gdnum;
    private String cinvcode;
    private String cinvname;

    public String getBomid() {
        return bomid;
    }

    public BomBean setBomid(String bomid) {
        this.bomid = bomid;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public BomBean setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Double getGdnum() {
        return gdnum;
    }

    public BomBean setGdnum(Double gdnum) {
        this.gdnum = gdnum;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public BomBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public BomBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }
}
