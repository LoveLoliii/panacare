package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.OrderDao;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.service.OrderService;
import com.panacealab.panacare.utils.PayCommonUtil;
import com.panacealab.panacare.utils.PropertyUtil;
import com.panacealab.panacare.utils.StateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
@Service
public class OrderServiceImpl implements OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;

    @Transactional
    @Override
    public Map saveOrder(String goods_uniq_id, String counts, String order_number, String order_state, String order_created_time) {
        Map<String,String> map = new HashMap<>();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoods_uniq_id(goods_uniq_id);
        orderInfo.setOrder_counts(Integer.parseInt(counts));
        orderInfo.setOrder_created_time(order_created_time);
        orderInfo.setOrder_number(order_number);
        orderInfo.setOrder_state(order_state);
        //save
        int rs ;
        try{
            rs = orderDao.insert(orderInfo);
        }catch (Exception e){
            logger.error("订单信息插入失败！by:{}",e.getMessage());
            e.printStackTrace();
            map.put("state", StateCode.DATABASE_INSERT_ERROR);
            return map;
        }
        if(0==rs){
            map.put("state",StateCode.DATABASE_NOT_INSERT);
        }else {
            map.put("state",StateCode.DATABASE_INSERT_SUCCESS);
            map.put("data",order_number);
        }
        return map;
    }


/* @Override
    public Map createWXPayOrder(String user_uniq_id, String order_number) {
        Map map = new HashMap();
        // 检查订单状态
        OrderInfo orderInfo = orderDao.query(order_number);
        String state = orderInfo.getOrder_state();
        if(!StateCode.ORDER_PENDING_PAYMENT.equals(state)){
            //订单状态为 订单超时取消  用户完成支付 待发货 已发货 订单完成 订单关闭
            map.put("state",state);
           // map.put("data",);
            //返回的是订单状态。
            return  map;


        }else{
            //订单状态为待付款 流程继续

            SortedMap<Object, Object> parameters = PayCommonUtil.getWXPrePayID();
            //获取商品信息
            GoodsInfo goodsInfo = orderDao.queryGoods(orderInfo.getGoods_uniq_id());
            //检查商品信息是否有误。
            //调用统一下单api()
            parameters.put("body","panacea-lab:"+goodsInfo.getGoods_name());
          //  parameters.put("spbill_create_ip", this.request.getRemoteAddr());
            parameters.put("out_trade_no",orderInfo.getOrder_number());
            parameters.put("total_fee", "1"); // 测试时，每次支付一分钱，微信支付所传的金额是以分为单位的，因此实际开发中需要x100
            //订阅 与 购买的问题
           // parameters.put("total_fee", orderInfo.getOrder_counts()*100); // 上线后，将此代码放开
            // 设置签名
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign); // 封装请求参数结束
             String requestXML = PayCommonUtil.getRequestXml(parameters); // 获取xml结果);
            // 调用统一下单接口
            String result = PayCommonUtil.httpsRequest(PropertyUtil.get("/pay.properties","WxPay.payURL",""), "POST",
                    requestXML);
            logger.debug("调用统一下单接口：" + result);
            SortedMap<Object, Object> parMap = PayCommonUtil.startWXPay(result);
            logger.debug("最终的map是：" + parMap.toString());


            if(null != parMap){
                map.put("data",parMap);
                map.put("state",StateCode.SUCCESS);
            }else{
                map.put("state",StateCode.FAILURE);
            }
            //返回生成带签名的客户端的支付信息

        }

        return map;
    }
*/


    @Override
    public GoodsInfo queryGoodsInfoByUID(String goods_uniq_id) {

        return orderDao.queryGoods(goods_uniq_id);
    }

    @Transactional
    @Override
    public String changeOrderState(OrderInfo orderInfo) {
            int rs = orderDao.update(orderInfo);
        return rs==0?"":"";
    }

    @Override
    public OrderInfo queryByUID(String out_trade_no) {

        return  orderDao.query(out_trade_no);
    }
}
