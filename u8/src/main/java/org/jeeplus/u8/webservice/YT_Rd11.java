/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package org.jeeplus.u8.webservice;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.Office;

import java.util.Date;
import java.util.List;

/**
 * 右表Entity
 * @author Jin
 * @version 2022-05-04
 */
public class YT_Rd11 extends DataEntity<YT_Rd11> {

    private static final long serialVersionUID = 1L;
    private String cBusType;    // 业务类型
    private String cSource;		// 单据来源
    private String cWhCode;		// 仓库编码
    private String dDate;		// 出库日期
    private String cCode;		// 出库单号
    private String cRdcode;    // 出库类别编码
    private String cDepCode;		// 部门编码
    private String cMemo;		//备注
    private String cMaker;		//制单人
    private List<YT_Rds11> rd11s;		// 材料出库单明细

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

    public List<YT_Rds11> getRd11s() {
        return rd11s;
    }

    public void setRd11s(List<YT_Rds11> rd11s) {
        this.rd11s = rd11s;
    }
}