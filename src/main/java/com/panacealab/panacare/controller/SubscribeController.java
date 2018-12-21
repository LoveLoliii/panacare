package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.SubscribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    /***
     * uid（user_uniq_id）
     *通过uid获取一个用户的订阅信息
     * @param
     * @return 返回订阅信息
     *token 和用户uid 要分开
     * */
   @RequestMapping(path = "getSubscribeInfoByUid",method = RequestMethod.POST)
    private Map getSubscribeInfoByUid(@RequestParam(name = "token",required = false)String token ,@RequestParam String user_uniq_id){

       Map map = new HashMap();
       map.put("state", "000");
       map.put("data", new ArrayList<>());
       String code = "000";//= verifyToken(token); // token 验证
       if(!"000".equals(code)){
           map.put("state",code);
           return map;
       }
       //到这里的意思就是map值重新设定噜。
       map = subscribeService.getSubscribeInfoByUid(user_uniq_id);
       return map;
   }

}
