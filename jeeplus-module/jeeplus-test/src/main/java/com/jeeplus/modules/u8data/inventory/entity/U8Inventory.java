package com.jeeplus.modules.u8data.inventory.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class U8Inventory extends DataEntity<U8Inventory> {
    private String cInvAddCode; // 存货代码
    private String cInvCCode; // 存货大类编码
    private String cInvCName; // 存货大类名称
    private String cInvCode; // 存货编码
    private String cInvName; // 存货名称
    private String cInvStd; // 存货规格
    private String cComUnitCode; // 单位编码
    private String cComUnitName; // 单位名称
    private String cDefWareHouse; // 仓库编码
    private String cWhName; // 仓库名称
    private String bPropertyCheck; // 是否质检
    private String bProxyForeign; // 是否委外
    private String daynum; // 每日产量
    private Date dSDate;  // 启用日期
    private Date dEDate; // 停用日期

    public String getcInvAddCode() {
        return cInvAddCode;
    }

    public U8Inventory setcInvAddCode(String cInvAddCode) {
        this.cInvAddCode = cInvAddCode;
        return this;
    }

    public String getcInvCCode() {
        return cInvCCode;
    }

    public U8Inventory setcInvCCode(String cInvCCode) {
        this.cInvCCode = cInvCCode;
        return this;
    }

    public String getcInvCName() {
        return cInvCName;
    }

    public U8Inventory setcInvCName(String cInvCName) {
        this.cInvCName = cInvCName;
        return this;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public U8Inventory setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    public String getcInvName() {
        return cInvName;
    }

    public U8Inventory setcInvName(String cInvName) {
        this.cInvName = cInvName;
        return this;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public U8Inventory setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
        return this;
    }

    public String getcComUnitCode() {
        return cComUnitCode;
    }

    public U8Inventory setcComUnitCode(String cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8Inventory setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }

    public String getcDefWareHouse() {
        return cDefWareHouse;
    }

    public U8Inventory setcDefWareHouse(String cDefWareHouse) {
        this.cDefWareHouse = cDefWareHouse;
        return this;
    }

    public String getcWhName() {
        return cWhName;
    }

    public U8Inventory setcWhName(String cWhName) {
        this.cWhName = cWhName;
        return this;
    }

    public String getbPropertyCheck() {
        return bPropertyCheck;
    }

    public U8Inventory setbPropertyCheck(String bPropertyCheck) {
        this.bPropertyCheck = bPropertyCheck;
        return this;
    }

    public String getbProxyForeign() {
        return bProxyForeign;
    }

    public U8Inventory setbProxyForeign(String bProxyForeign) {
        this.bProxyForeign = bProxyForeign;
        return this;
    }

    public String getDaynum() {
        return daynum;
    }

    public U8Inventory setDaynum(String daynum) {
        this.daynum = daynum;
        return this;
    }

    public Date getdSDate() {
        return dSDate;
    }

    public U8Inventory setdSDate(Date dSDate) {
        this.dSDate = dSDate;
        return this;
    }

    public Date getdEDate() {
        return dEDate;
    }

    public U8Inventory setdEDate(Date dEDate) {
        this.dEDate = dEDate;
        return this;
    }
}
