package com.panacealab.panacare.controller;

import com.google.gson.Gson;
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
    private String appSign(@RequestBody Map map){
       Gson g = new Gson();
        // 封装用户信息
        UserInfo u = g.fromJson(String.valueOf(map.get("user_info")),UserInfo.class);
        String mail = (String) map.get("mail");
        String code = (String) map.get("code");

        String rs = signService.sign(u,mail,code);

        return  g.toJson(rs);
    }

}
