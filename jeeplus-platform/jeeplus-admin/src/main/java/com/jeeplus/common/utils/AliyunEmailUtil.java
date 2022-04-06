package com.jeeplus.common.utils;

import com.google.common.collect.Lists;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 阿里云邮件推送 SMTP email
 * @Auther: Jin
 * @Date: 2020/9/24
 * @Description:
 */
public final class AliyunEmailUtil {
    public static String myEmailAccount = "service@rht.fingard.com.cn";
    public static String myEmailPassword = "BinTest4517";
    public static String myEmailSMTPHost = "smtpdm.aliyun.com";
    private static final String ALIDM_SMTP_PORT = "25";
    public final static  void SendEmail(String res, String cps, String iscopy, String htm, File file, String Title) throws MessagingException, IOException {
        //  创建参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        // props.setProperty("mail.smtp.port", "25");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.port", "465");

        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        props.setProperty("mail.user", myEmailAccount);
        props.setProperty("mail.password", myEmailPassword);
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        Session session = Session.getInstance(props,authenticator);
        session.setDebug(true);
       // 3.1 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 3.2 From: 发件人
        message.setFrom(new InternetAddress(myEmailAccount, "浙江保融科技有限公司", "UTF-8"));
        // 3.3 To: 收件人
        List<InternetAddress>  revices = Lists.newArrayList();
        Arrays.asList(res.split(";")).forEach(r-> {
            try {
                revices.add(new InternetAddress(r));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
        message.setRecipients(MimeMessage.RecipientType.TO,revices.toArray(new InternetAddress[revices.size()]));
        // 3.3 To: 抄送人
        if("1".equals(iscopy)&&cps!=null&&cps.length()>0){
            List<InternetAddress>  copys = Lists.newArrayList();
            Arrays.asList(cps.split(";")).forEach(r-> {
                try {
                    copys.add(new InternetAddress(r));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            message.setRecipients(MimeMessage.RecipientType.CC, copys.toArray(new InternetAddress[copys.size()]));
        }
        message.setSubject(MimeUtility.encodeText(Title), "UTF-8");
        Multipart multipart = new MimeMultipart();
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
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public static void main(String[] args) throws IOException, MessagingException {
        SendEmail("luyj2021@outlook.com",null,"","<b>测试</>",new File("D:\\20201209.pdf"),"测试");
    }
}
