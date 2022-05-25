/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package org.jeeplus.u8.webservice;


import com.jeeplus.core.persistence.DataEntity;

import java.util.List;

/**
 * 右表Entity
 * @author Jin
 * @version 2022-05-04
 */
public class YT_Rd01 extends DataEntity<YT_Rd01> {

    private static final long serialVersionUID = 1L;
    private String Code;    // 编号
    private String cBusType;		// 业务类型 1:到货单,2,委外订单
    private String cSource;		// 单据来源
    private String cWhCode;		// 仓库编码
    private String dDate;		// 入库日期
    private String cRdCode;		// 入库类别编码
    private String cDepCode;    // 部门编码
    private String cPersonCode;		// 业务员编码
    private String cPTCode;		// 采购类型编码
    private String cVenCode;		// 供应商编码
    private String cMaker;		// 制单人
    private String dnmaketim;		// 制单日期
    private String cExchCode;		// 人民币编码
    private List<YT_Rds01> rd01s;		// 采购入库单明细

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getcBusType() {
        return cBusType;
    }

    public void setcBusType(String cBusType) {
        this.cBusType = cBusType;
    }

    public String getcSource() {
        return cSource;
    }

    public void setcSource(String cSource) {
        this.cSource = cSource;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public void setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public String getcRdCode() {
        return cRdCode;
    }

    public void setcRdCode(String cRdCode) {
        this.cRdCode = cRdCode;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public void setcDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
    }

    public String getcPersonCode() {
        return cPersonCode;
    }

    public void setcPersonCode(String cPersonCode) {
        this.cPersonCode = cPersonCode;
    }

    public String getcPTCode() {
        return cPTCode;
    }

    public void setcPTCode(String cPTCode) {
        this.cPTCode = cPTCode;
    }

    public String getcVenCode() {
        return cVenCode;
    }

    public void setcVenCode(String cVenCode) {
        this.cVenCode = cVenCode;
    }

    public String getcMaker() {
        return cMaker;
    }

    public void setcMaker(String cMaker) {
        this.cMaker = cMaker;
    }

    public String getDnmaketim() {
        return dnmaketim;
    }

    public void setDnmaketim(String dnmaketim) {
        this.dnmaketim = dnmaketim;
    }

    public String getcExchCode() {
        return cExchCode;
    }

    public void setcExchCode(String cExchCode) {
        this.cExchCode = cExchCode;
    }

    public List<YT_Rds01> getRd01s() {
        return rd01s;
    }

    public void setRd01s(List<YT_Rds01> rd01s) {
        this.rd01s = rd01s;
    }
}