/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package org.jeeplus.u8.webservice;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.sys.entity.Office;

import java.util.Date;

/**
 * 右表Entity
 * @author Jin
 * @version 2022-05-04
 */
public class YT_Rds10 extends DataEntity<YT_Rds10> {

    private static final long serialVersionUID = 1L;
    private String cInvCode;    // 存货编码
    private String iQuantity;		// 数量
    private String cPosition;		// 货位编码
    private String irowno;		// 行号
    private String cmocode;    // 生产订单号
    private String imoseq;		// 生产订单行号
    private String cbMemo;		// 备注
    private String dMadeDate;		// 生产日期
    private String batch;		// 批号

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

    public String getcPosition() {
        return cPosition;
    }

    public void setcPosition(String cPosition) {
        this.cPosition = cPosition;
    }

    public String getIrowno() {
        return irowno;
    }

    public void setIrowno(String irowno) {
        this.irowno = irowno;
    }

    public String getCmocode() {
        return cmocode;
    }

    public void setCmocode(String cmocode) {
        this.cmocode = cmocode;
    }

    public String getImoseq() {
        return imoseq;
    }

    public void setImoseq(String imoseq) {
        this.imoseq = imoseq;
    }

    public String getCbMemo() {
        return cbMemo;
    }

    public void setCbMemo(String cbMemo) {
        this.cbMemo = cbMemo;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public void setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}