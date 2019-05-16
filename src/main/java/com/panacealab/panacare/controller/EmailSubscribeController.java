package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.MailInfo;
import com.panacealab.panacare.service.EmailSubService;
import com.panacealab.panacare.utils.MailSendUtil;
import com.panacealab.panacare.utils.MailTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * email related
 *
 * @author loveloliii
 * @date 2019/5/13.
 */
@RestController
public class EmailSubscribeController {
    @Autowired
    private EmailSubService emailSubService;

    @RequestMapping("/email/subscribe/{email}")
    private String subscribeByEmail(@PathVariable(value = "email") String email) {
        // check repeat
        emailSubService.saveSubscribeEmail(email);
        return "success";
    }

    @RequestMapping("/email/send")
    private String sendEmail(@RequestBody Map map) {
        String rs = "success";
        String email = (String) map.get("mail");
        String subject = (String) map.get("subject");
        // 载入必要配置信息
        Properties p = new Properties();
        try {

            p.load(MailTool.class.getResourceAsStream("/configure.properties"));
            String formName = p.getProperty("panacare.mail");
            String password = p.getProperty("panacare.key");
            String host = p.getProperty("panacare.smtp", "smtp-mail.outlook.com");

            String defaultHeader = "<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n</head>\n" +
                    "<body>\n"   ;
            String end =  "</body>\n</html>";
            String content= "";
            MailInfo mailInfo = MailInfo
                    .builder()
                    .content(defaultHeader+content+end)
                    .formName(formName)
                    .formPassword(password)
                    .host(host)
                    .replayAddress(email)
                    .subject(subject)
                    .toAddress(email)
                    .build();
            MailSendUtil.sendHtmlMail(mailInfo);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            rs = "fail";
        }
        return rs;
    }
}
