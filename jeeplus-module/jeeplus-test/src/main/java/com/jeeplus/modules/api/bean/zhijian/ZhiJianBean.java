package com.jeeplus.modules.api.bean.zhijian;

import com.jeeplus.modules.api.bean.baogong.BaoGongItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 质检Bean
 */
public class ZhiJianBean {
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
    private List<BaoGongItem> baoGongItems = new ArrayList<>();

    public String getBgid() {
        return bgid;
    }

    public ZhiJianBean setBgid(String bgid) {
        this.bgid = bgid;
        return this;
    }

    public String getBatchno() {
        if(batchno==null){
            batchno = "";
        }
        return batchno;
    }

    public ZhiJianBean setBatchno(String batchno) {
        this.batchno = batchno;
        return this;
    }

    public String getBgcode() {
        return bgcode;
    }

    public ZhiJianBean setBgcode(String bgcode) {
        this.bgcode = bgcode;
        return this;
    }

    public String getSccode() {
        return sccode;
    }

    public ZhiJianBean setSccode(String sccode) {
        this.sccode = sccode;
        return this;
    }

    public String getScline() {
        return scline;
    }

    public ZhiJianBean setScline(String scline) {
        this.scline = scline;
        return this;
    }

    public Double getNum() {
        return num;
    }

    public ZhiJianBean setNum(Double num) {
        this.num = num;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public ZhiJianBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public ZhiJianBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public ZhiJianBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public ZhiJianBean setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public List<BaoGongItem> getBaoGongItems() {
        return baoGongItems;
    }

    public ZhiJianBean setBaoGongItems(List<BaoGongItem> baoGongItems) {
        this.baoGongItems = baoGongItems;
        return this;
    }
}
