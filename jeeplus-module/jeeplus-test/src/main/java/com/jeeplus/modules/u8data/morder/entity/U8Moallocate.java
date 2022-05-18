package com.jeeplus.modules.u8data.morder.entity;

import com.jeeplus.core.persistence.DataEntity;

/**
 * 生产订单 子件
 */
public class U8Moallocate extends DataEntity<U8Moallocate> {
    private String allocateId; // 子件用料ID
    private String moDId; // 生产订单明细ID
    private Integer sortseq; // 子件行号
    private String invcode;
    private String cinvname;
    private String cinvstd;
    private Double qty; // 数量
    private Double issqty; // 已领数量
    private Double baseQtyN; // 基本用量-分子
    private Double baseQtyD; // 基本用量-分母
    private Double changeRate; // 换算率
    private Double auxBaseQtyN; // 辅助基本用量
    private String productType; // 产出类型
    private String cComUnitCode;// 单位编号
    private String cComUnitName; // 单位名称
    private String remark;

    public String getAllocateId() {
        return allocateId;
    }

    public U8Moallocate setAllocateId(String allocateId) {
        this.allocateId = allocateId;
        return this;
    }

    public String getMoDId() {
        return moDId;
    }

    public U8Moallocate setMoDId(String moDId) {
        this.moDId = moDId;
        return this;
    }

    public Integer getSortseq() {
        return sortseq;
    }

    public U8Moallocate setSortseq(Integer sortseq) {
        this.sortseq = sortseq;
        return this;
    }

    public String getInvcode() {
        return invcode;
    }

    public U8Moallocate setInvcode(String invcode) {
        this.invcode = invcode;
        return this;
    }

    public String getCinvname() {
        return cinvname;
    }

    public U8Moallocate setCinvname(String cinvname) {
        this.cinvname = cinvname;
        return this;
    }

    public String getCinvstd() {
        return cinvstd;
    }

    public U8Moallocate setCinvstd(String cinvstd) {
        this.cinvstd = cinvstd;
        return this;
    }

    public Double getQty() {
        return qty;
    }

    public U8Moallocate setQty(Double qty) {
        this.qty = qty;
        return this;
    }

    public Double getIssqty() {
        return issqty;
    }

    public U8Moallocate setIssqty(Double issqty) {
        this.issqty = issqty;
        return this;
    }

    public Double getBaseQtyN() {
        return baseQtyN;
    }

    public U8Moallocate setBaseQtyN(Double baseQtyN) {
        this.baseQtyN = baseQtyN;
        return this;
    }

    public Double getBaseQtyD() {
        return baseQtyD;
    }

    public U8Moallocate setBaseQtyD(Double baseQtyD) {
        this.baseQtyD = baseQtyD;
        return this;
    }

    public Double getChangeRate() {
        return changeRate;
    }

    public U8Moallocate setChangeRate(Double changeRate) {
        this.changeRate = changeRate;
        return this;
    }

    public Double getAuxBaseQtyN() {
        return auxBaseQtyN;
    }

    public U8Moallocate setAuxBaseQtyN(Double auxBaseQtyN) {
        this.auxBaseQtyN = auxBaseQtyN;
        return this;
    }

    public String getProductType() {
        return productType;
    }

    public U8Moallocate setProductType(String productType) {
        this.productType = productType;
        return this;
    }

    public String getcComUnitCode() {
        return cComUnitCode;
    }

    public U8Moallocate setcComUnitCode(String cComUnitCode) {
        this.cComUnitCode = cComUnitCode;
        return this;
    }

    public String getcComUnitName() {
        return cComUnitName;
    }

    public U8Moallocate setcComUnitName(String cComUnitName) {
        this.cComUnitName = cComUnitName;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public U8Moallocate setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
