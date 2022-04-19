package com.jeeplus.modules.esop.filemanger.entity;

/**
 * 文件实体
 */
public class FileBean {

    private String id;
    private String code;
    private String name;
    private String url;
    private String remarks;

    public String getId() {
        return id;
    }

    public FileBean setId(String id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return code;
    }

    public FileBean setCode(String code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public FileBean setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public FileBean setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public FileBean setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }
}
