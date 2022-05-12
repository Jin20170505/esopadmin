package com.jeeplus.modules.api.bean.baogong;

import java.util.ArrayList;
import java.util.List;

public class BaoGongBean {
    private String bgid; // 报工单ID
    private String bgcode; // 报工单号
    private String sccode; // 生产订单号
    private String scline; // 生产订单行号
    private Double gdnum; // 工单数量
    private String cinvcode;
    private String cinvname;
    private String cinvstd;

    private List<BaoGongItem> baoGongItems = new ArrayList<>();
    public String getBgid() {
        return bgid;
    }

    public BaoGongBean setBgid(String bgid) {
        this.bgid = bgid;
        return this;
    }

    public String getBgcode() {
        return bgcode;
    }

    public BaoGongBean setBgcode(String bgcode) {
        this.bgcode = bgcode;
        return this;
    }

    public String getSccode() {
        return sccode;
    }

    public BaoGongBean setSccode(String sccode) {
        this.sccode = sccode;
        return this;
    }

    public String getScline() {
        return scline;
    }

    public BaoGongBean setScline(String scline) {
        this.scline = scline;
        return this;
    }

    public Double getGdnum() {
        return gdnum;
    }

    public BaoGongBean setGdnum(Double gdnum) {
        this.gdnum = gdnum;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public BaoGongBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public BaoGongBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public BaoGongBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public List<BaoGongItem> getBaoGongItems() {
        return baoGongItems;
    }

    public BaoGongBean setBaoGongItems(List<BaoGongItem> baoGongItems) {
        this.baoGongItems = baoGongItems;
        return this;
    }
}
