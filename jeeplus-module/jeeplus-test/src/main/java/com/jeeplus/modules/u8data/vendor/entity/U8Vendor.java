package com.jeeplus.modules.u8data.vendor.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 供应商档案
 */
public class U8Vendor extends DataEntity<U8Vendor> {
    private Date dModifyDate;//  	修改日期 ,
    private Date dEndDate;// 		停用日期 ,
    private String cVenCode;//		供应商编码 ,
    private String cVenName;// 		供应商名称 ,
    private String cVenAddress;//  	供应商地址 ,
    private String cVenPhone;//  		电话 ,
    private String cVenPerson;//		联系人 ,
    private String cMemo;// 			备注
    private Date modifyTime;
    private Date nowTime;

    public Date getdModifyDate() {
        return dModifyDate;
    }

    public U8Vendor setdModifyDate(Date dModifyDate) {
        this.dModifyDate = dModifyDate;
        return this;
    }

    public Date getdEndDate() {
        return dEndDate;
    }

    public U8Vendor setdEndDate(Date dEndDate) {
        this.dEndDate = dEndDate;
        return this;
    }

    public String getcVenCode() {
        return cVenCode;
    }

    public U8Vendor setcVenCode(String cVenCode) {
        this.cVenCode = cVenCode;
        return this;
    }

    public String getcVenName() {
        return cVenName;
    }

    public U8Vendor setcVenName(String cVenName) {
        this.cVenName = cVenName;
        return this;
    }

    public String getcVenAddress() {
        return cVenAddress;
    }

    public U8Vendor setcVenAddress(String cVenAddress) {
        this.cVenAddress = cVenAddress;
        return this;
    }

    public String getcVenPhone() {
        return cVenPhone;
    }

    public U8Vendor setcVenPhone(String cVenPhone) {
        this.cVenPhone = cVenPhone;
        return this;
    }

    public String getcVenPerson() {
        return cVenPerson;
    }

    public U8Vendor setcVenPerson(String cVenPerson) {
        this.cVenPerson = cVenPerson;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8Vendor setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public U8Vendor setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public U8Vendor setNowTime(Date nowTime) {
        this.nowTime = nowTime;
        return this;
    }
}
