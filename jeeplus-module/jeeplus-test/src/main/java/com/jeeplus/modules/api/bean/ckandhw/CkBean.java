package com.jeeplus.modules.api.bean.ckandhw;

/**
 * 仓库
 */
public class CkBean {
    private String value;// 仓库ID
    private String text; // 仓库名称

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
}
