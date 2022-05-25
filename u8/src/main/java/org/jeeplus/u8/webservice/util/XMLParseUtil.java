package org.jeeplus.u8.webservice.util;

import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;

public final class XMLParseUtil {

    public static U8WebServiceResult responseWeb(String xml) {
        U8WebServiceResult result = new U8WebServiceResult();
        if(StringUtils.isEmpty(xml)){
            result.setCount("1");
            result.setMessage("U8返回空报文");
            return result;
        }
        int index = xml.indexOf("{");int last = xml.lastIndexOf("}")+1;
        if(index<0|| last<1){
            result.setCount("1");
            result.setMessage("U8返回无法解析的报文："+xml);
            return result;
        }
        String rs = xml.substring(index,last);
        try {
            JSONObject json = JSONObject.fromObject(rs);
            result.setCount(json.optString("count","1"));
            result.setMessage(json.optString("message","没有message节点"));
            return result;
        }catch (Exception e){
            result.setCount("1");
            result.setMessage("无法解析结果："+rs);
            return result;
        }

    }
//    public static void main(String[] args) throws UnsupportedEncodingException, DocumentException {
//        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
//                "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">" +
//                "<soap:Body>" +
//                "<YTTransVouchResponse xmlns=\"http://tempuri.org/\">" +
//                "<YTTransVouchResult>{\"count\":\"1\",\"message\":\"转出仓库编码不存在\",\"id\":\"\"}</YTTransVouchResult>" +
//                "</YTTransVouchResponse>" +
//                "</soap:Body>" +
//                "</soap:Envelope>";
//
//        int index = xml.indexOf("{");
//        int last = xml.lastIndexOf("}")+1;
//        System.out.println(xml.substring(index,last));
//    }
}
