package org.jeeplus.u8.webservice.huoweitiaozheng.entity;

public class U8WebHuoWeiTiaoZhengMxBean {
    private String irowno; // 序号
    private String cinvcode; // 存货编码
    private String cBatch; // 批号
    private String iQuantity; // 数量
    private String cBPosCode; // 调整前货位编码
    private String cAPosCode; // 调整后货位编码

    public String getIrowno() {
        return irowno;
    }

    public U8WebHuoWeiTiaoZhengMxBean setIrowno(String irowno) {
        this.irowno = irowno;
        return this;
    }

    public String getCinvcode() {
        return cinvcode;
    }

    public U8WebHuoWeiTiaoZhengMxBean setCinvcode(String cinvcode) {
        this.cinvcode = cinvcode;
        return this;
    }

    public String getcBatch() {
        return cBatch;
    }

    public U8WebHuoWeiTiaoZhengMxBean setcBatch(String cBatch) {
        this.cBatch = cBatch;
        return this;
    }

    public String getiQuantity() {
        return iQuantity;
    }

    public U8WebHuoWeiTiaoZhengMxBean setiQuantity(String iQuantity) {
        this.iQuantity = iQuantity;
        return this;
    }

    public String getcBPosCode() {
        return cBPosCode;
    }

    public U8WebHuoWeiTiaoZhengMxBean setcBPosCode(String cBPosCode) {
        this.cBPosCode = cBPosCode;
        return this;
    }

    public String getcAPosCode() {
        return cAPosCode;
    }

    public U8WebHuoWeiTiaoZhengMxBean setcAPosCode(String cAPosCode) {
        this.cAPosCode = cAPosCode;
        return this;
    }
}
