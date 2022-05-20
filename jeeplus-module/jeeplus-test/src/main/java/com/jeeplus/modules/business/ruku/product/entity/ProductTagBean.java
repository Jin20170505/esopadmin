package com.jeeplus.modules.business.ruku.product.entity;

/**
 * 标签·打印
 */
public class ProductTagBean {
    private String batchno;
    private String qrcode;
    private String cinvcode;
    private String cinvname;
    private String cinvstd;
    private String num;
    private String unit;
    private String date;

    public String getBatchno() {
        return batchno;
    }

    public ProductTagBean setBatchno(String batchno) {
        this.batchno = batchno;
        return this;
    }

    public String getQrcode() {
        return qrcode;
    }

    public ProductTagBean setQrcode(String qrcode) {
        this.qrcode = qrcode;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public ProductTagBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public ProductTagBean setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public ProductTagBean setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getNum() {
        return num;
    }

    public ProductTagBean setNum(String num) {
        this.num = num;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public ProductTagBean setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ProductTagBean setDate(String date) {
        this.date = date;
        return this;
    }
}
