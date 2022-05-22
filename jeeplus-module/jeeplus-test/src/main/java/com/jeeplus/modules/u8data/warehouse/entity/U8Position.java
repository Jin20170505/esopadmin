package com.jeeplus.modules.u8data.warehouse.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 货位档案
 */
public class U8Position extends DataEntity<U8Position> {
    private String cPosCode;//	货位编码
    private String cPosName;//	货位名称
    private String iPosGrade;//	编码级次
    private String bPosEnd;//		是否末级
    private String cWhCode;//	仓库编码
    private String iMaxCubage;//	最大体积
    private String iMaxWeight;//	最大重量
    private String cMemo;//		备注
    private String cBarCode;//	对应条形码编码
    private String pubufts;//		时间戳
    private String cPosCode1;//	上级货位编码
    private String cWhName;//		仓库名称

    public String getcPosCode() {
        return cPosCode;
    }

    public U8Position setcPosCode(String cPosCode) {
        this.cPosCode = cPosCode;
        return this;
    }

    public String getcPosName() {
        return cPosName;
    }

    public U8Position setcPosName(String cPosName) {
        this.cPosName = cPosName;
        return this;
    }

    public String getiPosGrade() {
        return iPosGrade;
    }

    public U8Position setiPosGrade(String iPosGrade) {
        this.iPosGrade = iPosGrade;
        return this;
    }

    public String getbPosEnd() {
        return bPosEnd;
    }

    public U8Position setbPosEnd(String bPosEnd) {
        this.bPosEnd = bPosEnd;
        return this;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public U8Position setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getiMaxCubage() {
        return iMaxCubage;
    }

    public U8Position setiMaxCubage(String iMaxCubage) {
        this.iMaxCubage = iMaxCubage;
        return this;
    }

    public String getiMaxWeight() {
        return iMaxWeight;
    }

    public U8Position setiMaxWeight(String iMaxWeight) {
        this.iMaxWeight = iMaxWeight;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8Position setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }

    public String getcBarCode() {
        return cBarCode;
    }

    public U8Position setcBarCode(String cBarCode) {
        this.cBarCode = cBarCode;
        return this;
    }

    public String getPubufts() {
        return pubufts;
    }

    public U8Position setPubufts(String pubufts) {
        this.pubufts = pubufts;
        return this;
    }

    public String getcPosCode1() {
        return cPosCode1;
    }

    public U8Position setcPosCode1(String cPosCode1) {
        this.cPosCode1 = cPosCode1;
        return this;
    }

    public String getcWhName() {
        return cWhName;
    }

    public U8Position setcWhName(String cWhName) {
        this.cWhName = cWhName;
        return this;
    }
}
