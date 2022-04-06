package com.jeeplus.modules.qiyewx.shenpi.entity.control;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 文本控件（control为text 或  textarea）
 */
public class TextOrTextArea {
    /** 文本内容，即申请人在此控件填写的文本内容  */
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
