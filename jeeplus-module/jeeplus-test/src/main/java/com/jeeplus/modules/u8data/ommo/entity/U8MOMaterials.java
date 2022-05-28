package com.jeeplus.modules.u8data.ommo.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 委外用料明细
 */
public class U8MOMaterials extends DataEntity<U8MOMaterials> {
    private String momaterialsID;//	委外用料单子表ID
    private String moDetailsID;//		委外订单子表ID
    private String cWhCode;//			仓库编码
    private String cWhName;//			仓库名称
    private String hw; // 货位
    private String cInvCode;//		子件编码
    private String cInvName;//		存货名称
    private String cInvStd;//		规格型号
    private String cComUnitCode;//	单位编码
    private String cComUnitName;//	单位名称
    private Double iQuantity;//	应领数量
    private String cBatch;//		批号
    private Date dRequiredDate;//	需求日期

    public String getMomaterialsID() {
        return momaterialsID;
    }

    public U8MOMaterials setMomaterialsID(String momaterialsID) {
        this.momaterialsID = momaterialsID;
        return this;
    }

    public String getMoDetailsID() {
        return moDetailsID;
    }

    public U8MOMaterials setMoDetailsID(String moDetailsID) {
        this.moDetailsID = moDetailsID;
        return this;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public U8MOMaterials setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getcWhName() {
        return cWhName;
    }

    public U8MOMaterials setcWhName(String cWhName) {
        this.cWhName = cWhName;
        return this;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public U8MOMaterials setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    public String getcInvName() {
        return cInvName;
    }

    public U8MOMaterials setcInvName(String cInvName) {
        this.cInvName = cInvName;
        return this;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public U8MOMaterials setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
        return this;
    }

    public String getcComUnitCode() {
        return cComUnitCode;
    }

    public U8MOMaterials setcComUnitCode(String cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8MOMaterials setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }

    public Double getiQuantity() {
        return iQuantity;
    }

    public U8MOMaterials setiQuantity(Double iQuantity) {
        this.iQuantity = iQuantity;
        return this;
    }

    public String getcBatch() {
        return cBatch;
    }

    public U8MOMaterials setcBatch(String cBatch) {
        this.cBatch = cBatch;
        return this;
    }

    public Date getdRequiredDate() {
        return dRequiredDate;
    }

    public U8MOMaterials setdRequiredDate(Date dRequiredDate) {
        this.dRequiredDate = dRequiredDate;
        return this;
    }

    public String getHw() {
        return hw;
    }

    public U8MOMaterials setHw(String hw) {
        this.hw = hw;
        return this;
    }
}
