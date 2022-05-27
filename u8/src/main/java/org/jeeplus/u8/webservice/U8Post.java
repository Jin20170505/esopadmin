// U8 WebService 接口
package org.jeeplus.u8.webservice;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import net.sourceforge.jtds.jdbc.DateTime;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.jeeplus.u8.webservice.util.XMLParseUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class U8Post {

    //成品入库单
    public static U8WebServiceResult Rd10Post(YT_Rd10 rd10,String posturl) throws Exception
    {
        Map<String,Object> param = new LinkedHashMap<String, Object>();
        param.put("cBusType",rd10.getcBusType());
        param.put("cSource",rd10.getcSource());
        param.put("cWhCode",rd10.getcWhCode());
        param.put("dDate",rd10.getdDate());
        param.put("cCode",rd10.getcCode());
        param.put("cRdcode",rd10.getcRdcode());
        param.put("cDepCode",rd10.getcDepCode());
        param.put("cMemo",rd10.getcMemo());
        param.put("cMaker",rd10.getcMaker());

        List<Map<String, Object>> Rd10s = Lists.newArrayList();
        for (YT_Rds10 rd1:rd10.getRd10s())
        {
            Map<String, Object> rd = new LinkedHashMap<String, Object>();
            rd.put("cInvCode",rd1.getcInvCode());
            rd.put("iQuantity",rd1.getiQuantity());
            rd.put("cPosition",rd1.getcPosition());
            rd.put("irowno",rd1.getIrowno());
            rd.put("cmocode",rd1.getCmocode());
            rd.put("imoseq",rd1.getImoseq());
            rd.put("cbMemo",rd1.getCbMemo());
            rd.put("dMadeDate",rd1.getdMadeDate());
            rd.put("batch",rd1.getBatch());
            Rd10s.add(rd);
        }
        param.put("Details",Rd10s);
        JSONObject
                ss = JSONObject.fromObject(param);

        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTRdrecord10 xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTRdrecord10>");
        soap.append("</soap:Body>");
        soap.append("</soap:Envelope>");
        String jsons = soap.toString();
        //接受返回报文
        String result = new String();
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
        return XMLParseUtil.responseWeb(result);
    }


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        YT_Rd01 rd01 = new YT_Rd01();
        YT_Rds01 rds01 = new YT_Rds01();
        List<YT_Rds01> s = new ArrayList<>();
        rd01.setCode("cs001");
        rd01.setcBusType("普通采购");
        rd01.setcSource("来料检验单");
        rd01.setcWhCode("01");
        rd01.setdDate("2022-05-20");
        rd01.setcRdCode("11");
        rd01.setcDepCode("05");
        rd01.setcPersonCode("");
        rd01.setcPTCode("01");
        rd01.setcVenCode("G00001");
        rd01.setcMaker("demo");
        rd01.setDnmaketim("2022-05-20");

        rds01.setIrowno("1");
        rds01.setcInvCode("C030000096");
        rds01.setiArrsId("1000000006");
        rds01.setcBatch("1");
        rds01.setcPosition("G-07-02-05");
        rds01.setdMadeDate("2022-05-20");
        rds01.setcARVCode("2205210003");
        rds01.setiQuantity("10");
        s.add(rds01);
        rd01.setRd01s(s);
        try {
            Rd01Post(rd01,U8Url.URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //材料出库单 or 补料 or 委外发料
    public static U8WebServiceResult Rd11Post(YT_Rd11 rd11, String posturl) throws Exception
    {
        Map<String,Object> param = new LinkedHashMap<String, Object>();
        param.put("cBusType",rd11.getcBusType());// 领料/补料/委外发料
        param.put("cSource",rd11.getcSource());
        param.put("cWhCode",rd11.getcWhCode());
        param.put("dDate",rd11.getdDate());
        param.put("cCode",rd11.getcCode());
        param.put("cRdcode",rd11.getcRdcode());
        param.put("cDepCode",rd11.getcDepCode());
        param.put("cMemo",rd11.getcMemo());
        param.put("cMaker",rd11.getcMaker());

        List<Map<String, Object>> Rd11s = Lists.newArrayList();
        for (YT_Rds11 rd1:rd11.getRd11s())
        {
            Map<String, Object> rd = new LinkedHashMap<String, Object>();
            rd.put("cInvCode",rd1.getcInvCode());
            rd.put("iQuantity",rd1.getiQuantity());
            rd.put("cmocode",rd1.getCmocode());
            rd.put("invcode",rd1.getInvcode());
            rd.put("imoseq",rd1.getImoseq());
            rd.put("iopseq",rd1.getIopseq());
            Rd11s.add(rd);
        }
        param.put("Details",Rd11s);
        JSONObject ss = JSONObject.fromObject(param);
        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTRdrecord11 xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTRdrecord11>");
        soap.append("</soap:Body>");
        soap.append("</soap:Envelope>");
        String jsons = soap.toString();
        //接受返回报文
        String result = new String();
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
        return XMLParseUtil.responseWeb(result);
    }

    //调拨单
    public static U8WebServiceResult TranPost(YT_Tran tr,String posturl) throws Exception
    {
        Map<String,Object> param = new LinkedHashMap<String, Object>();
        param.put("cTVCode",tr.getcTVCode());
        param.put("dTVDate",tr.getdTVDate());
        param.put("cOWhCode",tr.getcOWhCode());
        param.put("cIWhCode",tr.getcIWhCode());
        param.put("cIRdCode",tr.getcIRdCode());
        param.put("cORdCode",tr.getcORdCode());
        param.put("cTVMemo",tr.getcTVMemo());

        List<Map<String, Object>> Trans = Lists.newArrayList();
        for (YT_Trans tr1:tr.getTrans())
        {
            Map<String, Object> trs = new LinkedHashMap<String, Object>();
            trs.put("cbMemo",tr1.getCbMemo());
            trs.put("cInvCode",tr1.getcInvCode());
            trs.put("iTVQuantity",tr1.getiTVQuantity());
            trs.put("cTVBatch",tr1.getcTVBatch());
            trs.put("coutposcode",tr1.getCoutposcode());
            trs.put("irowno",tr1.getIrowno());
            trs.put("dMadeDate",tr1.getdMadeDate());
            Trans.add(trs);
        }
        param.put("Details",Trans);
        JSONObject
                ss = JSONObject.fromObject(param);

        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTTransVouch xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTTransVouch>");
        soap.append("</soap:Body>");
        soap.append("</soap:Envelope>");
        String jsons = soap.toString();
        //接受返回报文
        String result = new String();
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
        return XMLParseUtil.responseWeb(result);
    }

    //采购入库单
    public static U8WebServiceResult Rd01Post(YT_Rd01 tr,String posturl) throws Exception
    {
        Map<String,Object> param = new LinkedHashMap<String, Object>();
        param.put("Code",tr.getCode());
        param.put("cBusType",tr.getcBusType());
        param.put("cSource",tr.getcSource());
        param.put("cWhCode",tr.getcWhCode());
        param.put("dDate",tr.getdDate());
        param.put("cRdCode",tr.getcRdCode());
        param.put("cDepCode",tr.getcDepCode());
        param.put("cPersonCode",tr.getcPersonCode());
        param.put("cPTCode",tr.getcPTCode());
        param.put("cVenCode",tr.getcVenCode());
        param.put("cMaker",tr.getcMaker());
        param.put("dnmaketim",tr.getDnmaketim());
        param.put("cExchCode","RMB");

        List<Map<String, Object>> Trans = Lists.newArrayList();
        for (YT_Rds01 tr1:tr.getRd01s())
        {
            Map<String, Object> trs = new LinkedHashMap<String, Object>();
            trs.put("irowno",tr1.getIrowno());
            trs.put("cInvCode",tr1.getcInvCode());
            trs.put("iQuantity",tr1.getiQuantity());
            trs.put("iArrsId",tr1.getiArrsId());
            trs.put("cBatch",tr1.getcBatch());
            trs.put("cPosition",tr1.getcPosition());
            trs.put("dMadeDate",tr1.getdMadeDate());
            trs.put("cARVCode",tr1.getcARVCode());
            Trans.add(trs);
        }
        param.put("Details",Trans);
        JSONObject  ss = JSONObject.fromObject(param);
        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTRdrecord xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTRdrecord>");
        soap.append("</soap:Body>");
        soap.append("</soap:Envelope>");
        String jsons = soap.toString();
        //接受返回报文
        String result = new String();
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
        return XMLParseUtil.responseWeb(result);
    }



}