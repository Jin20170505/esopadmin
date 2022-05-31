package com.jeeplus.modules.u8data.ommoarrivalvouch.entity;

import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

public class U8OmmoArrivalVouch extends DataEntity<U8OmmoArrivalVouch> {
    private String mid;//	到货单主表id
    private String autoid;//	到货单子表id
    private String ccode;//			到货单号
    private Integer ivouchrowno;//		到货单行号
    private Date ddate;//			日期
    private String cvencode;//		供货单位编号
    private String cvenabbname;//		供应商
    private String cvenname;//		供应商全称
    private String cdepname;//		部门
    private String cdepcode;//	部门编号
    private String cmaker;//			制单人
    private String cpersonname;//		业务员
    private String cwhcode;//		仓库编码
    private String cwhname;//			仓库名称
    private String cinvcode;//		存货编码
    private String cinvname;//		存货名称
    private String cinvstd;//		规格型号
    private String cinvmunit;//		主计量单位
    private Double iquantity;//		数量
    private String vouchstate;//		单据状态
    private String cbcloser;//	行关闭人
    private String csocode;//			需求跟踪号
    private Integer irowno;//			跟踪行号
    private String cInvDefine1;// 最小包装量（存货自定义项1）
    private String cposcode;//	推荐货位（已拼接）
    private String cbatch;//		推荐批号（已拼接）
    private Date dpdate;// 		生产日期
    private Date start; // 开始日期
    private Date end; // 结束日期

    public Double getMinNum(){
        if(StringUtils.isEmpty(cInvDefine1)){
            return null;
        }else {
            return Double.valueOf(cInvDefine1);
        }

    }
    public String getScdate(){
        if(dpdate==null){
            return "";
        }else {
            return DateUtils.formatDate(dpdate,"yyyy-MM-dd");
        }
    }
    public String getMid() {
        return mid;
    }

    public U8OmmoArrivalVouch setMid(String mid) {
        this.mid = mid;
        return this;
    }

    public String getAutoid() {
        return autoid;
    }

    public U8OmmoArrivalVouch setAutoid(String autoid) {
        this.autoid = autoid;
        return this;
    }

    public String getCcode() {
        return ccode;
    }

    public U8OmmoArrivalVouch setCcode(String ccode) {
        this.ccode = ccode;
        return this;
    }

    public Integer getIvouchrowno() {
        return ivouchrowno;
    }

    public U8OmmoArrivalVouch setIvouchrowno(Integer ivouchrowno) {
        this.ivouchrowno = ivouchrowno;
        return this;
    }

    public Date getDdate() {
        return ddate;
    }

    public U8OmmoArrivalVouch setDdate(Date ddate) {
        this.ddate = ddate;
        return this;
    }

    public String getCvencode() {
        return cvencode;
    }

    public U8OmmoArrivalVouch setCvencode(String cvencode) {
        this.cvencode = cvencode;
        return this;
    }

    public String getCvenabbname() {
        return cvenabbname;
    }

    public U8OmmoArrivalVouch setCvenabbname(String cvenabbname) {
        this.cvenabbname = cvenabbname;
        return this;
    }

    public String getCvenname() {
        return cvenname;
    }

    public U8OmmoArrivalVouch setCvenname(String cvenname) {
        this.cvenname = cvenname;
        return this;
    }

    public String getCdepname() {
        return cdepname;
    }

    public U8OmmoArrivalVouch setCdepname(String cdepname) {
        this.cdepname = cdepname;
        return this;
    }

    public String getCdepcode() {
        return cdepcode;
    }

    public U8OmmoArrivalVouch setCdepcode(String cdepcode) {
        this.cdepcode = cdepcode;
        return this;
    }

    public String getCmaker() {
        return cmaker;
    }

    public U8OmmoArrivalVouch setCmaker(String cmaker) {
        this.cmaker = cmaker;
        return this;
    }

    public String getCpersonname() {
        return cpersonname;
    }

    public U8OmmoArrivalVouch setCpersonname(String cpersonname) {
        this.cpersonname = cpersonname;
        return this;
    }

    public String getCwhcode() {
        return cwhcode;
    }

    public U8OmmoArrivalVouch setCwhcode(String cwhcode) {
        this.cwhcode = cwhcode;
        return this;
    }

    public String getCwhname() {
        return cwhname;
    }

    public U8OmmoArrivalVouch setCwhname(String cwhname) {
        this.cwhname = cwhname;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public U8OmmoArrivalVouch setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public U8OmmoArrivalVouch setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public U8OmmoArrivalVouch setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public String getCinvmunit() {
        return cinvmunit;
    }

    public U8OmmoArrivalVouch setCinvmunit(String cinvmunit) {
        this.cinvmunit = cinvmunit;
        return this;
    }

    public Double getIquantity() {
        return iquantity;
    }

    public U8OmmoArrivalVouch setIquantity(Double iquantity) {
        this.iquantity = iquantity;
        return this;
    }

    public String getVouchstate() {
        return vouchstate;
    }

    public U8OmmoArrivalVouch setVouchstate(String vouchstate) {
        this.vouchstate = vouchstate;
        return this;
    }

    public String getCbcloser() {
        return cbcloser;
    }

    public U8OmmoArrivalVouch setCbcloser(String cbcloser) {
        this.cbcloser = cbcloser;
        return this;
    }

    public String getCsocode() {
        return csocode;
    }

    public U8OmmoArrivalVouch setCsocode(String csocode) {
        this.csocode = csocode;
        return this;
    }

    public Integer getIrowno() {
        return irowno;
    }

    public U8OmmoArrivalVouch setIrowno(Integer irowno) {
        this.irowno = irowno;
        return this;
    }

    public String getcInvDefine1() {
        return cInvDefine1;
    }

    public U8OmmoArrivalVouch setcInvDefine1(String cInvDefine1) {
        this.cInvDefine1 = cInvDefine1;
        return this;
    }

    public String getCposcode() {
        return cposcode;
    }

    public U8OmmoArrivalVouch setCposcode(String cposcode) {
        this.cposcode = cposcode;
        return this;
    }

    public String getCbatch() {
        return cbatch;
    }

    public U8OmmoArrivalVouch setCbatch(String cbatch) {
        this.cbatch = cbatch;
        return this;
    }

    public Date getDpdate() {
        return dpdate;
    }

    public U8OmmoArrivalVouch setDpdate(Date dpdate) {
        this.dpdate = dpdate;
        return this;
    }

    public Date getStart() {
        return start;
    }

    public U8OmmoArrivalVouch setStart(Date start) {
        this.start = start;
        return this;
    }

    public Date getEnd() {
        return end;
    }

    public U8OmmoArrivalVouch setEnd(Date end) {
        this.end = end;
        return this;
    }
}
