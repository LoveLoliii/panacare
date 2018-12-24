package com.panacealab.panacare.controller;

import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Random;

@RestController
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    /***
     *
     * 只生成本后台的订单号 在进入支付页面是生成
     * @param goods_uniq_id
     * @param counts
     */
    @RequestMapping("createOrder")
    private Map createOrder(@RequestParam(name = "token",required = false) String token,@RequestParam String goods_uniq_id,@RequestParam String counts){
        //验证token
        //检查库存由前端来做
        //生成订单
        //生成订单号 = 时间 + 随机数
        String order_number ;
        String user_uniq_id;//从token中取？
        user_uniq_id = TokenUtil.getTokenValues(token);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(System.currentTimeMillis());// /1000 从毫秒级改为秒级
        long random = (long) (Math.random()*100000);
        order_number = String.valueOf(Long.valueOf(time)*100000+random);
        //订单状态 默认值
        String order_state = StateCode.Order_Pending_Payment;

        String order_created_time = time;


        //保存到数据库

        //返回本后台的订单号


    return  null;
    }

    /***
     * 用户选择微信支付时，调用此api
     *  请求生成支付订单（） 调用统一下单api
     * @param
     *
     * */
    @RequestMapping("createWXPayOrder")
    private Map createWXPayOrder(@RequestParam(name = "token",required = false) String token,@RequestParam String order_number){
        //TODO 验证token
        //TODO 检查订单状态
        //TODO 调用统一下单api()
        //TODO 返回生成带签名的客户端的支付信息


        return null;
    }


    /***
     *
     * */

}
