package com.jeeplus.modules.qiyewx.shenpi.entity;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/28
 * @Description:  审批单号
 */
public class ShenPiDanHao {
    /** 审批单号列表，包含满足条件的审批申请 */
    private List<String> sp_no_list = Lists.newArrayList();
    /** 后续请求查询的游标，当返回结果没有该字段时表示审批单已经拉取完 */
    private Integer next_cursor;

    public List<String> getSp_no_list() {
        return sp_no_list;
    }

    public void setSp_no_list(List<String> sp_no_list) {
        this.sp_no_list = sp_no_list;
    }

    public Integer getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(Integer next_cursor) {
        this.next_cursor = next_cursor;
    }
}
