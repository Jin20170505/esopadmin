package com.jeeplus.modules.u8data.unit.entity;

import com.jeeplus.core.persistence.DataEntity;

public class U8Unit extends DataEntity<U8Unit> {
    private String cGroupCode; // 计量单位组编码
    private String cGroupName; // 计量单位组名称
    private String iGroupType; // 计量单位组类别
    private String cComunitCode; // 计量单位编码
    private String cComUnitName; // 计量单位名称
    private String bMainUnit;   // 是否主计量单位

    public String getbMainUnit() {
        return bMainUnit;
    }

    public U8Unit setbMainUnit(String bMainUnit) {
        this.bMainUnit = bMainUnit;
        return this;
    }

    public String getcGroupCode() {
        return cGroupCode;
    }

    public U8Unit setcGroupCode(String cGroupCode) {
        this.cGroupCode = cGroupCode;
        return this;
    }

    public String getcGroupName() {
        return cGroupName;
    }

    public U8Unit setcGroupName(String cGroupName) {
        this.cGroupName = cGroupName;
        return this;
    }

    public String getiGroupType() {
        return iGroupType;
    }

    public U8Unit setiGroupType(String iGroupType) {
        this.iGroupType = iGroupType;
        return this;
    }

    public String getcComunitCode() {
        return cComunitCode;
    }

    public U8Unit setcComunitCode(String cComunitCode) {
        this.cComunitCode = cComunitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8Unit setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }
}
