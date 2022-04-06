package com.jeeplus.modules.qiyewx.shenpi.entity.control;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/29
 * @Description: 单选/多选控件（control参数为Selector）
 */
public class Selector {
    /** 选择类型：single-单选；multi-多选 */
    private String type;
    /** 申请人所选择的选项，多选情况下可能有多个
     * （仅包含申请人说选择的选项，并非所有选项，若需要了解所有选项，需使用“获取审批模板详情”接口） */
    private List<SelectorOption> options;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<SelectorOption> getOptions() {
        return options;
    }

    public void setOptions(List<SelectorOption> options) {
        this.options = options;
    }
}
