package com.panacealab.panacare.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.google.gson.Gson;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.alipay.api.AlipayConstants.CHARSET;

@RestController
public class PayController {
    Logger logger = LoggerFactory.getLogger(PayController.class);

    @RequestMapping("getWay")
    private void getAliPayAsynNofity(HttpServletRequest request) {
        logger.debug("接收到支付宝的异步通知。");
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        String alipaypublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqIhoChXyibe7C6AJuD3KpW0SOvAJrYWIs0zuOMs5wKV96FUPzlKdDtTXtyOXHLZ+ISvNfEcTs/0266/RJuEvrDRVWWkYv01SRS+t8/KUPpbkuEp/2OKrdyPgJHi3GdiCHLYkK47zEPDfXXpCkSu4FGs8KZFLUeWPNmiTVnycSJzVuyz4Rxb8SooCrTzdYhOgbGVJO5ieSU1wXuL5IL0oR9uaqlRR7xky3IAEfIWbUZcF80+MpQYO25KAO/w+DHrWM8lnRZFrW5TLL4NdgSCznA33C4Bz4v+TT3icP0FxxuWj0OXk0CCOb0mmft6VYzwo83qulwyexqmkH1irJ1mDbwIDAQAB";
        String charset = "UTF-8";
        try {
            boolean flag = AlipaySignature.rsaCheckV1(params, alipaypublicKey, charset, "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
    }

    /***
     * 向app返回加签后的订单信息
     * @param map 包含原始的商品订单信息（不是指支付宝的订单信息
     * @return map 返回包含状态码和加签后的字符串hashmap容器
     * */
    @RequestMapping(path = "createAliPayOrderOld", method = RequestMethod.POST)
    private String createAliPayOrderOld(@RequestBody Map map) {
        Map resultMap = new HashMap();
        resultMap.put("state",StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)){
            resultMap.put("state",rs);
            //resultMap.put("data","");
            return "";
        }


        Gson g = new Gson();
        System.out.println(String.valueOf(map.get("orderInfo")));
        //处理订单信息
        OrderInfo orderInfo =  g.fromJson(String.valueOf(map.get("orderInfo")).trim(),OrderInfo.class);


        String privateKey = PropertyUtil.t("A.appPrivateKey");
        Map<String, String> params = new HashMap<String, String>();
        boolean rsa2 = (privateKey.length() > 0);
        params = PayCommonUtil.buildOrderParamMap(PropertyUtil.t("A.appIdDev"), rsa2, "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + "222222" + "\"}");//TODO 获取订单号getOutTradeNo()
        privateKey = rsa2 ? privateKey : PropertyUtil.t("A.appPrivateKeyRSA1");
        String sign = PayCommonUtil.getSign(params, privateKey, rsa2);
        String orderParam = PayCommonUtil.buildOrderParam(params);
        final String aliOrderInfo = orderParam + "&" + sign;
        System.out.println(aliOrderInfo);
        try {

           /* AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。*/
            resultMap.put("data",aliOrderInfo);
            resultMap.put("state",StateCode.Data_Return_Success);
            return aliOrderInfo;// URLEncoder.encode(aliOrderInfo, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


    @Transactional
    @RequestMapping(path = "createAliPayOrder", method = RequestMethod.POST)
    private Map createAliPayOrder(@RequestBody Map map) {
        Map resultMap = new HashMap();
        resultMap.put("state",StateCode.Initial_Code);
        //验证token
        String token = (String) map.get("token");
        String rs = TokenUtil.checkLoginState(token);
        if (!StateCode.Initial_Code.equals(rs)){
            resultMap.put("state",rs);
            //resultMap.put("data","");
            return resultMap;
        }

        logger.info("==================支付宝下单,商户订单号为："+"");
        //创建商户支付宝订单(因为需要记录每次支付宝支付的记录信息，单独存一个表跟商户订单表关联，以便以后查证)
       /* AlipaymentOrder alipaymentOrder=new AlipaymentOrder();
        alipaymentOrder.setClubOrderId(orderTest.getId().toString());//商家订单主键
        alipaymentOrder.setOutTradeNo(orderTest.getOutTradeNo());//商户订单号
        alipaymentOrder.setTradeStatus((byte) 0);//交易状态
        alipaymentOrder.setTotalAmount(Double.parseDouble(orderTest.getTotalAmount()));//订单金额
        alipaymentOrder.setReceiptAmount(0.00);//实收金额
        alipaymentOrder.setInvoiceAmount(0.00);//开票金额
        alipaymentOrder.setBuyerPayAmount(0.00);//付款金额
        alipaymentOrder.setRefundFee(0.00);	//总退款金额*/

        Gson g = new Gson();
        System.out.println(String.valueOf(map.get("orderInfo")));
        //处理订单信息
        //客户端应该传来的信息  goods_uniq_id order_counts order_created_time order_pay_way  user_uniq_id order_state由数据库生成的信息  order_id 由服务端生成的信息 order_number
        OrderInfo orderInfo =  g.fromJson(String.valueOf(map.get("orderInfo")).trim(),OrderInfo.class);
        //获取goods_id 用于拼接订单号
        String goods_id = "";//如果goods_id 不存在 说明 订单信息有问题 终止本次 并返回客户端错误信息
        orderInfo.setOrder_number(StringUtil.getOrderNumber(goods_id));
        try {

            //实例化客户端（参数：网关地址、商户appid、商户私钥、格式、编码、支付宝公钥、加密类型），为了取得预付订单信息
            String a = PropertyUtil.t("A.getWayPath");
            String b = PropertyUtil.t("A.appId");
            String c = PropertyUtil.t("A.appPrivateKey");
            String d = PropertyUtil.t("A.CHARSET");
            String e = PropertyUtil.t("A.aliPublicKey");
            String f = PropertyUtil.t("A.signType");
            System.out.println(a+b+c+d+e+f);
            AlipayClient alipayClient = new DefaultAlipayClient(a,b ,
                    c,"JSON", d,
                    e, f);

            //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest ali_request = new AlipayTradeAppPayRequest();

            //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

            //业务参数传入,可以传很多，参考API
            //model.setPassbackParams(URLEncoder.encode(request.getBody().toString())); //公用参数（附加数据）
            model.setBody("d");                       //对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
            model.setSubject("牙刷");                 //商品名称
            model.setOutTradeNo("111");           //商户订单号(自动生成)
            model.setTimeoutExpress("30m");     			  //交易超时时间
            model.setTotalAmount("0.01");         //支付金额
            model.setProductCode("QUICK_MSECURITY_PAY");        	  //销售产品码（固定值）
            ali_request.setBizModel(model);
            logger.info("====================异步通知的地址为："+"");
           // ali_request.setNotifyUrl(AlipayConfig.notify_url);        //异步回调地址（后台）
            //ali_request.setReturnUrl(AlipayConfig.return_url);	    //同步回调地址（APP）

            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayClient.sdkExecute(ali_request); //返回支付宝订单信息(预处理)
            String orderString=alipayTradeAppPayResponse.getBody();//就是orderString 可以直接给APP请求，无需再做处理。
            //this.createAlipayMentOrder(alipaymentOrder);//创建新的商户支付宝订单


            resultMap.put("data",  orderString);
            resultMap.put("state",StateCode.Data_Return_Success);
            return resultMap;// URLEncoder.encode(aliOrderInfo, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;

    }

}
