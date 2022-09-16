package com.jeeplus.modules.u8data.employee.entity;

import com.jeeplus.core.persistence.DataEntity;

public class U8Employee extends DataEntity<U8Employee> {
    private String deptid;
    private String code;
    private String name;
    private String phone;
    private String email;
    private String tel;
    private String sex;
    private String position;

    public String getDeptid() {
        return deptid;
    }

    public U8Employee setDeptid(String deptid) {
        this.deptid = deptid;
        return this;
    }

    public String getCode() {
        return code;
    }

    public U8Employee setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public U8Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public U8Employee setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public U8Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public U8Employee setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public U8Employee setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public U8Employee setPosition(String position) {
        this.position = position;
        return this;
    }
}
