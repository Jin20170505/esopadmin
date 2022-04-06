package com.jeeplus.common.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * 发送email
 * @Auther: Jin
 * @Date: 2020/9/24
 * @Description:
 */
public final class EmailUtil {
    // 发件人的 邮箱 和 密码
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "2553047257@qq.com";
    public static String myEmailPassword = "sopkkxonbgdvdjei";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    public static String myEmailSMTPHost = "smtp.qq.com";

    public final static  void SendEmail(String res, String cps, String iscopy, String htm, File file, String Title) throws MessagingException, IOException {
// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);
        // 3. 创建一封邮件
        // 3.1 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 3.2 From: 发件人
        message.setFrom(new InternetAddress(myEmailAccount, "浙江保融科技有限公司", "UTF-8"));
        // 3.3 To: 收件人
        InternetAddress[] internetAddressTo = new InternetAddress().parse(res);
        message.setRecipients(MimeMessage.RecipientType.TO,internetAddressTo);
        message.setRecipients(MimeMessage.RecipientType.CC,internetAddressTo);
        // 4. Subject: 邮件主题
        message.setSubject(MimeUtility.encodeText(Title), "UTF-8");
        Multipart multipart = new MimeMultipart();
        // 5.1 正文
        BodyPart contentPart = new MimeBodyPart();
        contentPart.setContent(htm, "text/html;charset=UTF-8");
        multipart.addBodyPart(contentPart);
        if(file!=null){
            BodyPart filePart = new MimeBodyPart();
            multipart.addBodyPart(filePart);
            ((MimeBodyPart) filePart).attachFile(file);
        }

        message.setContent(multipart);
        message.setSentDate(new Date());

        message.saveChanges();

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    public static void main(String[] args) throws IOException, MessagingException {
        SendEmail("luyj2021@outlook.com",null,"","<b>测试</>",new File("D:\\20201209.pdf"),"测试");
    }

}
