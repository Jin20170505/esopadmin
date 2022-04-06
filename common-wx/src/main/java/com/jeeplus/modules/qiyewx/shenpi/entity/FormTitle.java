package com.jeeplus.modules.qiyewx.shenpi.entity;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 控件名称 ，若配置了多语言则会包含中英文的控件名称
 */
public class FormTitle {
    /** 控件名称 */
    private String text;
    /** 语言 zh_CN  */
    private String lang;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
