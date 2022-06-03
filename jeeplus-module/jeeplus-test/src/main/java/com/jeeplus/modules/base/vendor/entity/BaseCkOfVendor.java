package com.jeeplus.modules.base.vendor.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.base.cangku.entity.BaseCangKu;

public class BaseCkOfVendor extends DataEntity<BaseCkOfVendor> {
    private Integer no;
    private BaseVendor vendor;
    private BaseCangKu cangKu;

    public BaseCkOfVendor(){}
    public BaseCkOfVendor(BaseVendor vendor) {
        this.vendor = vendor;
    }

    public Integer getNo() {
        return no;
    }

    public BaseCkOfVendor setNo(Integer no) {
        this.no = no;
        return this;
    }

    public BaseVendor getVendor() {
        return vendor;
    }

    public BaseCkOfVendor setVendor(BaseVendor vendor) {
        this.vendor = vendor;
        return this;
    }

    public BaseCangKu getCangKu() {
        return cangKu;
    }

    public BaseCkOfVendor setCangKu(BaseCangKu cangKu) {
        this.cangKu = cangKu;
        return this;
    }
}
