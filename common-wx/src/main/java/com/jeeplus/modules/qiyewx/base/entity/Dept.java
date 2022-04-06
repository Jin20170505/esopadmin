package com.jeeplus.modules.qiyewx.base.entity;

/**
 * @Auther: Jin
 * @Date: 2021/8/24
 * @Description:
 */
public class Dept {
    private Integer id;
    private String name;
    private String nameEn;
    private Integer parentid;
    private Integer order;

    public Integer getId() {
        return id;
    }

    public Dept setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Dept setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Dept setNameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public Integer getParentid() {
        return parentid;
    }

    public Dept setParentid(Integer parentid) {
        this.parentid = parentid;
        return this;
    }

    public Integer getOrder() {
        return order;
    }

    public Dept setOrder(Integer order) {
        this.order = order;
        return this;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", parentid=" + parentid +
                ", order=" + order +
                '}';
    }
}
