package org.jeeplus.u8.webservice.otheroutck;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.jeeplus.u8.webservice.otheroutck.entity.U8OtherOutCkMain;
import org.jeeplus.u8.webservice.otheroutck.entity.U8OtherOutCkMx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.jeeplus.u8.webservice.util.XMLParseUtil.responseWeb;

public final class U8OtherOutCkWebService {

    public static final U8WebServiceResult outck(U8OtherOutCkMain main) throws IOException {
        Map<String,Object> param = new LinkedHashMap<>();
        param.put("cCode",main.getcCode());
        param.put("cWhCode",main.getcWhCode());
        param.put("dDate",main.getdDate());
        param.put("crdcode",main.getCrdcode());
        param.put("cDepCode",main.getcDepCode());
        param.put("MesCode",main.getMesCode());
        param.put("cMemo",main.getcMemo());
        param.put("cMaker",main.getcMaker());

        List<Map<String, Object>> details = Lists.newArrayList();
        for (U8OtherOutCkMx bean:main.getDetails())
        {
            Map<String, Object> rd = new LinkedHashMap<String, Object>();
            rd.put("cInvCode",bean.getcInvCode());
            rd.put("iQuantity",bean.getiQuantity());
            rd.put("cBatch",bean.getcBatch());
            rd.put("cPosition",bean.getcPosition());
            rd.put("dMadeDate",bean.getdMadeDate());
            rd.put("iRSRowNO",bean.getiRSRowNO());
            details.add(rd);
        }
        param.put("Details",details);
        JSONObject ss = JSONObject.fromObject(param);
        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTRdRecord09 xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTRdRecord09>");
        soap.append("</soap:Body>");
        soap.append("</soap:Envelope>");
        String jsons = soap.toString();
        //接受返回报文
        String result = "";
        URL u = new URL(U8Url.URL);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoInput(true);
        //允许对外输出数据
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setDefaultUseCaches(false);
        conn.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
        //soap
        conn.setRequestProperty("Content-Length",String.valueOf(jsons.length()));
        conn.setRequestMethod("POST");
        //定义输出流
        OutputStream output = conn.getOutputStream();
        if(StringUtils.isNotBlank(jsons)){
            byte[] b = jsons.getBytes("UTF-8");
            //发送soap请求报文
            output.write(b,0,b.length);
            output.flush();
            output.close();
            //定义输入流，获取soap报文
            InputStream input = conn.getInputStream();
            //设置编码格式
            result = IOUtils.toString(input,"UTF-8");
            input.close();
        }
        System.out.println("请求返回报文：" + result);
        return responseWeb(result);
    }

}
