package com.panacealab.panacare.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.gson.Gson;
import com.panacealab.panacare.entity.*;
import com.panacealab.panacare.service.PayService;
import com.panacealab.panacare.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@RestController
public class PayController {
    Logger logger = LoggerFactory.getLogger(PayController.class);
    @Autowired
    private PayService payService;
    private ExecutorService executorService = PUtils.getExecutorServiceInstance();

    @RequestMapping("getWay")
    private String getAliPayAsynNofity(HttpServletRequest request) {
        logger.debug("接收到支付宝的异步通知");
        // 将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> params = convertRequestParamsToMap(request);
        String paramsJson = JSON.toJSONString(params);
        logger.info("支付宝回调，{}", paramsJson);
        HashMap paramsMap = JSON.parseObject(paramsJson, HashMap.class);
        String orderNumber = (String) paramsMap.get("out_trade_no");
        try {
            // 支付宝配置
         //   AlipayConfig alipayConfig = new AlipayConfig();
            // 调用SDK验证签名
            boolean signVerified = AlipaySignature.rsaCheckV1(params, PropertyUtil.get("/pay.properties","A.aliPublicKey",""),
                    PropertyUtil.get("/pay.properties","A.CHARSET",""), PropertyUtil.get("/pay.properties","A.signType",""));
            if (signVerified) {
                logger.info("支付宝回调签名认证成功");
                // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
                //this.check(params);
                // 另起线程处理业务
                executorService.execute(() -> {
                    AlipayNotifyParam param = buildAlipayNotifyParam(params);
                    String trade_status = param.getTradeStatus();
                    // 支付成功
                    if (trade_status.equals(AlipayTradeStatus.TRADE_SUCCESS.name())
                            || trade_status.equals(AlipayTradeStatus.TRADE_FINISHED.name())) {
                        // 处理支付成功逻辑
                        try {
                            logger.error("支付成功，即将修改订单信息，订单号为:", orderNumber);
                            // 处理业务逻辑
                            payService.updateOrder(orderNumber);
                        } catch (Exception e) {
                            logger.error("支付宝回调业务处理报错,params:" + paramsJson, e);
                        }
                    } else {
                        logger.error("没有处理支付宝回调业务，支付宝交易状态：{},params:{}", trade_status, paramsJson);
                    }
                });
                // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
                // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
                return "success";
            } else {
                logger.info("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
                return "failure";
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝回调签名认证失败,paramsJson:{},errorMsg:{}", paramsJson, e.getMessage());
            return "failure";
        }
    }


    private AlipayNotifyParam buildAlipayNotifyParam(Map<String, String> params) {
        String json = JSON.toJSONString(params);
        return JSON.parseObject(json, AlipayNotifyParam.class);
    }


    private void check(Map<String, String> params) throws AlipayApiException {
        String outTradeNo = params.get("out_trade_no");

        // 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
        //验证订单号
        OrderInfo order = new OrderInfo();
        if (order == null) {
            throw new AlipayApiException("out_trade_no错误");
        }

        // 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
        long total_amount = new BigDecimal(params.get("total_amount")).multiply(new BigDecimal(100)).longValue();
        if (total_amount != order.getOrder_counts().longValue()) {
            throw new AlipayApiException("error total_amount");
        }

        // 3、校验通知中的seller_id（或者seller_email)是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
        // 第三步可根据实际情况省略

        // 4、验证app_id是否为该商户本身。
        if (!params.get("app_id").equals(PropertyUtil.get("/pay.properties","A.appId",""))) {
            throw new AlipayApiException("app_id不一致");
        }
    }

    private Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {
        Map<String, String> retMap = new HashMap<String, String>();

        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            int valLen = values.length;

            if (valLen == 1) {
                retMap.put(name, values[0]);
            } else if (valLen > 1) {
                StringBuilder sb = new StringBuilder();
                for (String val : values) {
                    sb.append(",").append(val);
                }
                retMap.put(name, sb.toString().substring(1));
            } else {
                retMap.put(name, "");
            }
        }

        return retMap;

    }

    /***
     * 向app返回加签后的订单信息
     * @param map 包含原始的商品订单信息（不是指支付宝的订单信息
     * @return map 返回包含状态码和加签后的字符串hashmap容器
     * */
    @RequestMapping(path = "createAliPayOrderOld", method = RequestMethod.POST)
    private String createAliPayOrderOld(@RequestBody Map map) {
        Map resultMap = new HashMap();
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            //resultMap.put("data","");
            return "";
        }


        Gson g = new Gson();
        System.out.println(String.valueOf(map.get("orderInfo")));
        //处理订单信息
        OrderInfo orderInfo = g.fromJson(String.valueOf(map.get("orderInfo")).trim(), OrderInfo.class);


        String privateKey = PropertyUtil.get("/pay.properties","A.appPrivateKey","");
        Map<String, String> params = new HashMap<String, String>();
        boolean rsa2 = (privateKey.length() > 0);
        params = PayCommonUtil.buildOrderParamMap(PropertyUtil.get("/pay.properties","A.appIdDev",""), rsa2, "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + "222222" + "\"}");//TODO 获取订单号getOutTradeNo()
        privateKey = rsa2 ? privateKey : PropertyUtil.get("/pay.properties","A.appPrivateKeyRSA1","");
        String sign = PayCommonUtil.getSign(params, privateKey, rsa2);
        String orderParam = PayCommonUtil.buildOrderParam(params);
        final String aliOrderInfo = orderParam + "&" + sign;
        System.out.println(aliOrderInfo);
        try {

           /* AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。*/
            resultMap.put("data", aliOrderInfo);
            resultMap.put("state", StateCode.DATA_RETURN_SUCCESS);
            return aliOrderInfo;// URLEncoder.encode(aliOrderInfo, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


    @Transactional
    @RequestMapping(path = "createAliPayOrder", method = RequestMethod.POST)
    private Map createAliPayOrder(@RequestBody Map map) {
        Map resultMap = new HashMap(16);
        resultMap.put("state", StateCode.INITIAL_CODE);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.INITIAL_CODE.equals(rs)) {
            resultMap.put("state", rs);
            return resultMap;
        }
        Gson g = PUtils.getGsonInstance();
        //System.out.println(map.get("orderInfo"));
        //处理订单信息
        //客户端应该传来的信息  goods_uniq_id order_counts order_created_time order_pay_way  user_uniq_id order_state   由数据库生成的信息->order_id  由服务端生成的信息->order_number
        OrderInfo orderInfo = g.fromJson(String.valueOf(map.get("orderInfo")).trim(), OrderInfo.class);
        //1秒内(1/27000的重复率)
        orderInfo.setOrder_number(StringUtil.getOrderNumber());
        logger.info("支付宝下单,商户订单号为：" + orderInfo.getOrder_number());
        orderInfo.setOrder_state(StateCode.TradeState.PENDING_PAYMENT.name());
        //获取商品价格
        //获取商品信息
        GoodsInfo goodsInfo = payService.queryGoodsInfo(orderInfo.getGoods_uniq_id());
        String price = goodsInfo.getGoods_onsale_buy_uprice();
        BigDecimal bPrice = new BigDecimal(price);
        BigDecimal bCount = new BigDecimal(orderInfo.getOrder_counts());
        BigDecimal bTotal = bPrice.multiply(bCount);
        String total = bTotal.setScale(2, RoundingMode.HALF_UP).toString();
        //是否会影响精度
        //创建商户支付宝订单(因为需要记录每次支付宝支付的记录信息，单独存一个表跟商户订单表关联，以便以后查证)
        AlipaymentOrder alipaymentOrder = new AlipaymentOrder();
        //商户订单号
        alipaymentOrder.setOrder_number(orderInfo.getOrder_number());
        //交易状态
        alipaymentOrder.setAorder_trade_state(StateCode.TradeState.PENDING_PAYMENT.name());
        //订单金额
        alipaymentOrder.setAorder_total_amount(total);
        //实收金额
        alipaymentOrder.setAorder_receipt_amount(total);
        //开票金额
        alipaymentOrder.setAorder_invoice_amount(total);
        //付款金额
        alipaymentOrder.setAorder_buyer_pay_amount(total);
        //总退款金额
        alipaymentOrder.setAorder_refund_fee(total);
        //保存支付宝支付订单信息 保存失败怎么办 @Transactional 回滚
        payService.saveAlipaymentOrder(alipaymentOrder);
        //保存订单信息
        logger.debug("保存订单信息，订单号是：" + orderInfo.getOrder_number());
        payService.saveOrder(orderInfo);
        try {
            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
            String a = PropertyUtil.get("/pay.properties","A.getWayPath","");
            String b = PropertyUtil.get("/pay.properties","A.appId","");
            String c = PropertyUtil.get("/pay.properties","A.appPrivateKey","");
            String d = PropertyUtil.get("/pay.properties","A.CHARSET","");
            String e = PropertyUtil.get("/pay.properties","A.aliPublicKey","");
            String f = PropertyUtil.get("/pay.properties","A.signType","");
            System.out.println(a + b + c + d + e + f);
            AlipayClient alipayClient = new DefaultAlipayClient(a, b,c,"JSON", d,e, f);
            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();
            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            //业务参数传入,可以传很多，参考API
            //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
            //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.setBody(goodsInfo.getGoods_description());
            //goodsInfo.getGoods_name()  商品名称
            model.setSubject(goodsInfo.getGoods_name());
            //商户订单号(自动生成)
            model.setOutTradeNo(orderInfo.getOrder_number());
            //交易超时时间
            model.setTimeoutExpress("30m");
            //price  支付金额
            model.setTotalAmount("0.01");
            //销售产品码（固定值）
            model.setProductCode("QUICK_MSECURITY_PAY");
            ali_request.setBizModel(model);
            logger.info("异步通知的地址为：" + PropertyUtil.get("/pay.properties","A.notifyUrl",""));
            //异步回调地址（后台）
            ali_request.setNotifyUrl(PropertyUtil.get("/pay.properties","A.notifyUrl",""));
            // 这里和普通的接口调用不同，使用的是sdkExecute  返回支付宝订单信息(预处理)
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request);
            //就是orderString 可以直接给APP请求，无需再做处理。
            String orderString = alipayTradeAppPayResponse.getBody();
            resultMap.put("data", orderString);
            resultMap.put("state", StateCode.DATA_RETURN_SUCCESS);
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;

    }

    @Transactional
    @RequestMapping(path = "queryPayResult", method = RequestMethod.POST)
    private Map queryPayResult(@RequestBody Map map) {
        Map resultMap = new HashMap(16);
        String order_number = (String) map.get("order_number");
        logger.debug("order_number:" + order_number);
        OrderInfo orderInfo = payService.queryOrderInfo(order_number);
        resultMap.put("state", StateCode.DATA_RETURN_SUCCESS);
        resultMap.put("data", orderInfo);
        return resultMap;
    }
}
