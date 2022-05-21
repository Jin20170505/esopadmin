// U8 WebService 接口
package org.jeeplus.u8.webservice;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class U8Post {

    public static String Rd10Post(YT_Rd10 rd10,String posturl) throws Exception
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
        String url2 = posturl;
        //接受返回报文
        String result = new String();
        URL u = new URL(url2);
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
        String getjson =  responseWeb(result,"YTRdrecord10Result");
        return getjson;
    }

    public static String responseWeb(String response,String respon)
    {
        //创建Reader对象
        SAXReader reader = new SAXReader();
        //加载xml
        Document document = null;
        Map<String, String> request = new HashMap();
        try {
            //获取数据流
            InputStream is = new ByteArrayInputStream(response.getBytes());
            document = reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根节点
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        String ss = "1";
        List<String> s = new ArrayList<>();
        while (iterator.hasNext())
        {
            request = new HashMap();
            Element stu = (Element) iterator.next();
            //判断节点是否为Result 如果不是则向下遍历
            while (!stu.getName().equals(respon))
            {
                if (stu.elementIterator().hasNext()) {
                    stu = (Element) stu.elementIterator().next();
                }else {
                    ss = "";
                    break;
                }
             }
            if(ss != "")
            {
                String rs = stu.getText();
                return rs;
            }
        }
        return "";
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String s = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"><soap:Body><YTRdrecord10Response xmlns=\"http://tempuri.org/\"><YTRdrecord10Result>{\"count\":\"0\",\"message\":\"产品入库单同步成功\",\"id\":\"CPRK20220520222221\"}</YTRdrecord10Result></YTRdrecord10Response></soap:Body></soap:Envelope>";
    }

    public static String Rd11Post(YT_Rd11 rd11, String posturl) throws Exception
    {
        Map<String,Object> param = new LinkedHashMap<String, Object>();
        param.put("cBusType",rd11.getcBusType());
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
        String url2 = posturl;
        //接受返回报文
        String result = new String();
        URL u = new URL(url2);
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
        String getjson =  responseWeb(result,"YTRdrecord11Result");
        return getjson;
    }

    public static String TranPost(YT_Tran tr,String posturl) throws Exception
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
        String url2 = posturl;
        //接受返回报文
        String result = new String();
        URL u = new URL(url2);
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
        String getjson =  responseWeb(result,"YTTransVouchResult");
        return getjson;
    }



}