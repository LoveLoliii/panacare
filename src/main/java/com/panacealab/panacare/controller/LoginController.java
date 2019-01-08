package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.LoginService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.logging.Logger;

@RestController
public class LoginController {
private final static Logger logger= Logger.getLogger("LoginController") ;
    @Autowired
    private LoginService loginService;
    /***
     * @author:loveloliii
     * @param   map account 邮箱 或者是 手机号  pwd 密码
     * @return  rsmap 登陆结果与token
     * */
@RequestMapping(path = "login",method = RequestMethod.POST)
    private Map appLogin(@RequestBody Map map){
        //System.out.println(mail+"->"+pwd);
    String account = (String) map.get("mail");
    String pwd = (String) map.get("pwd");

    logger.info("account:"+account);
       //验证
       return loginService.check(account,pwd);

    }



}
