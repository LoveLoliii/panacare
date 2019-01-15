package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.AlipaymentOrder;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.entity.SubscribeInfo;

public interface PayService {
    void saveAlipaymentOrder(AlipaymentOrder alipaymentOrder);

    void saveOrder(OrderInfo orderInfo);

    GoodsInfo queryGoodsInfo(String goods_uniq_id);

    void saveSubscribeInfo(SubscribeInfo subscribeInfo);
}
