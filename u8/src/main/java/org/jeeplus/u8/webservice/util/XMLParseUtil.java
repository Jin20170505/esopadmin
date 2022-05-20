package org.jeeplus.u8.webservice.util;

import com.google.common.collect.Lists;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class XMLParseUtil {

    public static Map<String, Object> getValueByNode(String xml, List<String> nodes, String charsetName) throws DocumentException, UnsupportedEncodingException {
        Document document = new SAXReader().read(new ByteArrayInputStream(xml.getBytes(charsetName)));
        Map<String, Object> result = new HashMap<>();
        nodes.forEach(node -> {
            String xpath = "//" + node;
            Node singleNode = document.selectSingleNode(xpath);
            if(singleNode != null) {
                result.put(node, singleNode.getStringValue());
            }
        });
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, DocumentException {
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><YTTransVouchResponse xmlns=\"http://tempuri.org/\"><YTTransVouchResult>{\"count\":\"1\",\"message\":\"转出仓库编码不存在\",\"id\":\"\"}</YTTransVouchResult></YTTransVouchResponse></soap:Body></soap:Envelope>";
        List<String> nodes = Lists.newArrayList();
        nodes.add("soap:Envelope");
        nodes.add("soap:Body");
        nodes.add("YTTransVouchResponse");
        nodes.add("YTTransVouchResult");
       int i = xml.indexOf("<YTTransVouchResult>") + "<YTTransVouchResult>".length();
        System.out.println(i);
       int j = xml.indexOf("</YTTransVouchResult>");
        System.out.println(j);
       xml = xml.substring(i,j);
        System.out.println(xml);
        JSONObject json = JSONObject.fromObject(xml);
        System.out.println(json.toString());
    }
}
