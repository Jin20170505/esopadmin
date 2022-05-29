package org.jeeplus.u8.webservice.otherinck.entity;

public class U8OtherInCkMx {
    /// <summary>
    /// 货位编码
    /// </summary>
    private String cPosition;

    /// <summary>
    /// 数量
    /// </summary>
    private String iQuantity;

    /// <summary>
    /// 批号
    /// </summary>
    private String cBatch;
    /// 生产日期
    private String dMadeDate;
    /// <summary>
    /// 行号 
    /// </summary>
    private String iRSRowNO;

    /// <summary>
    /// 存货编码
    /// </summary>
    private String cInvCode;

    public String getcPosition() {
        return cPosition;
    }

    public U8OtherInCkMx setcPosition(String cPosition) {
        this.cPosition = cPosition;
        return this;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public U8OtherInCkMx setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
        return this;
    }

    public String getcBatch() {
        return cBatch;
    }

    public U8OtherInCkMx setcBatch(String cBatch) {
        this.cBatch = cBatch;
        return this;
    }

    public String getiRSRowNO() {
        return iRSRowNO;
    }

    public U8OtherInCkMx setiRSRowNO(String iRSRowNO) {
        this.iRSRowNO = iRSRowNO;
        return this;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public U8OtherInCkMx setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public U8OtherInCkMx setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
        return this;
    }
}
