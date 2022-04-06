package com.jeeplus.modules.qiyewx.base.entity;

/**
 * @Auther: Jin
 * @Date: 2021/8/24
 * @Description:
 */
public class Employee {
    private String userid;
    private String name;
    private String deptment;
    private String position;
    private String mobile;
    private String gender;
    private String email;
    private String avatar;
    private String thumbAvatar;
    private String telephone;
    private String alias;
    private Integer mainDepartment;
    private Integer status;

    public String getUserid() {
        return userid;
    }

    public Employee setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }

    public String getDeptment() {
        return deptment;
    }

    public Employee setDeptment(String deptment) {
        this.deptment = deptment;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Employee setPosition(String position) {
        this.position = position;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public Employee setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Employee setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Employee setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getThumbAvatar() {
        return thumbAvatar;
    }

    public Employee setThumbAvatar(String thumbAvatar) {
        this.thumbAvatar = thumbAvatar;
        return this;
    }

    public String getTelephone() {
        return telephone;
    }

    public Employee setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public Employee setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Integer getMainDepartment() {
        return mainDepartment;
    }

    public Employee setMainDepartment(Integer mainDepartment) {
        this.mainDepartment = mainDepartment;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public Employee setStatus(Integer status) {
        this.status = status;
        return this;
    }
}
