package com.jeeplus.modules.api.bean.chuku;

/**
 * 领料出库 材料出库
 */
public class LingLiaoBean {
    private String bgid;
    private String bgcode;
    private String schid;
    private String sccode;
    private String scline;
    private String cinvcode;
    private String cinvname;
    private String cinvstd;
    private Double num;
    private String remarks;
    private Double gdnum;

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

    public String getSchid() {
        return schid;
    }

    public LingLiaoBean setSchid(String schid) {
        this.schid = schid;
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

    public Double getGdnum() {
        return gdnum;
    }

    public LingLiaoBean setGdnum(Double gdnum) {
        this.gdnum = gdnum;
        return this;
    }
}
