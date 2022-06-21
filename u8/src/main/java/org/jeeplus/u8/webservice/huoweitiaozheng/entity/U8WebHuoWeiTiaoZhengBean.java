package org.jeeplus.u8.webservice.huoweitiaozheng.entity;

import com.google.common.collect.Lists;

import java.util.List;

public class U8WebHuoWeiTiaoZhengBean {
    private String cVouchCode; // 货位调整单号
    private String dDate; // 单据日期
    private String cWhCode; // 仓库编码
    private String cMaker; // 制单人名字
    private List<U8WebHuoWeiTiaoZhengMxBean> details = Lists.newArrayList();

    public String getcVouchCode() {
        return cVouchCode;
    }

    public U8WebHuoWeiTiaoZhengBean setcVouchCode(String cVouchCode) {
        this.cVouchCode = cVouchCode;
        return this;
    }

    public String getdDate() {
        return dDate;
    }

    public U8WebHuoWeiTiaoZhengBean setdDate(String dDate) {
        this.dDate = dDate;
        return this;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public U8WebHuoWeiTiaoZhengBean setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getcMaker() {
        return cMaker;
    }

    public U8WebHuoWeiTiaoZhengBean setcMaker(String cMaker) {
        this.cMaker = cMaker;
        return this;
    }

    public List<U8WebHuoWeiTiaoZhengMxBean> getDetails() {
        return details;
    }

    public U8WebHuoWeiTiaoZhengBean setDetails(List<U8WebHuoWeiTiaoZhengMxBean> details) {
        this.details = details;
        return this;
    }
}
