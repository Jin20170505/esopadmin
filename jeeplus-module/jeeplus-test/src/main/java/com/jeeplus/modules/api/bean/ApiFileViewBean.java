package com.jeeplus.modules.api.bean;

import java.io.Serializable;

/**
 * 文件浏览 列表 实体
 */
public class ApiFileViewBean implements Serializable {
    private String id; // 文件ID
    private String no;
    private String name; // 文件名称
    private String remarks; // 文件备注
    private String url; // 文件地址
    private String productname; // 产品名称

    public String getId() {
        return id;
    }

    public ApiFileViewBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getNo() {
        return no;
    }

    public ApiFileViewBean setNo(String no) {
        this.no = no;
        return this;
    }

    public String getName() {
        return name;
    }

    public ApiFileViewBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public ApiFileViewBean setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ApiFileViewBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getProductname() {
        return productname;
    }

    public ApiFileViewBean setProductname(String productname) {
        this.productname = productname;
        return this;
    }
}