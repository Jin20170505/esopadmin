package com.jeeplus.modules.u8data.customer.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 客户档案
 */
public class U8Customer extends DataEntity<U8Customer> {
    private Date dModifyDate;// 	修改日期
    private Date dEndDate;// 		停用日期
    private String cCusCode;//  		客户编码
    private String cCusName;//  		客户名称
    private String cCusAddress;//  	客户地址
    private String cCusPhone;//  		电话
    private String cCusPerson;//  	负责人
    private String cMemo;//			备注

    public Date getdModifyDate() {
        return dModifyDate;
    }

    public U8Customer setdModifyDate(Date dModifyDate) {
        this.dModifyDate = dModifyDate;
        return this;
    }

    public Date getdEndDate() {
        return dEndDate;
    }

    public U8Customer setdEndDate(Date dEndDate) {
        this.dEndDate = dEndDate;
        return this;
    }

    public String getcCusCode() {
        return cCusCode;
    }

    public U8Customer setcCusCode(String cCusCode) {
        this.cCusCode = cCusCode;
        return this;
    }

    public String getcCusName() {
        return cCusName;
    }

    public U8Customer setcCusName(String cCusName) {
        this.cCusName = cCusName;
        return this;
    }

    public String getcCusAddress() {
        return cCusAddress;
    }

    public U8Customer setcCusAddress(String cCusAddress) {
        this.cCusAddress = cCusAddress;
        return this;
    }

    public String getcCusPhone() {
        return cCusPhone;
    }

    public U8Customer setcCusPhone(String cCusPhone) {
        this.cCusPhone = cCusPhone;
        return this;
    }

    public String getcCusPerson() {
        return cCusPerson;
    }

    public U8Customer setcCusPerson(String cCusPerson) {
        this.cCusPerson = cCusPerson;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8Customer setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }
}
