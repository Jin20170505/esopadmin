package org.jeeplus.u8.webservice.xiaoshouchuku.entity;

import com.google.common.collect.Lists;

import java.util.List;

public class U8WebXiaoShouBean {
    /// <summary>
    /// 编号
    /// </summary>
    private String Code;

    /// <summary>
    /// 仓库编码
    /// </summary>
    private String cWhCode;

    /// <summary>
    /// 出库日期
    /// </summary>
    private String dDate;

    /// <summary>
    /// 出库类型编码
    /// </summary>
    private String cRdCode;

    /// <summary>
    /// 部门编码
    /// </summary>
    private String cDepCode;

    /// <summary>
    /// 业务员编码
    /// </summary>
    private String cPersonCode;

    /// <summary>
    /// 销售类型
    /// </summary>
    private String cSTCode;

    /// <summary>
    /// 客户编码
    /// </summary>
    private String cCusCode;

    /// <summary>
    /// 备注
    /// </summary>
    private String cMemo;

    /// <summary>
    /// 制单人
    /// </summary>
    private String cMaker;

    /// <summary>
    /// 是否退货
    /// </summary>
    private String bredvouch;

    private List<U8WebXiaoShouMxBean> Details = Lists.newArrayList();


    public String getCode() {
        return Code;
    }

    public U8WebXiaoShouBean setCode(String code) {
        Code = code;
        return this;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public U8WebXiaoShouBean setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getdDate() {
        return dDate;
    }

    public U8WebXiaoShouBean setdDate(String dDate) {
        this.dDate = dDate;
        return this;
    }

    public String getcRdCode() {
        return cRdCode;
    }

    public U8WebXiaoShouBean setcRdCode(String cRdCode) {
        this.cRdCode = cRdCode;
        return this;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public U8WebXiaoShouBean setcDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
        return this;
    }

    public String getcPersonCode() {
        return cPersonCode;
    }

    public U8WebXiaoShouBean setcPersonCode(String cPersonCode) {
        this.cPersonCode = cPersonCode;
        return this;
    }

    public String getcSTCode() {
        return cSTCode;
    }

    public U8WebXiaoShouBean setcSTCode(String cSTCode) {
        this.cSTCode = cSTCode;
        return this;
    }

    public String getcCusCode() {
        return cCusCode;
    }

    public U8WebXiaoShouBean setcCusCode(String cCusCode) {
        this.cCusCode = cCusCode;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8WebXiaoShouBean setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }

    public String getcMaker() {
        return cMaker;
    }

    public U8WebXiaoShouBean setcMaker(String cMaker) {
        this.cMaker = cMaker;
        return this;
    }

    public String getBredvouch() {
        return bredvouch;
    }

    public U8WebXiaoShouBean setBredvouch(String bredvouch) {
        this.bredvouch = bredvouch;
        return this;
    }

    public List<U8WebXiaoShouMxBean> getDetails() {
        return Details;
    }

    public U8WebXiaoShouBean setDetails(List<U8WebXiaoShouMxBean> details) {
        Details = details;
        return this;
    }
}
