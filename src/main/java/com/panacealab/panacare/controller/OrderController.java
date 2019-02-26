package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.service.OrderService;
import com.panacealab.panacare.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class OrderController {
    private static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    /***
     *
     * 只生成本后台的订单号 在进入支付页面是生成
     * @param goods_uniq_id
     * @param counts
     */
    @RequestMapping("createOrder")
    private Map createOrder(@RequestParam(name = "token",required = false) String token,@RequestParam String goods_uniq_id,@RequestParam String counts){
        Map map = new HashMap(16);
        //验证token
        String  code = TokenUtil.getTokenValues(token);
        map.put("state",code);
        if (!StateCode.INITIAL_CODE.equals(code))
        {return map;}
        //检查库存由前端来做
        //生成订单
        //生成订单号 = 时间 + 随机数
        String order_number ;
        //从token中取？
        String user_uniq_id ;
        user_uniq_id = TokenUtil.getTokenValues(token);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // /1000 从毫秒级改为秒级
        String time = sdf.format(System.currentTimeMillis());
        long random = (long) (Math.random()*100000);
        order_number = String.valueOf(Long.valueOf(time)*100000+random);
        //订单状态 默认值
        String order_state = StateCode.ORDER_PENDING_PAYMENT;
        String order_created_time = time;
        //保存到数据库
        map = orderService.saveOrder(goods_uniq_id,counts,order_number,order_state,order_created_time);
        //返回本后台的订单号
    return  map;
    }

    /***
     * 用户选择微信支付时，调用此api
     *  请求生成支付订单（） 调用统一下单api
     * @param token token
     * @param order_number 订单编号
     *
     * */
    @RequestMapping("createWXPayOrder")
    private Map createWXPayOrder(@RequestParam(name = "token",required = false) String token,@RequestParam String order_number){
        Map map = new HashMap(16);
        String code = new TokenUtil().checkTokenWithRedis(token);
        map.put("state",code);
        if (!StateCode.INITIAL_CODE.equals(code))
        { return map;}
        String user_uniq_id = TokenUtil.getTokenValues(token);
        map = orderService.createWXPayOrder(user_uniq_id,order_number);
        return map;
    }


    /***
     *用于接受微信服务器返回的异步消息
     * */

    @RequestMapping(name = "wxPayNotify",method = RequestMethod.POST)
    private void wxPayNotify(@RequestParam HttpServletRequest request, @RequestParam HttpServletResponse response){
        try {
            String result = PayCommonUtil.reciverWx(request);
            //解析xml为map
            Map<String,String> m = new HashMap<>(16);
            if (m != null && !"".equals(m))
            {
                m = XMLUtil.doWXXMLParse(result);
            }
            // 过滤空 设置 TreeMap
            SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
            Iterator it = m.keySet().iterator();
            while (it.hasNext())
            {
                String parameter = (String) it.next();
                String parameterValue = m.get(parameter);
                String v = "";
                if (null != parameterValue)
                {
                    v = parameterValue.trim();
                }
                packageParams.put(parameter, v);
            }
            // 判断签名是否正确
            String resXml = "";
            if (PayCommonUtil.isTenpaySign("UTF-8", packageParams))
            {
                if ("SUCCESS".equals((String) packageParams.get("return_code")))
                {
                    // 如果返回成功
                    // 商户号
                   String mch_id = (String) packageParams.get("mch_id");
                    // 商户订单号
                   String out_trade_no = (String) packageParams.get("out_trade_no");
                   String total_fee = (String) packageParams.get("total_fee");
                    // String transaction_id = (String) packageParams.get("transaction_id"); // 微信支付订单号
                    OrderInfo orderInfo = orderService.queryByUID(out_trade_no);
                    GoodsInfo goodsInfo = orderService.queryGoodsInfoByUID(orderInfo.getGoods_uniq_id());
                    // 验证商户ID 和 价格 以防止篡改金额
                    if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id)
                    // 实际项目中将此注释删掉，以保证支付金额相等
                    && total_fee.trim().toString().equals(String.valueOf(100*orderInfo.getOrder_counts()*Integer.parseInt(goodsInfo.getGoods_onsale_buy_uprice())))
                    ){

                        // 变更支付方式为wx
                        orderInfo.setOrder_pay_way(StateCode.PAY_WAY_WX);
                        // 订单状态为已付款
                        orderInfo.setOrder_state(StateCode.ORDER_PURCHASED);
                        orderInfo.setOrder_finished_time(String.valueOf(System.currentTimeMillis()));
                         // 变更数据库中该订单状态
                        orderService.changeOrderState(orderInfo);

                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else
                    {
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                                + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                    }
                } else // 如果微信返回支付失败，将错误信息返回给微信
                {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
                }
            } else
            {
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
            }
            // 处理业务完毕，将业务结果通知给微信
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
