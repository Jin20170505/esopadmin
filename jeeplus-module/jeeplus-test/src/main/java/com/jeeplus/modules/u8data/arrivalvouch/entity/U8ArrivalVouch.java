package com.jeeplus.modules.u8data.arrivalvouch.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class U8ArrivalVouch extends DataEntity<U8ArrivalVouch> {
    private String mid;//	到货单主表id
    private String autoid;//	到货单子表id
    private String ccode;//			到货单号
    private String ivouchrowno;//		到货单行号
    private Date ddate;//			日期
    private String cvencode;//		供货单位编号
    private String cvenabbname;//		供应商
    private String cvenname;//		供应商全称
    private String cdepname;//		部门
    private String cdepcode;//		部门编号
    private String cmaker;//			制单人
    private String cpersonname;//		业务员
    private String cwhcode;//			仓库编码
    private String cwhname;//			仓库名称
    private String cinvcode;//		存货编码
    private String cinvname;//		存货名称
    private String cinvstd;//			规格型号
    private String cinvmunit;//		主计量单位
    private Double iquantity;//		数量
    private Double iinvexchrate;//	换算率
    private String vouchstate;//		单据状态
    private String cbcloser;//		行关闭人
    private String csocode;//			需求跟踪号
    private String  irowno;//			跟踪行号
    private String CINSPECTCODE;//	报检单单据编号
    private String INSPECTID;//		报检单主表编号 ,
    private String INSPECTAUTOID;//	报检单子表编号
    private Double FQUANTITY;//	报检数量
    private Double FREGQUANTITY;//	合格数量
    private Double frealquantity;//	实收数量（已入库数量）

    public String getMid() {
        return mid;
    }

    public U8ArrivalVouch setMid(String mid) {
        this.mid = mid;
        return this;
    }

    public String getAutoid() {
        return autoid;
    }

    public U8ArrivalVouch setAutoid(String autoid) {
        this.autoid = autoid;
        return this;
    }

    public String getCcode() {
        return ccode;
    }

    public U8ArrivalVouch setCcode(String ccode) {
        this.ccode = ccode;
        return this;
    }

    public String getIvouchrowno() {
        return ivouchrowno;
    }

    public U8ArrivalVouch setIvouchrowno(String ivouchrowno) {
        this.ivouchrowno = ivouchrowno;
        return this;
    }

    public Date getDdate() {
        return ddate;
    }

    public U8ArrivalVouch setDdate(Date ddate) {
        this.ddate = ddate;
        return this;
    }

    public String getCvencode() {
        return cvencode;
    }

    public U8ArrivalVouch setCvencode(String cvencode) {
        this.cvencode = cvencode;
        return this;
    }

    public String getCvenabbname() {
        return cvenabbname;
    }

    public U8ArrivalVouch setCvenabbname(String cvenabbname) {
        this.cvenabbname = cvenabbname;
        return this;
    }

    public String getCvenname() {
        return cvenname;
    }

    public U8ArrivalVouch setCvenname(String cvenname) {
        this.cvenname = cvenname;
        return this;
    }

    public String getCdepname() {
        return cdepname;
    }

    public U8ArrivalVouch setCdepname(String cdepname) {
        this.cdepname = cdepname;
        return this;
    }

    public String getCdepcode() {
        return cdepcode;
    }

    public U8ArrivalVouch setCdepcode(String cdepcode) {
        this.cdepcode = cdepcode;
        return this;
    }

    public String getCmaker() {
        return cmaker;
    }

    public U8ArrivalVouch setCmaker(String cmaker) {
        this.cmaker = cmaker;
        return this;
    }

    public String getCpersonname() {
        return cpersonname;
    }

    public U8ArrivalVouch setCpersonname(String cpersonname) {
        this.cpersonname = cpersonname;
        return this;
    }

    public String getCwhcode() {
        return cwhcode;
    }

    public U8ArrivalVouch setCwhcode(String cwhcode) {
        this.cwhcode = cwhcode;
        return this;
    }

    public String getCwhname() {
        return cwhname;
    }

    public U8ArrivalVouch setCwhname(String cwhname) {
        this.cwhname = cwhname;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public U8ArrivalVouch setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public U8ArrivalVouch setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public U8ArrivalVouch setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getCinvmunit() {
        return cinvmunit;
    }

    public U8ArrivalVouch setCinvmunit(String cinvmunit) {
        this.cinvmunit = cinvmunit;
        return this;
    }

    public Double getIquantity() {
        return iquantity;
    }

    public U8ArrivalVouch setIquantity(Double iquantity) {
        this.iquantity = iquantity;
        return this;
    }

    public Double getIinvexchrate() {
        return iinvexchrate;
    }

    public U8ArrivalVouch setIinvexchrate(Double iinvexchrate) {
        this.iinvexchrate = iinvexchrate;
        return this;
    }

    public String getVouchstate() {
        return vouchstate;
    }

    public U8ArrivalVouch setVouchstate(String vouchstate) {
        this.vouchstate = vouchstate;
        return this;
    }

    public String getCbcloser() {
        return cbcloser;
    }

    public U8ArrivalVouch setCbcloser(String cbcloser) {
        this.cbcloser = cbcloser;
        return this;
    }

    public String getCsocode() {
        return csocode;
    }

    public U8ArrivalVouch setCsocode(String csocode) {
        this.csocode = csocode;
        return this;
    }

    public String getIrowno() {
        return irowno;
    }

    public U8ArrivalVouch setIrowno(String irowno) {
        this.irowno = irowno;
        return this;
    }

    public String getCINSPECTCODE() {
        return CINSPECTCODE;
    }

    public U8ArrivalVouch setCINSPECTCODE(String CINSPECTCODE) {
        this.CINSPECTCODE = CINSPECTCODE;
        return this;
    }

    public String getINSPECTID() {
        return INSPECTID;
    }

    public U8ArrivalVouch setINSPECTID(String INSPECTID) {
        this.INSPECTID = INSPECTID;
        return this;
    }

    public String getINSPECTAUTOID() {
        return INSPECTAUTOID;
    }

    public U8ArrivalVouch setINSPECTAUTOID(String INSPECTAUTOID) {
        this.INSPECTAUTOID = INSPECTAUTOID;
        return this;
    }

    public Double getFQUANTITY() {
        return FQUANTITY;
    }

    public U8ArrivalVouch setFQUANTITY(Double FQUANTITY) {
        this.FQUANTITY = FQUANTITY;
        return this;
    }

    public Double getFREGQUANTITY() {
        return FREGQUANTITY;
    }

    public U8ArrivalVouch setFREGQUANTITY(Double FREGQUANTITY) {
        this.FREGQUANTITY = FREGQUANTITY;
        return this;
    }

    public Double getFrealquantity() {
        return frealquantity;
    }

    public U8ArrivalVouch setFrealquantity(Double frealquantity) {
        this.frealquantity = frealquantity;
        return this;
    }
}
