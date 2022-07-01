package com.jeeplus.modules.sys.mysql.backups.util;


import com.jeeplus.common.utils.EmailUtil;

import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLBackProcessUtil {
    public static void main(String[] args) {
        try {
            backupDB("/usr/local/mysql/bin/mysqldump","127.0.0.1","3306","root","root1234","esop","/Users/jin/esoptest.sql");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 数据库备份
     * @param mysqldumppath mysqldump 所在位置
     * @param ip mysql数据库IP
     * @param port mysql 端口
     * @param username mysql用户名
     * @param pasword mysql密码
     * @param database 数据库名
     * @param filepath 备份数据库文件地址
     * @throws IOException
     * @throws InterruptedException
     */
    public static void backupDB(String mysqldumppath,String ip,String port,String username,String pasword,String database,String filepath) throws Exception{
        List<String> commands = new ArrayList<>();
        commands.add(mysqldumppath);
        commands.add("-h");
        commands.add(ip);
        commands.add("-P");
        commands.add(port);
        commands.add("-u"+username);
        commands.add("-p"+pasword);
        commands.add("-B");
        commands.add(database);
        processSh(commands, filepath);
    }

    /**
     * 输出需要生成文件的情况
     * @param commands 命令分段，必须要分割，不能直接拼好一条add进来会报错，commands中的String不需要加空格
     * @param filePath 输出到哪个文件中，全路径
     */
    public static void processSh(List<String> commands, String filePath) throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(commands);
        processBuilder.redirectOutput(new File(filePath));
        Process process = processBuilder.start();
        process.waitFor();
        //执行命令的异常信息
        System.out.println("结果："+readCmdResult(process));
        try {
            EmailUtil.SendEmail("1958207751@qq.com",null,null,"结果："+readCmdResult(process),new File(filePath),"数据库备份");
        } catch (MessagingException e) {
            e.printStackTrace();
            EmailUtil.SendEmail("1958207751@qq.com",null,null,"结果："+e.getLocalizedMessage(),null,"数据库备份");
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



