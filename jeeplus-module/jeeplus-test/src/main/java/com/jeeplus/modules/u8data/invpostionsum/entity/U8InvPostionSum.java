package com.jeeplus.modules.u8data.invpostionsum.entity;

import com.jeeplus.core.persistence.DataEntity;

public class U8InvPostionSum extends DataEntity<U8InvPostionSum> {
    private String cwhcode; // 仓库编码
    private String cwhname; // 仓库名称
    private String cPosCode; // 货位编码
    private String cinvcode; // 存货编码
    private String cinvname; // 存货名称
    private String cinvstd; // 规格型号
    private String cinvtype; // 存货大类编码
    private Double iquantity; // 现存数量
    private String cbatch; // 批号
    private String dMadeDate; // 生产日期
    private String cMassUnit;//保质期单位
    private String iMassDate; // 保质期天数
    private String iMassDates; // 保质期天数(含单位文本)
    private String dVDate; // 失效日期
    private String cExpirationdate;// 有效期至

    public String getCwhcode() {
        return cwhcode;
    }

    public U8InvPostionSum setCwhcode(String cwhcode) {
        this.cwhcode = cwhcode;
        return this;
    }

    public String getCwhname() {
        return cwhname;
    }

    public U8InvPostionSum setCwhname(String cwhname) {
        this.cwhname = cwhname;
        return this;
    }

    public String getcPosCode() {
        return cPosCode;
    }

    public U8InvPostionSum setcPosCode(String cPosCode) {
        this.cPosCode = cPosCode;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public U8InvPostionSum setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public U8InvPostionSum setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public U8InvPostionSum setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getCinvtype() {
        return cinvtype;
    }

    public U8InvPostionSum setCinvtype(String cinvtype) {
        this.cinvtype = cinvtype;
        return this;
    }

    public Double getIquantity() {
        if(null==iquantity){
            iquantity=0.0;
        }
        return iquantity;
    }

    public U8InvPostionSum setIquantity(Double iquantity) {
        this.iquantity = iquantity;
        return this;
    }

    public String getCbatch() {
        return cbatch;
    }

    public U8InvPostionSum setCbatch(String cbatch) {
        this.cbatch = cbatch;
        return this;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public U8InvPostionSum setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
        return this;
    }

    public String getcMassUnit() {
        return cMassUnit;
    }

    public U8InvPostionSum setcMassUnit(String cMassUnit) {
        this.cMassUnit = cMassUnit;
        return this;
    }

    public String getiMassDate() {
        return iMassDate;
    }

    public U8InvPostionSum setiMassDate(String iMassDate) {
        this.iMassDate = iMassDate;
        return this;
    }

    public String getiMassDates() {
        return iMassDates;
    }

    public U8InvPostionSum setiMassDates(String iMassDates) {
        this.iMassDates = iMassDates;
        return this;
    }

    public String getdVDate() {
        return dVDate;
    }

    public U8InvPostionSum setdVDate(String dVDate) {
        this.dVDate = dVDate;
        return this;
    }

    public String getcExpirationdate() {
        return cExpirationdate;
    }

    public U8InvPostionSum setcExpirationdate(String cExpirationdate) {
        this.cExpirationdate = cExpirationdate;
        return this;
    }
}
