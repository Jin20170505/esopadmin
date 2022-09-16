package com.jeeplus.modules.u8data.employee.entity;

import com.jeeplus.core.persistence.DataEntity;

public class SysEmployee extends DataEntity<SysEmployee> {
    private String deptid; // 部门ID
    private String code; // 工号 登录账号
    private String name; // 姓名
    private String password; // 密码
    private String phone; // 手机号
    private String email; // 电子邮箱
    private String tel; // 电话

    public String getDeptid() {
        return deptid;
    }

    public SysEmployee setDeptid(String deptid) {
        this.deptid = deptid;
        return this;
    }

    public String getCode() {
        return code;
    }

    public SysEmployee setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public SysEmployee setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public SysEmployee setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public SysEmployee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public SysEmployee setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysEmployee setPassword(String password) {
        this.password = password;
        return this;
    }
}
