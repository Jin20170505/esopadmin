package com.jeeplus.modules.u8data.dept.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * U8部门
 */
public class U8Dept extends DataEntity<U8Dept> {
    private String code; // 编号
    private String name; // 名称
    private String memo; // 备注
    private Integer no; // 序号
    private String sfmj;// 是否末级

    public String getCode() {
        return code;
    }

    public U8Dept setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public U8Dept setName(String name) {
        this.name = name;
        return this;
    }

    public String getMemo() {
        return memo;
    }

    public U8Dept setMemo(String memo) {
        this.memo = memo;
        return this;
    }

    public Integer getNo() {
        return no;
    }

    public U8Dept setNo(Integer no) {
        this.no = no;
        return this;
    }

    public String getSfmj() {
        return sfmj;
    }

    public U8Dept setSfmj(String sfmj) {
        this.sfmj = sfmj;
        return this;
    }
}
