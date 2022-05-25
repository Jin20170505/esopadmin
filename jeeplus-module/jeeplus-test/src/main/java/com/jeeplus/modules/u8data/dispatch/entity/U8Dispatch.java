package com.jeeplus.modules.u8data.dispatch.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * 销售发货
 */
public class U8Dispatch extends DataEntity<U8Dispatch> {
    private String mid; // 主表ID
    private String lineid; // 行ID
    private String cdlcode;// 			发货退货单号
    private Integer irowno;//					发货单行号
    private Date fahuoDate; //  发货日期
    private String cCusCode;//				客户编号
    private String ccusname;//				客户名称
    private String ccusabbname;//			客户简称
    private String cdepcode;//				部门编码
    private String cdepname;//				部门名称
    private String cInvCode;//				存货编码
    private String cInvName;//				存货名称
    private String cInvStd;//				规格型号
    private String cComUnitCode;//			计量单位编码
    private String cComUnitName;//			计量单位名称
    private String scdate;//    生产日期
    private Double iquantity;//				数量
    private String cWhCode;//				仓库编码
    private String cWhName;//				仓库名称
    private String cSoCode;//				销售订单号
    private String iorderrowno;//			订单行号
    private Double fOutQuantity;//			累计出库数量
    private String cBatch;//					批号
    private String cPosition;//				货位
    private String cpersoncode;//	业务员编码
    private String cPsnName;//	业务员名称
    private Date start; // 开始日期
    private Date end; // 结束日期
    public Date getFahuoDate() {
        return fahuoDate;
    }

    public U8Dispatch setFahuoDate(Date fahuoDate) {
        this.fahuoDate = fahuoDate;
        return this;
    }

    public String getScdate() {
        return scdate;
    }

    public U8Dispatch setScdate(String scdate) {
        this.scdate = scdate;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public U8Dispatch setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public U8Dispatch setEnd(Date end) {
        this.end = end;
        return this;
    }

    public String getMid() {
        return mid;
    }

    public U8Dispatch setMid(String mid) {
        this.mid = mid;
        return this;
    }

    public String getLineid() {
        return lineid;
    }

    public U8Dispatch setLineid(String lineid) {
        this.lineid = lineid;
        return this;
    }

    public String getCdlcode() {
        return cdlcode;
    }

    public U8Dispatch setCdlcode(String cdlcode) {
        this.cdlcode = cdlcode;
        return this;
    }

    public Integer getIrowno() {
        return irowno;
    }

    public U8Dispatch setIrowno(Integer irowno) {
        this.irowno = irowno;
        return this;
    }

    public String getcCusCode() {
        return cCusCode;
    }

    public U8Dispatch setcCusCode(String cCusCode) {
        this.cCusCode = cCusCode;
        return this;
    }

    public String getCcusname() {
        return ccusname;
    }

    public U8Dispatch setCcusname(String ccusname) {
        this.ccusname = ccusname;
        return this;
    }

    public String getCcusabbname() {
        return ccusabbname;
    }

    public U8Dispatch setCcusabbname(String ccusabbname) {
        this.ccusabbname = ccusabbname;
        return this;
    }

    public String getCdepcode() {
        return cdepcode;
    }

    public U8Dispatch setCdepcode(String cdepcode) {
        this.cdepcode = cdepcode;
        return this;
    }

    public String getCdepname() {
        return cdepname;
    }

    public U8Dispatch setCdepname(String cdepname) {
        this.cdepname = cdepname;
        return this;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public U8Dispatch setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    public String getcInvName() {
        return cInvName;
    }

    public U8Dispatch setcInvName(String cInvName) {
        this.cInvName = cInvName;
        return this;
    }

    public String getcInvStd() {
        return cInvStd;
    }

    public U8Dispatch setcInvStd(String cInvStd) {
        this.cInvStd = cInvStd;
        return this;
    }

    public String getcComUnitCode() {
        return cComUnitCode;
    }

    public U8Dispatch setcComUnitCode(String cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8Dispatch setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }

    public Double getIquantity() {
        return iquantity;
    }

    public U8Dispatch setIquantity(Double iquantity) {
        this.iquantity = iquantity;
        return this;
    }

    public String getcWhCode() {
        return cWhCode;
    }

    public U8Dispatch setcWhCode(String cWhCode) {
        this.cWhCode = cWhCode;
        return this;
    }

    public String getcWhName() {
        return cWhName;
    }

    public U8Dispatch setcWhName(String cWhName) {
        this.cWhName = cWhName;
        return this;
    }

    public String getcSoCode() {
        return cSoCode;
    }

    public U8Dispatch setcSoCode(String cSoCode) {
        this.cSoCode = cSoCode;
        return this;
    }

    public String getIorderrowno() {
        return iorderrowno;
    }

    public U8Dispatch setIorderrowno(String iorderrowno) {
        this.iorderrowno = iorderrowno;
        return this;
    }

    public Double getfOutQuantity() {
        return fOutQuantity;
    }

    public U8Dispatch setfOutQuantity(Double fOutQuantity) {
        this.fOutQuantity = fOutQuantity;
        return this;
    }

    public String getcBatch() {
        return cBatch;
    }

    public U8Dispatch setcBatch(String cBatch) {
        this.cBatch = cBatch;
        return this;
    }

    public String getcPosition() {
        return cPosition;
    }

    public U8Dispatch setcPosition(String cPosition) {
        this.cPosition = cPosition;
        return this;
    }

    public String getCpersoncode() {
        return cpersoncode;
    }

    public U8Dispatch setCpersoncode(String cpersoncode) {
        this.cpersoncode = cpersoncode;
        return this;
    }

    public String getcPsnName() {
        return cPsnName;
    }

    public U8Dispatch setcPsnName(String cPsnName) {
        this.cPsnName = cPsnName;
        return this;
    }
}
