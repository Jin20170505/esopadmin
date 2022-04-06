package com.jeeplus.modules.sys.entity;

import java.io.Serializable;

/**
 * @Auther: Jin
 * @Date: 2020/10/21
 * @Description: 用户所属产品线
 */
public class CProductType implements Serializable {
    private String id;
    private String code;
    private String name;

    public CProductType(){}
    public CProductType(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
