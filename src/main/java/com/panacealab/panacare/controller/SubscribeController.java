package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.SubscribeInfo;
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
     * @param token
     * @param user_uniq_id
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

    /***
     * uid（user_uniq_id）
     *通过uid获取所有用户的订阅信息
     * @param
     * @return 返回订阅信息
     *
     * */
   @RequestMapping(path = "getSubscribeInfoAll",method = RequestMethod.POST)
    private Map getSubscribeInfoAll(){
       //web 端 登陆用token or session 还是把token放在session中
       Map map = new HashMap();
       map.put("state", "000");
       map.put("data", new ArrayList<>());
       String code = "000";//= verifyToken(token); // token 验证
       if(!"000".equals(code)){
           map.put("state",code);
           return map;
       }
       //到这里的意思就是map值重新设定噜。
       map = subscribeService.getSubscribeInfoAll();
       return map;
   }


   @RequestMapping(path = "addSubscribeInfo",method = RequestMethod.POST)
    private String addSubscribeInfo(@RequestParam(name = "token",required = false)String  token, @RequestParam SubscribeInfo subscribeInfo){
    String state = "000" ;

      //= verifyToken(token); // token 验证
       if(!"000".equals(state)){

           return state;
       }
       //到这里的意思就是map值重新设定噜。
       state = subscribeService.addSubscribeInfo(subscribeInfo);
       return state;
   }

   /***
    * 用户可以更改什么  订阅的商品 订阅的数量 订阅状态
    * */
   @RequestMapping(path = "modifySubscribeInfo",method = RequestMethod.POST)
    private String modifySubscribeInfo(@RequestParam(name = "token",required = false)String token ,@RequestParam SubscribeInfo subscribeInfo){

       return "000";
   }


}
