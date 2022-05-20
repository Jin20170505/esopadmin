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
public class YT_Trans extends DataEntity<YT_Trans> {

    private static final long serialVersionUID = 1L;
    private String cbMemo;		// 备注
    private String cInvCode;		// 存货编码
    private String iTVQuantity;		// 数量
    private String cTVBatch;		// 批号
    private String coutposcode;    // 调出货位
    private String irowno;		// 行号
    private String dMadeDate;		// 生产日期

    public String getCbMemo() {
        return cbMemo;
    }

    public void setCbMemo(String cbMemo) {
        this.cbMemo = cbMemo;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public void setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
    }

    public String getiTVQuantity() {
        return iTVQuantity;
    }

    public void setiTVQuantity(String iTVQuantity) {
        this.iTVQuantity = iTVQuantity;
    }

    public String getcTVBatch() {
        return cTVBatch;
    }

    public void setcTVBatch(String cTVBatch) {
        this.cTVBatch = cTVBatch;
    }

    public String getCoutposcode() {
        return coutposcode;
    }

    public void setCoutposcode(String coutposcode) {
        this.coutposcode = coutposcode;
    }

    public String getIrowno() {
        return irowno;
    }

    public void setIrowno(String irowno) {
        this.irowno = irowno;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public void setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
    }
}