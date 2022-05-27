package com.jeeplus.modules.u8data.ommo.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 委外订单
 */
public class U8OmMoMain extends DataEntity<U8OmMoMain> {
    private String moid; //  委外订单ID
    private String cCode;//		订单编号
    private String cVenCode;//		供应商编码
    private String cVenName;//		供应商名称
    private Date dDate;//		订单日期
    private Date dCreateTime;//	制单时间
    private String cMemo;//		备注
    private String cInvCode;//	存货编码
    private String cInvName;//		存货名称
    private String cInvStd;//			规格型号
    private String cComUnitCode;//	单位编码
    private String cComUnitName;//	单位名称
    private Double iQuantity;//		数量
    private Date dStartDate;//		计划下达日期
    private Date dArriveDate;//		计划到货日期
    private String cbMemo;//			行备注
    private String moDetailsID;//		委外订单子表ID
    private Integer iVouchRowNo;//		委外订单行号
    private Date start; // 开始日期
    private Date end; // 结束日期
    public String getMoid() {
        return moid;
    }

    public U8OmMoMain setMoid(String moid) {
        this.moid = moid;
        return this;
    }

    public String getcCode() {
        return cCode;
    }

    public U8OmMoMain setcCode(String cCode) {
        this.cCode = cCode;
        return this;
    }

    public String getcVenCode() {
        return cVenCode;
    }

    public U8OmMoMain setcVenCode(String cVenCode) {
        this.cVenCode = cVenCode;
        return this;
    }

    public String getcVenName() {
        return cVenName;
    }

    public U8OmMoMain setcVenName(String cVenName) {
        this.cVenName = cVenName;
        return this;
    }

    public Date getdDate() {
        return dDate;
    }

    public U8OmMoMain setdDate(Date dDate) {
        this.dDate = dDate;
        return this;
    }

    public Date getdCreateTime() {
        return dCreateTime;
    }

    public U8OmMoMain setdCreateTime(Date dCreateTime) {
        this.dCreateTime = dCreateTime;
        return this;
    }

    public String getcMemo() {
        return cMemo;
    }

    public U8OmMoMain setcMemo(String cMemo) {
        this.cMemo = cMemo;
        return this;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public U8OmMoMain setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    public String getcInvName() {
        return cInvName;
    }

    public U8OmMoMain setcInvName(String cInvName) {
        this.cInvName = cInvName;
        return this;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public U8OmMoMain setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
        return this;
    }

    public String getcComUnitCode() {
        return cComUnitCode;
    }

    public U8OmMoMain setcComUnitCode(String cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8OmMoMain setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }

    public Double getiQuantity() {
        return iQuantity;
    }

    public U8OmMoMain setiQuantity(Double iQuantity) {
        this.iQuantity = iQuantity;
        return this;
    }

    public Date getdStartDate() {
        return dStartDate;
    }

    public U8OmMoMain setdStartDate(Date dStartDate) {
        this.dStartDate = dStartDate;
        return this;
    }

    public Date getdArriveDate() {
        return dArriveDate;
    }

    public U8OmMoMain setdArriveDate(Date dArriveDate) {
        this.dArriveDate = dArriveDate;
        return this;
    }

    public String getCbMemo() {
        return cbMemo;
    }

    public U8OmMoMain setCbMemo(String cbMemo) {
        this.cbMemo = cbMemo;
        return this;
    }

    public String getMoDetailsID() {
        return moDetailsID;
    }

    public U8OmMoMain setMoDetailsID(String moDetailsID) {
        this.moDetailsID = moDetailsID;
        return this;
    }

    public Integer getiVouchRowNo() {
        return iVouchRowNo;
    }

    public U8OmMoMain setiVouchRowNo(Integer iVouchRowNo) {
        this.iVouchRowNo = iVouchRowNo;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public U8OmMoMain setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public U8OmMoMain setEnd(Date end) {
        this.end = end;
        return this;
    }
}
