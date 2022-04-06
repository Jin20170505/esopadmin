package com.jeeplus.common.utils;

/**
 * 金额数字工具
 * @Auther: Jin
 * @Date: 2020/10/22
 * @Description:
 */
public final class MoneyNumUtil {
    private static final char[] data = new char[] { '零', '壹', '贰', '叁', '肆',
            '伍', '陆', '柒', '捌', '玖' };

    private static final char[] units = new char[] { '元', '拾', '佰', '仟', '万',
            '拾', '佰', '仟', '亿' };

    public static String convertToChinese(String moneyStr) {
        StringBuffer sbf = new StringBuffer();
        int unit = 0;
        String[] ms = moneyStr.split("\\.");
        int money = Integer.parseInt(ms[0]);
        while (money != 0) {
            sbf.insert(0, units[unit++]);
            int number = money % 10;
            sbf.insert(0, data[number]);
            money /= 10;
        }
        if(ms.length>1){
            String xs = ms[1];
            if(xs.length()>1 && !"0".equals(xs.substring(1,2))){
            	if(!"0".equals(xs.substring(0,1))){
                sbf.append(data[Integer.valueOf(xs.substring(0,1))]).append("角").append(data[Integer.valueOf(xs.substring(1,2))]).append("分");
            	}else {
            		sbf.append("零").append(data[Integer.valueOf(xs.substring(1,2))]).append("分");
            	}
            }else{
            	if(!"0".equals(xs.substring(0,1))){
                sbf.append(data[Integer.valueOf(xs.substring(0,1))]).append("角").append("整");
            	}
            }
        }else {
        	sbf.append("整");
        }
        
        return sbf.toString();
    }

    public static void main(String[] args) {
        System.out.println(convertToChinese("123."));
    }
}
