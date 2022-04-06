package com.jeeplus.modules.qiyewx.daka.entity;

import com.jeeplus.modules.qiyewx.daka.entity.daka_month.*;

import java.util.List;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: 打卡月报数据
 */
public class DaKaMonthData {
    /** 基础信息 */
    private BaseInfo base_info;
    /** 汇总信息  */
    private SummaryInfo summary_info;
    /** 异常状态统计信息  */
    private List<ExceptionInfo> exception_infos;
    /** 假勤统计信息 */
    private List<SpItem> sp_items;
    /** 加班情况 */
    private OverworkInfo overwork_info;

    public BaseInfo getBase_info() {
        return base_info;
    }

    public void setBase_info(BaseInfo base_info) {
        this.base_info = base_info;
    }

    public SummaryInfo getSummary_info() {
        return summary_info;
    }

    public void setSummary_info(SummaryInfo summary_info) {
        this.summary_info = summary_info;
    }

    public List<ExceptionInfo> getException_infos() {
        return exception_infos;
    }

    public void setException_infos(List<ExceptionInfo> exception_infos) {
        this.exception_infos = exception_infos;
    }

    public List<SpItem> getSp_items() {
        return sp_items;
    }

    public void setSp_items(List<SpItem> sp_items) {
        this.sp_items = sp_items;
    }

    public OverworkInfo getOverwork_info() {
        return overwork_info;
    }

    public void setOverwork_info(OverworkInfo overwork_info) {
        this.overwork_info = overwork_info;
    }
}
