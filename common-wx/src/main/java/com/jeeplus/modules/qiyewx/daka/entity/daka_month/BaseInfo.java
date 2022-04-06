package com.jeeplus.modules.qiyewx.daka.entity.daka_month;

/**
 * @Auther: Jin
 * @Date: 2021/8/27
 * @Description: (打卡月报表数据)基础信息
 */
public class BaseInfo {
    /** 记录类型：1-固定上下班；2-外出（此报表中不会出现外出打卡数据）；3-按班次上下班；4-自由签到；5-加班；7-无规则  */
    private Integer record_type;
    /** 打卡人员姓名 */
    private String name;
    /** 	打卡人员帐号，即userid */
    private String acctid;
    /** 打卡人员所在部门，会显示所有所在部门 */
    private String departs_name;
    /** 	打卡人员所属规则信息 */
    private RuleInfo rule_nfo;

    public Integer getRecord_type() {
        return record_type;
    }

    public void setRecord_type(Integer record_type) {
        this.record_type = record_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcctid() {
        return acctid;
    }

    public void setAcctid(String acctid) {
        this.acctid = acctid;
    }

    public String getDeparts_name() {
        return departs_name;
    }

    public void setDeparts_name(String departs_name) {
        this.departs_name = departs_name;
    }

    public RuleInfo getRule_nfo() {
        return rule_nfo;
    }

    public void setRule_nfo(RuleInfo rule_nfo) {
        this.rule_nfo = rule_nfo;
    }
}
