package org.jeeplus.u8.webservice.otherinck.entity;

import com.google.common.collect.Lists;

import java.util.List;

public class U8OtherInCkMain {
    /// <summary>
    /// 仓库编码
    /// </summary>
    private String cWhCode;

    /// <summary>
    /// 入库日期
    /// </summary>
    private String dDate;

    /// <summary>
    /// 入库单号
    /// </summary>
    private String cCode;

    /// <summary>
    /// 入库类别编码 ="14"
    /// </summary>
    private String crdcode;

    /// <summary>
    /// 入库部门编码
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

    private List<U8OtherInCkMx> Details = Lists.newArrayList();

    public String getcWhCode() {
        return cWhCode;
    }

    public U8OtherInCkMain setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getdDate() {
        return dDate;
    }

    public U8OtherInCkMain setdDate(String dDate) {
        this.dDate = dDate;
        return this;
    }

    public String getcCode() {
        return cCode;
    }

    public U8OtherInCkMain setcCode(String cCode) {
        this.cCode = cCode;
        return this;
    }

    public String getCrdcode() {
        return crdcode;
    }

    public U8OtherInCkMain setCrdcode(String crdcode) {
        this.crdcode = crdcode;
        return this;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public U8OtherInCkMain setcDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8OtherInCkMain setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }

    public String getcMaker() {
        return cMaker;
    }

    public U8OtherInCkMain setcMaker(String cMaker) {
        this.cMaker = cMaker;
        return this;
    }

    public String getMesCode() {
        return MesCode;
    }

    public U8OtherInCkMain setMesCode(String mesCode) {
        MesCode = mesCode;
        return this;
    }

    public List<U8OtherInCkMx> getDetails() {
        return Details;
    }

    public U8OtherInCkMain setDetails(List<U8OtherInCkMx> details) {
        Details = details;
        return this;
    }
}
