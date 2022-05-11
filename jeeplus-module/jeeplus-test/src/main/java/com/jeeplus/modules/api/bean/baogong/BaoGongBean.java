package com.jeeplus.modules.api.bean.baogong;

import java.util.ArrayList;
import java.util.List;

public class BaoGongBean {
    private String bgid; // 报工单ID
    private String bgcode; // 报工单号
    private String sccode; // 生产订单号
    private String scline; // 生产订单行号
    private String site; // 工序
    private Double gdnum; // 工单数量
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

    public String getSite() {
        return site;
    }

    public BaoGongBean setSite(String site) {
        this.site = site;
        return this;
    }

    public Double getGdnum() {
        return gdnum;
    }

    public BaoGongBean setGdnum(Double gdnum) {
        this.gdnum = gdnum;
        return this;
    }
}
