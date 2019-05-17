package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.MailInfo;
import com.panacealab.panacare.service.EmailSubService;
import com.panacealab.panacare.utils.MailSendUtil;
import com.panacealab.panacare.utils.MailTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    /**
     * 因为向所有人发送邮件具有敏感性 所以使用一次性token的方式作为验证方案
     * */
    @CrossOrigin
    @RequestMapping("/email/send")
    private String sendEmail(@RequestBody Map map) {
        String rs = "success";
        String email = (String) map.get("email");
        //todo 对mail 进行判断 如何发送给所有人 以及如何判断与保证每份邮件都成功发出去了 失败应该如何处理
        //使用;或者；分割多个邮件 前端js检查提醒用户 后端检查保证安全
        String[] eList = email.toLowerCase().split("[;；]");
        String content= (String) map.get("content");
        String subject = (String) map.get("subject");
        // pto 规定下样式 后期可以动态配置（2019/05/17）
        for(String s:eList){
            //todo check email
            // s = check(s)
            MailSendUtil.sendMail(content,s,subject);
        }

        return rs;
    }
}
