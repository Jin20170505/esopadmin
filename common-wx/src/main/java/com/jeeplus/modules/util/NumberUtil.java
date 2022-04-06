package com.jeeplus.modules.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Jin
 * @Date: 2021/9/12
 * @Description:
 */
public final class NumberUtil {

    public static final String getNumber(String str){
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        Matcher m = p.matcher(str);
        if(m.find()){
            str = m.group(1)==null ?"":m.group(1);
        }else {
            p=Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if(m.find()){
                str = m.group(1)==null ?"":m.group(1);
            }else {
                str = "";
            }
        }
        return str;
    }

    public static void main(String[] args) {
        System.out.println(getNumber("2h"));
    }
}
