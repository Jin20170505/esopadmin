package com.jeeplus.modules.word;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Jin
 * @Date: 2021/7/6
 * @Description: 以word为模板 生成word文件
 */
public class WordTemplateService {
    public static void main(String[] args) {
        Map map=new HashMap();
        map.put("jcz","月报");
        map.put("cwglgd","2018-5-28");
        map.put("bjcdw","");
        map.put("dwfzr","");
        map.put("y","2021");
        map.put("m","07");
        map.put("d","06");
        getBuild("static/template/被稽查单位承诺书（模板）.doc",map,"D:/aaa.doc");
    }

    public static void getBuild(String  tmpFile, Map<String, String> contentMap, String exportFile){
        HWPFDocument document = null;
        try {
            document = new HWPFDocument(new FileInputStream(tmpFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取文本内容
        Range bodyRange = document.getRange();
        // 替换内容
        for (Map.Entry<String, String> entry : contentMap.entrySet()) {
            bodyRange.replaceText("${" + entry.getKey() + "}", entry.getValue());
        }
        //导出到文件
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.write(byteArrayOutputStream);
            OutputStream outputStream = new FileOutputStream(exportFile);
            outputStream.write(byteArrayOutputStream.toByteArray());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
