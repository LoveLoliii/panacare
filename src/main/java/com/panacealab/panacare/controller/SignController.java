package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.service.SignService;
import com.panacealab.panacare.utils.MailSendUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class SignController {
    @Autowired
    private SignService signService;
    /*@RequestMapping(path = "sign",method = RequestMethod.POST)
    private Map appSign(){


        return null;
    }*/

    @RequestMapping(path = "getVerificationCode",method = RequestMethod.POST)
    private String getVerificationCode(@RequestBody Map map){
        // 生成验证码 发送邮件
       String  rs = signService.getVerificationCode((String) map.get("mail"));

        return rs;
    }

    @RequestMapping(path="sign",method = RequestMethod.POST)
    private String appSign(@RequestBody UserInfo userInfo, @RequestParam String mail, @RequestParam String code){
        // 封装用户信息
        UserInfo u = userInfo;

        String rs = signService.sign(u,mail,code);

        return rs;
    }

}
