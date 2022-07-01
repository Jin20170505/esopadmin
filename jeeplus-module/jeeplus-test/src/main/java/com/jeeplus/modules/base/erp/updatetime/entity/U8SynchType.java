package com.jeeplus.modules.base.erp.updatetime.entity;

public enum U8SynchType {
    ROUTE("01","工艺路线");
    private String code;
    private String name;
    U8SynchType(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
