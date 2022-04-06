package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: control 为 Contact 且 value 为 departments  部门控件
 */
public class Department {
    /** 部门id */
    private String openapi_id;
    private String name;

    public String getOpenapi_id() {
        return openapi_id;
    }

    public void setOpenapi_id(String openapi_id) {
        this.openapi_id = openapi_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
