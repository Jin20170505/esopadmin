package org.jeeplus.u8.webservice.xiaoshouchuku.entity;

public class U8WebXiaoShouMxBean {
    /// <summary>
    /// 存货编码
    /// </summary>
    private String cInvCode;

    /// <summary>
    /// 数量
    /// </summary>
    private String iQuantity;

    /// <summary>
    /// 批号
    /// </summary>
    private String cBatch;

    /// <summary>
    /// 货位编码
    /// </summary>
    private String cPosition;

    /// <summary>
    /// 生产日期
    /// </summary>
    private String dMadeDate;

    /// <summary>
    ///行号
    /// </summary>
    private String irowno;

    /// <summary>
    ///发货单子表id
    /// </summary>
    private String iDLsID;

    /// <summary>
    ///发货单号
    /// </summary>
    private String cDLCode;

    public String getcInvCode() {
        return cInvCode;
    }

    public U8WebXiaoShouMxBean setcInvCode(String cInvCode) {
        this.cInvCode = cInvCode;
        return this;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public U8WebXiaoShouMxBean setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
        return this;
    }

    public String getcBatch() {
        return cBatch;
    }

    public U8WebXiaoShouMxBean setcBatch(String cBatch) {
        this.cBatch = cBatch;
        return this;
    }

    public String getcPosition() {
        return cPosition;
    }

    public U8WebXiaoShouMxBean setcPosition(String cPosition) {
        this.cPosition = cPosition;
        return this;
    }

    public String getdMadeDate() {
        return dMadeDate;
    }

    public U8WebXiaoShouMxBean setdMadeDate(String dMadeDate) {
        this.dMadeDate = dMadeDate;
        return this;
    }

    public String getIrowno() {
        return irowno;
    }

    public U8WebXiaoShouMxBean setIrowno(String irowno) {
        this.irowno = irowno;
        return this;
    }

    public String getiDLsID() {
        return iDLsID;
    }

    public U8WebXiaoShouMxBean setiDLsID(String iDLsID) {
        this.iDLsID = iDLsID;
        return this;
    }

    public String getcDLCode() {
        return cDLCode;
    }

    public U8WebXiaoShouMxBean setcDLCode(String cDLCode) {
        this.cDLCode = cDLCode;
        return this;
    }

    @Override
    public String toString() {
        return "U8WebXiaoShouMxBean{" +
                "cInvCode='" + cInvCode + '\'' +
                ", iQuantity='" + iQuantity + '\'' +
                ", cBatch='" + cBatch + '\'' +
                ", cPosition='" + cPosition + '\'' +
                ", dMadeDate='" + dMadeDate + '\'' +
                ", irowno='" + irowno + '\'' +
                ", iDLsID='" + iDLsID + '\'' +
                ", cDLCode='" + cDLCode + '\'' +
                '}';
    }
}
