package com.jeeplus.modules.business.check.ipqc.entity;

import com.jeeplus.core.persistence.DataEntity;

public class BusinessCheckIPQCFile extends DataEntity<BusinessCheckIPQCFile> {
    private BusinessCheckIPQC p;
    private Integer no;
    private String url;

    public BusinessCheckIPQCFile() {
        super();
    }

    public BusinessCheckIPQCFile(String id){
        super(id);
    }

    public BusinessCheckIPQCFile(BusinessCheckIPQC p) {
        this.p = p;
    }

    public BusinessCheckIPQC getP() {
        return p;
    }

    public BusinessCheckIPQCFile setP(BusinessCheckIPQC p) {
        this.p = p;
        return this;
    }

    public Integer getNo() {
        return no;
    }

    public BusinessCheckIPQCFile setNo(Integer no) {
        this.no = no;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public BusinessCheckIPQCFile setUrl(String url) {
        this.url = url;
        return this;
    }
}
