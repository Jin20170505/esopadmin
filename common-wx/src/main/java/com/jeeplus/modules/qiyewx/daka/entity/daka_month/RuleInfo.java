package com.jeeplus.modules.qiyewx.daka.entity.daka_month;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: 打卡人员所属规则信息
 */
public class RuleInfo {
    /** 所属规则的id */
    private Integer groupid;
    /** 打卡规则名 */
    private String groupname;

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }
}
