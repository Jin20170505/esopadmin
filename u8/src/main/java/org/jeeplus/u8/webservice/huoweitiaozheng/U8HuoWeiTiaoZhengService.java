package org.jeeplus.u8.webservice.huoweitiaozheng;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.jeeplus.u8.webservice.U8Url;
import org.jeeplus.u8.webservice.entity.U8WebServiceResult;
import org.jeeplus.u8.webservice.huoweitiaozheng.entity.U8WebHuoWeiTiaoZhengBean;
import org.jeeplus.u8.webservice.huoweitiaozheng.entity.U8WebHuoWeiTiaoZhengMxBean;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.jeeplus.u8.webservice.util.XMLParseUtil.responseWeb;

public final class U8HuoWeiTiaoZhengService {
    /**
     * 货位调整
     * @param huoWeiTiaoZhengBean
     * @return
     * @throws Exception
     */
    public static U8WebServiceResult huoweichange(U8WebHuoWeiTiaoZhengBean huoWeiTiaoZhengBean) throws Exception {
        Map<String,Object> param = new LinkedHashMap<>();
        param.put("cVouchCode",huoWeiTiaoZhengBean.getcVouchCode());
        param.put("cWhCode",huoWeiTiaoZhengBean.getcWhCode());
        param.put("dDate",huoWeiTiaoZhengBean.getdDate());
        param.put("cMaker",huoWeiTiaoZhengBean.getcMaker());

        List<Map<String, Object>> details = Lists.newArrayList();
        for (U8WebHuoWeiTiaoZhengMxBean bean:huoWeiTiaoZhengBean.getDetails())
        {
            Map<String, Object> rd = new LinkedHashMap<String, Object>();
            rd.put("irowno",bean.getIrowno());
            rd.put("cinvcode",bean.getCinvcode());
            rd.put("iQuantity",bean.getiQuantity());
            rd.put("cBatch",bean.getcBatch());
            rd.put("cBPosCode",bean.getcAPosCode());
            rd.put("cAPosCode",bean.getcBPosCode());
            details.add(rd);
        }
        param.put("Detail",details);
        JSONObject ss = JSONObject.fromObject(param);
        StringBuilder soap = new StringBuilder();
        soap.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        soap.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        soap.append("<soap:Body>");
        soap.append("<YTAdjustPVouch xmlns=\"http://tempuri.org/\">");
        soap.append("<jsonstring>"+ss.toString()+"</jsonstring>");
        soap.append("</YTAdjustPVouch>");
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
