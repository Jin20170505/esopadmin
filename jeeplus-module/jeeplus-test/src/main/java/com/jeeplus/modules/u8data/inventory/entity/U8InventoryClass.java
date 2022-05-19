package com.jeeplus.modules.u8data.inventory.entity;

import com.jeeplus.common.utils.StringUtils;

/**
 * 存货大类
 */
public class U8InventoryClass {
    private String cInvCCode; // 大类编码
    private String cInvCName; // 大类名称
    private String parentCode; // 上级编码
    private String bInvCEnd; // 是否末级

    public String getcInvCCode() {
        return cInvCCode;
    }

    public U8InventoryClass setcInvCCode(String cInvCCode) {
        this.cInvCCode = cInvCCode;
        return this;
    }

    public String getcInvCName() {
        return cInvCName;
    }

    public U8InventoryClass setcInvCName(String cInvCName) {
        this.cInvCName = cInvCName;
        return this;
    }

    public String getParentCode() {
        if(StringUtils.isEmpty(parentCode)){
            parentCode="0";
        }
        return parentCode;
    }

    public U8InventoryClass setParentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public String getbInvCEnd() {
        return bInvCEnd;
    }

    public U8InventoryClass setbInvCEnd(String bInvCEnd) {
        this.bInvCEnd = bInvCEnd;
        return this;
    }
}
