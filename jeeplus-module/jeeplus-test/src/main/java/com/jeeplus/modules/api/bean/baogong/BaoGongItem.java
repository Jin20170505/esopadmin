package com.jeeplus.modules.api.bean.baogong;

/**
 * 报工工序
 */
public class BaoGongItem {
    private String bghid; // 报工行ID
    private String siteid; // 工序ID
    private String site; // 工序
    private Double dbnum; // 待报数量
    private Double hglv; // 合格率
    public String getBghid() {
        return bghid;
    }

    public BaoGongItem setBghid(String bghid) {
        this.bghid = bghid;
        return this;
    }

    public String getSiteid() {
        return siteid;
    }

    public BaoGongItem setSiteid(String siteid) {
        this.siteid = siteid;
        return this;
    }

    public String getSite() {
        return site;
    }

    public BaoGongItem setSite(String site) {
        this.site = site;
        return this;
    }

    public Double getDbnum() {
        return dbnum;
    }

    public BaoGongItem setDbnum(Double dbnum) {
        this.dbnum = dbnum;
        return this;
    }

    public Double getHglv() {
        return hglv;
    }

    public BaoGongItem setHglv(Double hglv) {
        this.hglv = hglv;
        return this;
    }
}
