package com.jeeplus.modules.u8data.customercinvcode.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 客户存货对照表
 */
public class U8CusInvContrapose extends DataEntity<U8CusInvContrapose> {
    private String cinvcode; // 存货编码
    private String customercode; // 客户编码
    private String customercinvcode; // 客户存货编码
    private String customercinvname; // 客户存货名称

    public String getCinvcode() {
        return cinvcode;
    }

    public U8CusInvContrapose setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCustomercode() {
        return customercode;
    }

    public U8CusInvContrapose setCustomercode(String customercode) {
        this.customercode = customercode;
        return this;
    }

    public String getCustomercinvcode() {
        return customercinvcode;
    }

    public U8CusInvContrapose setCustomercinvcode(String customercinvcode) {
        this.customercinvcode = customercinvcode;
        return this;
    }

    public String getCustomercinvname() {
        return customercinvname;
    }

    public U8CusInvContrapose setCustomercinvname(String customercinvname) {
        this.customercinvname = customercinvname;
        return this;
    }
}
