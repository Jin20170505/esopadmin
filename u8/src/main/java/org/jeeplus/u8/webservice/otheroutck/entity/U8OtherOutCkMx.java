package org.jeeplus.u8.webservice.otheroutck.entity;

public class U8OtherOutCkMx {

    /// <summary>
    /// 货位编码
    /// </summary>
    private String cPosition;

    /// 生产日期
    private String dMadeDate;

    /// <summary>
    /// 数量
    /// </summary>
    private String iQuantity;

    /// <summary>
    /// 批号
    /// </summary>
    private String cBatch;

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

    public U8OtherOutCkMx setcPosition(String cPosition) {
        this.cPosition = cPosition;
        return this;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public U8OtherOutCkMx setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
        return this;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public U8OtherOutCkMx setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
        return this;
    }

    public String getcBatch() {
        return cBatch;
    }

    public U8OtherOutCkMx setcBatch(String cBatch) {
        this.cBatch = cBatch;
        return this;
    }

    public String getiRSRowNO() {
        return iRSRowNO;
    }

    public U8OtherOutCkMx setiRSRowNO(String iRSRowNO) {
        this.iRSRowNO = iRSRowNO;
        return this;
    }

    public String getcInvCode() {
        return cInvCode;
    }

    public U8OtherOutCkMx setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    @Override
    public String toString() {
        return "U8OtherOutCkMx{" +
                "cPosition='" + cPosition + '\'' +
                ", dMadeDate='" + dMadeDate + '\'' +
                ", iQuantity='" + iQuantity + '\'' +
                ", cBatch='" + cBatch + '\'' +
                ", iRSRowNO='" + iRSRowNO + '\'' +
                ", cInvCode='" + cInvCode + '\'' +
                '}';
    }
}
