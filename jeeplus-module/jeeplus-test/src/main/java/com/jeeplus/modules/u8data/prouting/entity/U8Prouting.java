package com.jeeplus.modules.u8data.prouting.entity;

import com.jeeplus.core.persistence.DataEntity;

import java.util.Date;

/**
 * U8工艺路线（主）
 */
public class U8Prouting extends DataEntity<U8Prouting> {
    private String proutingid;
    private String auxUnitCode;
    private String status;
    private String rountingType;
    private Double changeRate;
    private String version;
    private String versionDesc;
    private Date versionEffDate;
    private Date versionEndDate;
    private String identCode;
    private String identDesc;
    private String cinvcode;
    private String cinvname;
    private String cinvstd;

    public String getProutingid() {
        return proutingid;
    }

    public U8Prouting setProutingid(String proutingid) {
        this.proutingid = proutingid;
        return this;
    }

    public String getAuxUnitCode() {
        return auxUnitCode;
    }

    public U8Prouting setAuxUnitCode(String auxUnitCode) {
        this.auxUnitCode = auxUnitCode;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public U8Prouting setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getRountingType() {
        return rountingType;
    }

    public U8Prouting setRountingType(String rountingType) {
        this.rountingType = rountingType;
        return this;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public U8Prouting setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public U8Prouting setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public U8Prouting setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
        return this;
    }

    public Date getVersionEffDate() {
        return versionEffDate;
    }

    public U8Prouting setVersionEffDate(Date versionEffDate) {
        this.versionEffDate = versionEffDate;
        return this;
    }

    public Date getVersionEndDate() {
        return versionEndDate;
    }

    public U8Prouting setVersionEndDate(Date versionEndDate) {
        this.versionEndDate = versionEndDate;
        return this;
    }

    public String getIdentCode() {
        return identCode;
    }

    public U8Prouting setIdentCode(String identCode) {
        this.identCode = identCode;
        return this;
    }

    public String getIdentDesc() {
        return identDesc;
    }

    public U8Prouting setIdentDesc(String identDesc) {
        this.identDesc = identDesc;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public U8Prouting setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public U8Prouting setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public U8Prouting setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }
}
