package com.jeeplus.modules.qiyewx.shenpi.entity.control;

import com.jeeplus.modules.qiyewx.shenpi.entity.FormTitle;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description:
 */
public class SelectorOption {
    /** 	选项key，选项的唯一id，可通过“获取审批模板详情”接口获得 */
    private String key;
    /** 选项值，若配置了多语言则会包含中英文的选项值 */
    private List<FormTitle> value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<FormTitle> getValue() {
        return value;
    }

    public void setValue(List<FormTitle> value) {
        this.value = value;
    }
}
