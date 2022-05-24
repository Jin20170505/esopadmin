package com.jeeplus.modules.u8data.arrivalvouch.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class U8ArrivalVouch extends DataEntity<U8ArrivalVouch> {
    private String mid;//	到货单主表id
    private String autoid;//	到货单子表id
    private String ccode;//			到货单号
    private Integer ivouchrowno;//		到货单行号
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
    private Double cInvDefine1;// 最小包装量（存货自定义项1）
    private String cposcode;// 推荐货位（已拼接）
    private String cbatch;//  推荐批号（已拼接）
    private String  dpdate;// 生产日期
    private String cbcloser;//		行关闭人
    private String cordercode;//			需求跟踪号
    private String  irowno;//			跟踪行号
    private Date start; // 开始日期
    private Date end; // 结束日期

    public Date getStart() {
        return start;
    }

    public U8ArrivalVouch setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public U8ArrivalVouch setEnd(Date end) {
        this.end = end;
        return this;
    }

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

    public Integer getIvouchrowno() {
        return ivouchrowno;
    }

    public U8ArrivalVouch setIvouchrowno(Integer ivouchrowno) {
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

    public String getIrowno() {
        return irowno;
    }

    public U8ArrivalVouch setIrowno(String irowno) {
        this.irowno = irowno;
        return this;
    }

    public String getCordercode() {
        return cordercode;
    }

    public U8ArrivalVouch setCordercode(String cordercode) {
        this.cordercode = cordercode;
        return this;
    }

    public Double getcInvDefine1() {
        return cInvDefine1;
    }

    public U8ArrivalVouch setcInvDefine1(Double cInvDefine1) {
        this.cInvDefine1 = cInvDefine1;
        return this;
    }

    public String getCposcode() {
        return cposcode;
    }

    public U8ArrivalVouch setCposcode(String cposcode) {
        this.cposcode = cposcode;
        return this;
    }

    public String getCbatch() {
        return cbatch;
    }

    public U8ArrivalVouch setCbatch(String cbatch) {
        this.cbatch = cbatch;
        return this;
    }

    public String getDpdate() {
        if(dpdate!=null){
            dpdate = dpdate.split(" ")[0];
        }
        return dpdate;
    }

    public U8ArrivalVouch setDpdate(String dpdate) {
        this.dpdate = dpdate;
        return this;
    }
}
