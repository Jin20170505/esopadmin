package org.jeeplus.u8.webservice.entity;

/**
 * U8 WebService 返回结果
 * <?xml version="1.0" encoding="utf-8"?><soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"><soap:Body><YTTransVouchResponse xmlns="http://tempuri.org/"><YTTransVouchResult>{"count":"1","message":"转出仓库编码不存在","id":""}</YTTransVouchResult></YTTransVouchResponse></soap:Body></soap:Envelope>
 */
public class U8WebServiceResult {
    private String count;
    private String message;

    public String getCount() {
        return count;
    }

    public U8WebServiceResult setCount(String count) {
        this.count = count;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public U8WebServiceResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
