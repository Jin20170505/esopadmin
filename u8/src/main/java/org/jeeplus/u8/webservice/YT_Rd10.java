/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package org.jeeplus.u8.webservice;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.modules.sys.entity.Office;

import java.util.Date;
import java.util.List;

/**
 * 右表Entity
 * @author Jin
 * @version 2022-05-04
 */
public class YT_Rd10 extends DataEntity<YT_Rd10> {

    private static final long serialVersionUID = 1L;
    private String cBusType;    // 业务类型
    private String cSource;		// 来源
    private String cWhCode;		// 仓库编码
    private String cCode;		// 单据编码
    private String cRdcode;		// 入库类型
    private String cDepCode;    // 部门编码
    private String cMemo;		// 备注
    private String cMaker;		// 制单人
    private String dDate;		// 入库日期
    private List<YT_Rds10> rd10s;		// 制单人

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

    public String getcCode() {
        return cCode;
    }

    public void setcCode(String cCode) {
        this.cCode = cCode;
    }

    public String getcRdcode() {
        return cRdcode;
    }

    public void setcRdcode(String cRdcode) {
        this.cRdcode = cRdcode;
    }

    public String getcDepCode() {
        return cDepCode;
    }

    public void setcDepCode(String cDepCode) {
        this.cDepCode = cDepCode;
    }

    public String getcMemo() {
        return cMemo;
    }

    public void setcMemo(String cMemo) {
        this.cMemo = cMemo;
    }

    public String getcMaker() {
        return cMaker;
    }

    public void setcMaker(String cMaker) {
        this.cMaker = cMaker;
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate;
    }

    public List<YT_Rds10> getRd10s() {
        return rd10s;
    }

    public void setRd10s(List<YT_Rds10> rd10s) {
        this.rd10s = rd10s;
    }
}