package com.jeeplus.modules.util;

import com.jeeplus.common.utils.StringUtils;
import net.sf.json.JSONObject;

import java.io.*;

/**
 * @Auther: Jin
 * @Date: 2021/8/24
 * @Description: 缓存（放于临时文件中的）
 */
public class FileCacheUttil {

    public final static void writeJson(String fileName,String jsonString){
        try {
            setJson(fileName,jsonString);
        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException("写入失败!");
        }
    }

    public final static JSONObject readJson(String filename){
        JSONObject json = null;
        try {
            String jsonString = getJson(filename);
            if(StringUtils.isEmpty(jsonString)){
                return json;
            }
            json = JSONObject.fromObject(jsonString);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("读取失败");
        }
        return json;
    }

    private static void setJson(String fileName,String json) throws IOException {
        String fileurl=System.getProperty("user.dir")+ File.separator+ fileName+".json";
        File file = new File(fileurl);
        Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        write.write(json);
        write.flush();
        write.close();
    }

    private static String getJson(String fileName)throws IOException{
        String jsonStr = null;
        String fileurl=System.getProperty("user.dir")+ File.separator+ fileName+".json";
        File file = new File(fileurl);
        if(!file.exists()){
            return null;
        }
        Reader reader = new InputStreamReader(new FileInputStream(fileurl), "utf-8");
        int ch = 0;
        StringBuffer sb = new StringBuffer();
        while ((ch = reader.read()) != -1) {
            sb.append((char) ch);
        }
        reader.close();
        jsonStr = sb.toString();
        return jsonStr;
    }
}
