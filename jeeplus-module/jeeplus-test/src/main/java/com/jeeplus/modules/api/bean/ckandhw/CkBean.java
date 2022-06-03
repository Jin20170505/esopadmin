package com.jeeplus.modules.api.bean.ckandhw;

/**
 * 仓库
 */
public class CkBean {
    private String value;// 仓库ID
    private String text; // 仓库名称
    private String usehw; // 是否启用货位
    public String getValue() {
        return value;
    }

    public CkBean setValue(String value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public CkBean setText(String text) {
        this.text = text;
        return this;
    }

    public String getUsehw() {
        return usehw;
    }

    public CkBean setUsehw(String usehw) {
        this.usehw = usehw;
        return this;
    }
}
