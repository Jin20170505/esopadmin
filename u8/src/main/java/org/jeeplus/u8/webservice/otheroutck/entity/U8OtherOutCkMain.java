package org.jeeplus.u8.webservice.otheroutck.entity;

import com.google.common.collect.Lists;

import java.util.List;

//
public class U8OtherOutCkMain {
    /// <summary>
    /// 仓库编码
    /// </summary>
    private String cWhCode;

    /// <summary>
    /// 出库日期
    /// </summary>
    private String dDate;

    /// <summary>
    /// 出库单号
    /// </summary>
    private String cCode;

    /// <summary>
    /// 出库类别编码 = 24
    /// </summary>
    private String crdcode;

    /// <summary>
    /// 出库部门编码
    /// </summary>
    private String cDepCode;

    /// <summary>
    /// 备注
    /// </summary>
    private String cMemo;

    /// <summary>
    /// 制单人
    /// </summary>
    private String cMaker;

    /// <summary>
    /// Mes单号
    /// </summary>
    private String MesCode;


    private List<U8OtherOutCkMx> Details = Lists.newArrayList();

    public String getcWhCode() {
        return cWhCode;
    }

    public U8OtherOutCkMain setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getdDate() {
        return dDate;
    }

    public U8OtherOutCkMain setdDate(String dDate) {
        this.dDate = dDate;
        return this;
    }

    public String getcCode() {
        return cCode;
    }

    public U8OtherOutCkMain setcCode(String cCode) {
        this.cCode = cCode;
        return this;
    }

    public String getCrdcode() {
        return crdcode;
    }

    public U8OtherOutCkMain setCrdcode(String crdcode) {
        this.crdcode = crdcode;
        return this;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public U8OtherOutCkMain setcDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8OtherOutCkMain setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }

    public String getcMaker() {
        return cMaker;
    }

    public U8OtherOutCkMain setcMaker(String cMaker) {
        this.cMaker = cMaker;
        return this;
    }

    public String getMesCode() {
        return MesCode;
    }

    public U8OtherOutCkMain setMesCode(String mesCode) {
        MesCode = mesCode;
        return this;
    }

    public List<U8OtherOutCkMx> getDetails() {
        return Details;
    }

    public U8OtherOutCkMain setDetails(List<U8OtherOutCkMx> details) {
        Details = details;
        return this;
    }

    @Override
    public String toString() {
        return "U8OtherOutCkMain{" +
                "cWhCode='" + cWhCode + '\'' +
                ", dDate='" + dDate + '\'' +
                ", cCode='" + cCode + '\'' +
                ", crdcode='" + crdcode + '\'' +
                ", cDepCode='" + cDepCode + '\'' +
                ", cMemo='" + cMemo + '\'' +
                ", cMaker='" + cMaker + '\'' +
                ", MesCode='" + MesCode + '\'' +
                ", Details=" + Details +
                '}';
    }
}
