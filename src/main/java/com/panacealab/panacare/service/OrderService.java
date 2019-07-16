package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;

import java.util.Map;

public interface OrderService {
    Map saveOrder(String goods_uniq_id, String counts, String order_number, String order_state, String order_created_time);

  /*  Map createWXPayOrder(String user_uniq_id, String order_number);*/

    OrderInfo queryByUID(String out_trade_no);

    GoodsInfo queryGoodsInfoByUID(String goods_uniq_id);

    String changeOrderState(OrderInfo orderInfo);
}
