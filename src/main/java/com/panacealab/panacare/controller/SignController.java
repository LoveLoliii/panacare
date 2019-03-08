package com.panacealab.panacare.controller;

import com.google.gson.Gson;
import com.panacealab.panacare.entity.UserInfo;
import com.panacealab.panacare.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Loveloliii
 */
@RestController
public class SignController {
    @Autowired
    private SignService signService;

    @RequestMapping(path = "getVerificationCode",method = RequestMethod.POST)
    private Map getVerificationCode(@RequestBody Map map){
        // 生成验证码 发送邮件
       Map  rs = signService.getVerificationCode((String) map.get("mail"));
        return rs;
    }

    @RequestMapping(path="sign",method = RequestMethod.POST)
    private Map appSign(@RequestBody Map map){
       Gson g = new Gson();
        UserInfo u = g.fromJson(String.valueOf(map.get("user_info")),UserInfo.class);
        String mail = (String) map.get("mail");
        String code = (String) map.get("code");
        String state = signService.sign(u,mail,code);
        Map rs = new HashMap(2);
        rs.put("state",state);
        return  rs;
    }

}
