package com.panacealab.panacare.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.panacealab.panacare.service.LoginService;


import com.panacealab.panacare.utils.PropertyUtil;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.asynchttpclient.Dsl.asyncHttpClient;


@RestController
public class LoginController {
private final static Logger logger= LoggerFactory.getLogger("LoginController") ;
    @Autowired
    private LoginService loginService;
    /***
     * @author loveloliii
     * @param   map account 邮箱 或者是 手机号  pwd 密码
     * @return  rsmap 登陆结果与token
     * */
@RequestMapping(path = "login",method = RequestMethod.POST)
    private Map appLogin(@RequestBody Map map){
    String account = (String) map.get("mail");
    String pwd = (String) map.get("pwd");

    logger.info("account:"+account);
       //验证
       return loginService.check(account,pwd);

    }

    /**
     * 微信小程序 通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口 用于获取openID 与session_key?
     * 应该是低频率的使用
     * @param code 临时验证码
     * @return
     */
    @RequestMapping(path="/weappLogin",method = RequestMethod.GET)
    private String  weappLogin(@RequestParam String code){
        AsyncHttpClient asyncHttpClient = asyncHttpClient();
        String appid = PropertyUtil.get("","","wxa011cc0e920d6d8b");
        String secret = PropertyUtil.get("","","192fd71d9dcf0d78140c353639ab796b");
        AtomicReference<String> rs = new AtomicReference<>("");
        System.out.println(appid+""+secret+""+code);
         asyncHttpClient
                .prepareGet("https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+code+"&grant_type=authorization_code")
                .execute()
                .toCompletableFuture()
                 //.exceptionally(t->{/* something wrong happened*/  return "";})
                .thenApply(Response::getResponseBody)
                .thenAccept(s -> {
                    rs.set(s);
                   logger.info(s);
                })
                 .join();
                // String 转 Map
        Gson g = new Gson();
        HashMap hashMap = (HashMap) g.fromJson(rs.get(),new TypeToken<HashMap<String,String>>(){}.getRawType());
        String sessionKey = (String) hashMap.get("session_key");
        String openid = (String) hashMap.get("openid");
        logger.info(sessionKey+";"+openid);
        if(openid != null){
            //    对比第三方表是否有此ID
            boolean exist = loginService.isExist(openid);
            if(exist){
                // todo（通过第三方表获取uuid，从主表获取信息。），创建登陆态
                //fixme 返回的token 需要考虑邮箱的问题 loginService.check().

            }else {
                // 建立信息 分别为第三方登陆表和主用户表建立用户信息

            }
        }else{
            // 无法获取到openID 处理错误
            return "error";
        }
    return  rs.get();
    }

    @CrossOrigin
    @RequestMapping(path = "/admin/login",method = RequestMethod.POST)
    private Map adminLogin(@RequestBody Map map, HttpServletResponse response){
        String account = (String) map.get("mail");
        String pwd = (String) map.get("pwd");

        logger.info("account:"+account);
        //验证
        return loginService.adminCheck(account,pwd);

    }

    /***
     * 使用token完成自动登陆
     * @author loveloliii
     *
     *
     * */
    @RequestMapping(path = "loginWithToken",method = RequestMethod.POST)
    private Map loginWithToken(@RequestBody Map map){
        Map resultMap = new HashMap(16);
        String token = (String) map.get("token");
        logger.info("token:"+token);
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }

        resultMap.put("state",StateCode.TOKEN_VERIFY_SUCCESS);
        return resultMap;
    }

}
