package org.jeeplus.u8.webservice.xiaoshouchuku;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.jeeplus.u8.webservice.xiaoshouchuku.entity.U8WebXiaoShouBean;
import org.jeeplus.u8.webservice.xiaoshouchuku.entity.U8WebXiaoShouMxBean;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.jeeplus.u8.webservice.util.XMLParseUtil.responseWeb;

public final class U8XiaoShouChuKuWebService {
    /**
     * 销售出库
     * @param xiaoShouBean
     * @return
     * @throws Exception
     */
    public static U8WebServiceResult xiaoshouchuku(U8WebXiaoShouBean xiaoShouBean) throws Exception {
        Map<String,Object> param = new LinkedHashMap<>();
        param.put("Code",xiaoShouBean.getCode());
        param.put("cWhCode",xiaoShouBean.getcWhCode());
        param.put("dDate",xiaoShouBean.getdDate());
        param.put("cRdCode",xiaoShouBean.getcRdCode());
        param.put("cDepCode",xiaoShouBean.getcDepCode());
        param.put("cPersonCode",xiaoShouBean.getcPersonCode());
        param.put("cSTCode",xiaoShouBean.getcSTCode());
        param.put("cCusCode",xiaoShouBean.getcCusCode());
        param.put("bredvouch",xiaoShouBean.getBredvouch());
        param.put("cMemo",xiaoShouBean.getcMemo());
        param.put("cMaker",xiaoShouBean.getcMaker());

        List<Map<String, Object>> details = Lists.newArrayList();
        for (U8WebXiaoShouMxBean bean:xiaoShouBean.getDetails())
        {
            Map<String, Object> rd = new LinkedHashMap<String, Object>();
            rd.put("cInvCode",bean.getcInvCode());
            rd.put("iQuantity",bean.getiQuantity());
            rd.put("cBatch",bean.getcBatch());
            rd.put("cPosition",bean.getcPosition());
            rd.put("dMadeDate",bean.getdMadeDate());
            rd.put("irowno",bean.getIrowno());
            rd.put("iDLsID",bean.getiDLsID());
            rd.put("cDLCode",bean.getcDLCode());
            details.add(rd);
        }
        param.put("Details",details);
        JSONObject ss = JSONObject.fromObject(param);
        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTRdrecord32 xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTRdrecord32>");
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
