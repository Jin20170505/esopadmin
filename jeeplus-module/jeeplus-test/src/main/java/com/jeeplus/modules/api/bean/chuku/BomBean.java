package com.jeeplus.modules.api.bean.chuku;

public class BomBean {
    private String bomid;
    private String remarks;
    private Double num;
    private Integer no; // 序号
    private String cinvcode; // 存货编码
    private String cinvname; // 存货名称
    private String cinvstd;// 规格型号
    private String scyid; // 生产bom子件ID
    private String unit;

    public Integer getNo() {
        return no;
    }

    public BomBean setNo(Integer no) {
        this.no = no;
        return this;
    }

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

    public String getCinvstd() {
        return cinvstd;
    }

    public BomBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getScyid() {
        return scyid;
    }

    public BomBean setScyid(String scyid) {
        this.scyid = scyid;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public BomBean setNum(Double num) {
        this.num = num;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public BomBean setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
