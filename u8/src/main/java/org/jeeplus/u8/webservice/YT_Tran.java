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
public class YT_Tran extends DataEntity<YT_Tran> {

    private static final long serialVersionUID = 1L;
    private String cTVCode;    // 调拨单号
    private String dTVDate;		// 调拨日期
    private String cOWhCode;		// 转出仓库编码
    private String cIWhCode;		// 转入仓库编码
    private String cIRdCode;		// 入库类别编码
    private String cORdCode;    // 出库类别编码
    private String cTVMemo;		// 备注
    private List<YT_Trans> trans;		// 调拨单子表

    public String getcTVCode() {
        return cTVCode;
    }

    public void setcTVCode(String cTVCode) {
        this.cTVCode = cTVCode;
    }

    public String getdTVDate() {
        return dTVDate;
    }

    public void setdTVDate(String dTVDate) {
        this.dTVDate = dTVDate;
    }

    public String getcOWhCode() {
        return cOWhCode;
    }

    public void setcOWhCode(String cOWhCode) {
        this.cOWhCode = cOWhCode;
    }

    public String getcIWhCode() {
        return cIWhCode;
    }

    public void setcIWhCode(String cIWhCode) {
        this.cIWhCode = cIWhCode;
    }

    public String getcIRdCode() {
        return cIRdCode;
    }

    public void setcIRdCode(String cIRdCode) {
        this.cIRdCode = cIRdCode;
    }

    public String getcORdCode() {
        return cORdCode;
    }

    public void setcORdCode(String cORdCode) {
        this.cORdCode = cORdCode;
    }

    public String getcTVMemo() {
        return cTVMemo;
    }

    public void setcTVMemo(String cTVMemo) {
        this.cTVMemo = cTVMemo;
    }

    public List<YT_Trans> getTrans() {
        return trans;
    }

    public void setTrans(List<YT_Trans> trans) {
        this.trans = trans;
    }
}