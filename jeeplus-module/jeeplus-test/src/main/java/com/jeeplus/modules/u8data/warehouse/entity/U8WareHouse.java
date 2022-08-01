package com.jeeplus.modules.u8data.warehouse.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.u8data.prouting.entity.U8Prouting;

import java.util.Date;

/**
 * 仓库档案
 */
public class U8WareHouse extends DataEntity<U8WareHouse> {
    private Date dModifyDate;//  	修改日期
    private String bProxyWh;//		代管仓
    private Date dWhEndDate;// 		停用日期
    private String cWhCode;//  		仓库编码
    private String cWhName;//  		仓库名称
    private String cWhAddress;//  	仓库地址
    private String cWhPhone;//  		电话 ,
    private String cWhPerson;//  		负责人 ,
    private String bWhPos;//  		是否货位管理 ,
    private String iFrequency;//  	盘点周期 ,
    private String cFrequency;//  	盘点周期单位

    private Date modifyTime;
    private Date nowTime;
    public Date getdModifyDate() {
        return dModifyDate;
    }

    public U8WareHouse setdModifyDate(Date dModifyDate) {
        this.dModifyDate = dModifyDate;
        return this;
    }

    public String getbProxyWh() {
        return bProxyWh;
    }

    public U8WareHouse setbProxyWh(String bProxyWh) {
        this.bProxyWh = bProxyWh;
        return this;
    }

    public Date getdWhEndDate() {
        return dWhEndDate;
    }

    public U8WareHouse setdWhEndDate(Date dWhEndDate) {
        this.dWhEndDate = dWhEndDate;
        return this;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public U8WareHouse setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getcWhName() {
        return cWhName;
    }

    public U8WareHouse setcWhName(String cWhName) {
        this.cWhName = cWhName;
        return this;
    }

    public String getcWhAddress() {
        return cWhAddress;
    }

    public U8WareHouse setcWhAddress(String cWhAddress) {
        this.cWhAddress = cWhAddress;
        return this;
    }

    public String getcWhPhone() {
        return cWhPhone;
    }

    public U8WareHouse setcWhPhone(String cWhPhone) {
        this.cWhPhone = cWhPhone;
        return this;
    }

    public String getcWhPerson() {
        return cWhPerson;
    }

    public U8WareHouse setcWhPerson(String cWhPerson) {
        this.cWhPerson = cWhPerson;
        return this;
    }

    public String getbWhPos() {
        return bWhPos;
    }

    public U8WareHouse setbWhPos(String bWhPos) {
        this.bWhPos = bWhPos;
        return this;
    }

    public String getiFrequency() {
        return iFrequency;
    }

    public U8WareHouse setiFrequency(String iFrequency) {
        this.iFrequency = iFrequency;
        return this;
    }

    public String getcFrequency() {
        return cFrequency;
    }

    public U8WareHouse setcFrequency(String cFrequency) {
        this.cFrequency = cFrequency;
        return this;
    }
    public Date getModifyTime() {
        return modifyTime;
    }

    public U8WareHouse setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
        return this;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public U8WareHouse setNowTime(Date nowTime) {
        this.nowTime = nowTime;
        return this;
    }
}
