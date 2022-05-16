package com.jeeplus.modules.api.bean.beiliao;

import java.util.ArrayList;
import java.util.List;

/**
 * 备料
 */
public class BeiLiaoBean {
    private String schid;
    private String sccode;
    private String scline;
    private String cinvcode;
    private String cinvname;
    private String cinvstd;
    private Double num;
    private String unit;
    private String remarks;
    private List<BeiLiaoItem> beiLiaoItems=new ArrayList<>();
    public String getUnit() {
        return unit;
    }

    public BeiLiaoBean setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getSchid() {
        return schid;
    }

    public BeiLiaoBean setSchid(String schid) {
        this.schid = schid;
        return this;
    }

    public String getSccode() {
        return sccode;
    }

    public BeiLiaoBean setSccode(String sccode) {
        this.sccode = sccode;
        return this;
    }

    public String getScline() {
        return scline;
    }

    public BeiLiaoBean setScline(String scline) {
        this.scline = scline;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public BeiLiaoBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public BeiLiaoBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public BeiLiaoBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public BeiLiaoBean setNum(Double num) {
        this.num = num;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public BeiLiaoBean setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public List<BeiLiaoItem> getBeiLiaoItems() {
        return beiLiaoItems;
    }

    public BeiLiaoBean setBeiLiaoItems(List<BeiLiaoItem> beiLiaoItems) {
        this.beiLiaoItems = beiLiaoItems;
        return this;
    }
}
