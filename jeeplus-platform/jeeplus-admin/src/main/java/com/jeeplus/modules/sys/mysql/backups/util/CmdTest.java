package com.jeeplus.modules.sys.mysql.backups.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CmdTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        String sysName = System.getProperty("os.name").toLowerCase();
        System.out.println(sysName);
        String cmd = "java -version";
        Process exec = Runtime.getRuntime().exec(cmd);
        if (exec.waitFor() == 0){
            System.out.println("命令结果：" + readCmdResult(exec));
        }
    }

    public static String readCmdResult(Process process){
        BufferedReader reader = null;
        BufferedReader errorreader = null;
        String line = null;
        StringBuilder rs = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            errorreader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorreader.readLine()) != null) {
                rs.append(line.trim()).append("\n");
            }
            line = null;
            while ((line = reader.readLine()) != null) {
                rs.append(line.trim()).append("\n");
            }
            return rs.toString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }
}
