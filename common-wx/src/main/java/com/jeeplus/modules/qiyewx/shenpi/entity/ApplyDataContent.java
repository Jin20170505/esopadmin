package com.jeeplus.modules.qiyewx.shenpi.entity;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 审批申请详情，由多个表单控件及其内容组成
 */
public class ApplyDataContent {
    /** 控件类型：Text-文本；Textarea-多行文本；Number-数字；Money-金额；Date-日期/日期+时间；
     * Selector-单选/多选；；Contact-成员/部门；Tips-说明文字；File-附件；Table-明细；Attendance-假勤；
     * Vacation-请假；PunchCorrection-补卡;DateRange-时长 */
    private String control;
    /** 控件id */
    private String id;
    /** 控件名称 ，若配置了多语言则会包含中英文的控件名称 */
    private List<FormTitle> title;
    /** 控件值 ，包含了申请人在各种类型控件中输入的值，不同控件有不同的值 */
    private FormValue value;

    private String valueJson;

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<FormTitle> getTitle() {
        return title;
    }

    public void setTitle(List<FormTitle> title) {
        this.title = title;
    }

    public FormValue getValue() {
        return value;
    }

    public void setValue(FormValue value) {
        this.value = value;
    }

    public String getValueJson() {
        return valueJson;
    }

    public void setValueJson(String valueJson) {
        this.valueJson = valueJson;
    }
}
