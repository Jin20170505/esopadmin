package com.jeeplus.modules.u8data.dept.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 同步部门使用的实体
 */
public class SysDeptBean extends DataEntity<SysDeptBean> {
    private String parentid;
    private String parentids;
    private String code;
    private String name;
    private Integer sort;
    private String type;
    private String grade;

    public String getParentid() {
        return parentid;
    }

    public SysDeptBean setParentid(String parentid) {
        this.parentid = parentid;
        return this;
    }

    public String getParentids() {
        return parentids;
    }

    public SysDeptBean setParentids(String parentids) {
        this.parentids = parentids;
        return this;
    }

    public String getCode() {
        return code;
    }

    public SysDeptBean setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public SysDeptBean setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public SysDeptBean setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public String getType() {
        return type;
    }

    public SysDeptBean setType(String type) {
        this.type = type;
        return this;
    }

    public String getGrade() {
        return grade;
    }

    public SysDeptBean setGrade(String grade) {
        this.grade = grade;
        return this;
    }
}
