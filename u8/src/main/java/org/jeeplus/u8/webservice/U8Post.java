// U8 WebService 接口
package org.jeeplus.u8.webservice;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class U8Post {

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
        U8WebServiceResult rs = new U8WebServiceResult();
        int i = result.indexOf("<YTTransVouchResult>") + "<YTTransVouchResult>".length();
        int j = result.indexOf("</YTTransVouchResult>");
        result = result.substring(i,j);
        JSONObject json = JSONObject.fromObject(result);
        rs.setCount(json.getString("count"));
        rs.setMessage(json.getString("message"));
        return rs;
    }

    public static U8WebServiceResult Rd11Post(YT_Rd11 rd11, String posturl) throws Exception
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
        U8WebServiceResult rs = new U8WebServiceResult();
        int i = result.indexOf("<YTTransVouchResult>") + "<YTTransVouchResult>".length();
        int j = result.indexOf("</YTTransVouchResult>");
        result = result.substring(i,j);
        JSONObject json = JSONObject.fromObject(result);
        rs.setCount(json.getString("count"));
        rs.setMessage(json.getString("message"));
        return rs;
    }

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
        U8WebServiceResult rs = new U8WebServiceResult();
        int i = result.indexOf("<YTTransVouchResult>") + "<YTTransVouchResult>".length();
        int j = result.indexOf("</YTTransVouchResult>");
        result = result.substring(i,j);
        JSONObject json = JSONObject.fromObject(result);
        rs.setCount(json.getString("count"));
        rs.setMessage(json.getString("message"));
        return rs;
    }

    public static void main(String[] args) {

        //调拨单调用
        /*YT_Tran tran = new YT_Tran();
        tran.setcTVCode("cs001");
        tran.setdTVDate("2022-05-19");
        tran.setcOWhCode("01");
        tran.setcIWhCode("02");
        tran.setcIRdCode("15");
        tran.setcORdCode("25");
        tran.setcTVMemo("测试");

        List<YT_Trans> trans = Lists.newArrayList();
        YT_Trans tr = new YT_Trans();
        tr.setCbMemo("测试");
        tr.setcInvCode("C690000019");
        tr.setiTVQuantity("1");
        tr.setcTVBatch("220520010");
        tr.setCoutposcode("A-04-01-01");
        tr.setIrowno("1");
        tr.setdMadeDate("2022-05-22");
        trans.add(tr);
        tran.setTrans(trans);
        try {
            TranPost(tran,"http://localhost:57820/U8ToService.asmx");
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //材料出库单调用
        YT_Rd11 rd11 = new YT_Rd11();
        rd11.setcBusType("领料");
        rd11.setcSource("生产订单");
        rd11.setcWhCode("01");
        rd11.setdDate("2022-05-20");
        rd11.setcCode("cs004");
        rd11.setcRdcode("21");
        rd11.setcDepCode("");
        rd11.setcMemo("测试");
        rd11.setcMaker("demo");

        List<YT_Rds11> rds11 = Lists.newArrayList();
        YT_Rds11 rd1 = new YT_Rds11();
        rd1.setcInvCode("D210000003");
        rd1.setiQuantity("1");
        rd1.setCmocode("SC2205200002");
        rd1.setInvcode("C060000085");
        rd1.setImoseq("1");
        rd1.setIopseq("60");
        rds11.add(rd1);
        rd11.setRd11s(rds11);
        try {
            Rd11Post(rd11,"http://localhost:57820/U8ToService.asmx");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //产成品入库单调用

        /*YT_Rd10 rd10 = new YT_Rd10();
        rd10.setcBusType("成品入库");
        rd10.setcSource("生产订单");
        rd10.setcWhCode("08");
        rd10.setdDate("2022-05-20");
        rd10.setcCode("cs001");
        rd10.setcRdcode("13");
        rd10.setcDepCode("09020206");
        rd10.setcMemo("测试");
        rd10.setcMaker("demo");

        List<YT_Rds10> rds10 = Lists.newArrayList();
        YT_Rds10 rd2 = new YT_Rds10();

        rd2.setcInvCode("C380000013");
        rd2.setiQuantity("1");
        rd2.setcPosition("A-04-01-01");
        rd2.setIrowno("1");
        rd2.setCmocode("11");
        rd2.setImoseq("10");
        rd2.setCbMemo("10");
        rd2.setdMadeDate("10");
        rd2.setBatch("1");
        rds10.add(rd2);
        rd10.setRd10s(rds10);
        try {
            Rd10Post(rd10,"http://localhost:57820/U8ToService.asmx");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}