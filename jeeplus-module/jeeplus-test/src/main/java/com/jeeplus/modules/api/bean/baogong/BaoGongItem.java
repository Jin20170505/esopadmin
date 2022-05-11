package com.jeeplus.modules.api.bean.baogong;

/**
 * 报工工序
 */
public class BaoGongItem {
    private String bghid; // 报工行ID
    private String site; // 工序
    private Double dbnum; // 待报数量

    public String getBghid() {
        return bghid;
    }

    public BaoGongItem setBghid(String bghid) {
        this.bghid = bghid;
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
}
