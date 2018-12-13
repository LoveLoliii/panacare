package com.panacealab.panacare.utils;

import com.panacealab.panacare.dao.SignDao;
import com.panacealab.panacare.entity.MailInfo;
import com.panacealab.panacare.entity.MailValidate;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;


public class MailSendUtil {


//      private static SignDao utilsDao = SpringContextHolder.get

      private static MailSendUtil mailSendUtil;
      private static String host="";
      private static String formName="";
      private static String password="";
    //  private static String replayAddress="";
//    public static  void main(String[] args){
//
//            mailSendUtil.sendCode("758549127@qq.com");
////        for(int i=0;i<1000;i++){
////            getRandomCode6();
////        }
//
//
//    }



    public   void sendCode(String replayAddress){




    }

    public static String getRandomCode6() {
        String verificationCode;
			int code =0;
			code = (int) (Math.random()*1000000);
			String add="";
			if(code<10){
				 add = "00000";
			}else if(code<100){
				add="0000";
			}else if(code<1000){
				add="000";
			}else if(code<10000){
			    add="00";
            }else if(code<100000)
                add="0";
			verificationCode = add+String.valueOf(code);
			System.out.println(verificationCode);
			return verificationCode;


    }


    public static void sendHtmlMail(MailInfo mailInfo) {


        Message message = getMessage(mailInfo);
        // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
        Multipart mainPart = new MimeMultipart();
        // 创建一个包含HTML内容的MimeBodyPart
        BodyPart html = new MimeBodyPart();
        // 设置HTML内容

        try {
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            message.setContent(mainPart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        // 将MiniMultipart对象设置为邮件内容

    }

    private static Message getMessage(MailInfo mailInfo) {
        final Properties p = System.getProperties();
        p.setProperty("mail.smtp.host", mailInfo.getHost());
        p.setProperty("mail.smtp.auth", "true");
        p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.socketFactory.fallback", "true");
        p.put("mail.smtp.starttls.enable", "true");
        p.setProperty("mail.smtp.user", mailInfo.getFormName());
        p.setProperty("mail.smtp.pass", mailInfo.getFormPassword());
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session session = Session.getInstance(p, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(p.getProperty("mail.smtp.user"), p.getProperty("mail.smtp.pass"));
            }
        });
        session.setDebug(true);
        Message message = new MimeMessage(session);
        //消息发送的主题
        try {
            message.setSubject(mailInfo.getSubject());

            //接受消息的人
            message.setReplyTo(InternetAddress.parse(mailInfo.getReplayAddress()));
            //消息的发送者
            message.setFrom(new InternetAddress(p.getProperty("mail.smtp.user"), "panacea-lab"));
            // 创建邮件的接收者地址，并设置到邮件消息中
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailInfo.getToAddress()));
            // 消息发送的时间
            message.setSentDate(new Date());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
