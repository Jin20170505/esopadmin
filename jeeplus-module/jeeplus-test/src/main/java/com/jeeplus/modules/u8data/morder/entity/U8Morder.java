package com.jeeplus.modules.u8data.morder.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 生产订单
 */
public class U8Morder extends DataEntity<U8Morder> {
    private String moId; // 生产订单ID
    private String moCode; // 生产订单单号
    private Date startdate; // 开工日期
    private Date dueDate; // 完工日期
    private String cdepcode; // 部门编码
    private String cdepname; // 部门名称
    private Date createTime; // 创建日期
    private String modid; // 行ID
    private Integer sortSeq; // 行号
    private String moLotCode; // 生产批号
    private String cinvcode; // 存货名称
    private String cinvname; // 存货名称
    private String cinvstd; // 规格型号
    private String moClass; // 订单类型
    private String cComUnitCode; // 计量单位编码
    private String cComUnitName; // 计量单位名称
    private String remark; // 行备注
    private Double qty; // 生产数量
    private Double qualifiedInQty; // 入库数量
    private String status; // 状态 3为已审核

    private Date start; // 开始日期
    private Date end; // 结束日期

    public String getMoId() {
        return moId;
    }

    public U8Morder setMoId(String moId) {
        this.moId = moId;
        return this;
    }

    public String getMoCode() {
        return moCode;
    }

    public U8Morder setMoCode(String moCode) {
        this.moCode = moCode;
        return this;
    }

    public Date getStartdate() {
        return startdate;
    }

    public U8Morder setStartdate(Date startdate) {
        this.startdate = startdate;
        return this;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public U8Morder setDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getCdepcode() {
        return cdepcode;
    }

    public U8Morder setCdepcode(String cdepcode) {
        this.cdepcode = cdepcode;
        return this;
    }

    public String getCdepname() {
        return cdepname;
    }

    public U8Morder setCdepname(String cdepname) {
        this.cdepname = cdepname;
        return this;
    }

    public String getModid() {
        return modid;
    }

    public U8Morder setModid(String modid) {
        this.modid = modid;
        return this;
    }

    public Integer getSortSeq() {
        return sortSeq;
    }

    public U8Morder setSortSeq(Integer sortSeq) {
        this.sortSeq = sortSeq;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public U8Morder setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public U8Morder setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public U8Morder setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getMoClass() {
        return moClass;
    }

    public U8Morder setMoClass(String moClass) {
        this.moClass = moClass;
        return this;
    }

    public String getcComUnitCode() {
        return cComUnitCode;
    }

    public U8Morder setcComUnitCode(String cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8Morder setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public U8Morder setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Double getQty() {
        return qty;
    }

    public U8Morder setQty(Double qty) {
        this.qty = qty;
        return this;
    }

    public Double getQualifiedInQty() {
        return qualifiedInQty;
    }

    public U8Morder setQualifiedInQty(Double qualifiedInQty) {
        this.qualifiedInQty = qualifiedInQty;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public U8Morder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public U8Morder setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public U8Morder setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public U8Morder setEnd(Date end) {
        this.end = end;
        return this;
    }

    public String getMoLotCode() {
        return moLotCode;
    }

    public U8Morder setMoLotCode(String moLotCode) {
        this.moLotCode = moLotCode;
        return this;
    }
}
