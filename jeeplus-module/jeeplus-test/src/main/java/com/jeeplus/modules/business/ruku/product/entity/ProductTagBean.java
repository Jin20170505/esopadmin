package com.jeeplus.modules.business.ruku.product.entity;

/**
 * 标签·打印
 */
public class ProductTagBean {
    private String cinvcode;
    private String cinvname;
    private String cinvstd;
    private String numunit;
    private String date;

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

    public String getNumunit() {
        return numunit;
    }

    public ProductTagBean setNumunit(String numunit) {
        this.numunit = numunit;
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
