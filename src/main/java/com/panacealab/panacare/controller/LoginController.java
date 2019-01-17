package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.LoginService;


import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;


@RestController
public class LoginController {
private final static Logger logger= LoggerFactory.getLogger("LoginController") ;
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

    /***
     * 使用token完成自动登陆
     * @author loveloliii
     *
     *
     * */
    @RequestMapping(path = "loginWithToken",method = RequestMethod.POST)
    private Map loginWithToken(@RequestBody Map map){
        Map resultMap = new HashMap();
        String token = (String) map.get("token");
        logger.info("token:"+token);
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)) {
            resultMap.put("state", rs);
            //resultMap.put("data","");
            return resultMap;
        }

        resultMap.put("state",StateCode.Token_Verify_Success);
        return resultMap;
    }

}
