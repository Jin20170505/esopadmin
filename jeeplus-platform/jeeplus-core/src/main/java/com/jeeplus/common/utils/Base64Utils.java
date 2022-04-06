/**
 * 作者：syp
 * 时间：2019/6/29 17:12
 * 描述：
 **/

package com.jeeplus.common.utils;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.List;

public class Base64Utils {
    /**
     * pdf合并
     * @param inputs
     * @param filepth
     */
    public static void hbpdf(List<InputStream> inputs, String filepth) {
        try {
            PDFMergerUtility PDFmerger = new PDFMergerUtility();
            PDFmerger.addSources(inputs);
            PDFmerger.setDestinationFileName(filepth);
            PDFmerger.mergeDocuments();
        } catch (Exception e) {

        }
    }
    // 加密
    @SuppressWarnings("restriction")
    public static String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    // 解密
    @SuppressWarnings("restriction")
    public static String getFromBase64(String s) {
        byte[] b = null;
        String result = null;
        if (s != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] addBytes(byte[] data1, byte[] data2) {
        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }
    /**
     * byte转InputStream
     * @param buf
     * @return
     */
    public static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }


    /**
     * file转byte[]
     * @param filePath
     * @return
     */
    public static byte[] File2byte(String filePath)
    {
        byte[] buffer = null;
        try
        {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return buffer;
    }

    public static void main(String[] args) {
        //String sa = "U0MwMDAwMDnnrKwx6L2m";
        //System.out.println(getFromBase64(sa));
        //hbpdf();

        String str = "的健康角度考虑";
        System.out.println(getBase64(str));
        String str2 = "55qE5YGl5bq36KeS5bqm6ICD6JmR";
        System.out.println(getFromBase64(str2));
    }
}
