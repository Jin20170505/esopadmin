package com.jeeplus.modules.qiyewx.daka.entity;

import com.jeeplus.modules.qiyewx.daka.entity.daka_day.OtInfo;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.DaySpItem;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.DaySummaryInfo;
import com.jeeplus.modules.qiyewx.daka.entity.daka_day.DayBaseInfo;

import java.util.List;

/**
 * 打卡日报
 */
public class DaKaDayData {

    /** 基础信息 */
    private DayBaseInfo base_info;

    /** 加班信息 */
    private OtInfo otInfo;
    /**	汇总信息  */
    private DaySummaryInfo summaryInfo;
    /** 假勤统计信息 */
    private List<DaySpItem> spItems;

    public DayBaseInfo getBase_info() {
        return base_info;
    }

    public void setBase_info(DayBaseInfo base_info) {
        this.base_info = base_info;
    }

    public OtInfo getOtInfo() {
        return otInfo;
    }

    public void setOtInfo(OtInfo otInfo) {
        this.otInfo = otInfo;
    }

    public DaySummaryInfo getSummaryInfo() {
        return summaryInfo;
    }

    public void setSummaryInfo(DaySummaryInfo summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    public List<DaySpItem> getSpItems() {
        return spItems;
    }

    public void setSpItems(List<DaySpItem> spItems) {
        this.spItems = spItems;
    }
}
