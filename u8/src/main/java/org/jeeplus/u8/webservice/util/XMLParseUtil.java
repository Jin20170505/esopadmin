package org.jeeplus.u8.webservice.util;

import org.dom4j.DocumentException;

import java.io.UnsupportedEncodingException;

public final class XMLParseUtil {

    public static void main(String[] args) throws UnsupportedEncodingException, DocumentException {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
                "<soap:Body>" +
                "<YTTransVouchResponse xmlns=\"http://tempuri.org/\">" +
                "<YTTransVouchResult>{\"count\":\"1\",\"message\":\"转出仓库编码不存在\",\"id\":\"\"}</YTTransVouchResult>" +
                "</YTTransVouchResponse>" +
                "</soap:Body>" +
                "</soap:Envelope>";

        int index = xml.indexOf("{");
        int last = xml.lastIndexOf("}")+1;
        System.out.println(xml.substring(index,last));
    }
}
