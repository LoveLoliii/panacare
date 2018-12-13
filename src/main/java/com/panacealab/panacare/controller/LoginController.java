package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.LoginService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;
import java.util.logging.Logger;

@RestController
public class LoginController {
private final static Logger logger= Logger.getLogger("LoginController") ;
    @Autowired
    private LoginService loginService;
    /***
     * @author:loveloliii
     * @param   account 邮箱 或者是 手机号
     * @param   pwd 密码
     * @return  rsmap 登陆结果与token
     * */
@RequestMapping(path = "login",method = RequestMethod.POST)
    private Map appLogin(@RequestParam String account,@RequestParam String pwd){
        //System.out.println(mail+"->"+pwd);
    logger.info("account:"+account);
       //验证
       return loginService.check(account,pwd);

    }



}
