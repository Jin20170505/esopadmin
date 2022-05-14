package com.jeeplus.modules.api.bean.ckandhw;

/**
 * 货位
 */
public class HwBean {
    private String value; // 货位ID
    private String text; // 货位名称
    private String ckid; // 仓库ID

    public String getValue() {
        return value;
    }

    public HwBean setValue(String value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public HwBean setText(String text) {
        this.text = text;
        return this;
    }

    public String getCkid() {
        return ckid;
    }

    public HwBean setCkid(String ckid) {
        this.ckid = ckid;
        return this;
    }
}
