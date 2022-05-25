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
public class YT_Rds01 extends DataEntity<YT_Rds01> {

    private static final long serialVersionUID = 1L;
    private String irowno;    // 行号
    private String cInvCode;		// 存货编码
    private String iQuantity;		// 数量
    private String iArrsId;		// u8采购到货单子表标识 子表id
    private String cBatch;		// 批号
    private String cPosition;    // 货位编码
    private String dMadeDate;		// 生产日期
    private String cARVCode;		// 到货单号

    public String getIrowno() {
        return irowno;
    }

    public void setIrowno(String irowno) {
        this.irowno = irowno;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
    }

    public String getiArrsId() {
        return iArrsId;
    }

    public void setiArrsId(String iArrsId) {
        this.iArrsId = iArrsId;
    }

    public String getcBatch() {
        return cBatch;
    }

    public void setcBatch(String cBatch) {
        this.cBatch = cBatch;
    }

    public String getcPosition() {
        return cPosition;
    }

    public void setcPosition(String cPosition) {
        this.cPosition = cPosition;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public void setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
    }

    public String getcARVCode() {
        return cARVCode;
    }

    public void setcARVCode(String cARVCode) {
        this.cARVCode = cARVCode;
    }
}