package com.jeeplus.modules.qiyewx.shenpi.entity;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description: 审批申请数据
 */
public class ApplyData {
    /** 审批申请详情，由多个表单控件及其内容组成 */
    private List<ApplyDataContent> contents;

    public List<ApplyDataContent> getContents() {
        return contents;
    }

    public void setContents(List<ApplyDataContent> contents) {
        this.contents = contents;
    }
}
