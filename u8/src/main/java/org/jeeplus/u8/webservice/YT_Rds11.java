/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package org.jeeplus.u8.webservice;


import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 右表Entity
 * @author Jin
 * @version 2022-05-04
 */
public class YT_Rds11 extends DataEntity<YT_Rds11> {

    private static final long serialVersionUID = 1L;
    private String cInvCode;    // 存货编码
    private String iQuantity;		// 数量
    private String cmocode;		// 生产订单号
    private String invcode;		// 产品编码
    private String imoseq;    // 生产订单行号
    private String iopseq;		// 子件行号

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

    public String getCmocode() {
        return cmocode;
    }

    public void setCmocode(String cmocode) {
        this.cmocode = cmocode;
    }

    public String getInvcode() {
        return invcode;
    }

    public void setInvcode(String invcode) {
        this.invcode = invcode;
    }

    public String getImoseq() {
        return imoseq;
    }

    public void setImoseq(String imoseq) {
        this.imoseq = imoseq;
    }


    public String getIopseq() {
        return iopseq;
    }

    public void setIopseq(String iopseq) {
        this.iopseq = iopseq;
    }
}