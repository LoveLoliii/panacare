package com.panacealab.panacare.dao;

import com.panacealab.panacare.entity.AlipaymentOrder;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.entity.SubscribeInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayDao {
    void insert(AlipaymentOrder alipaymentOrder);

    void insertOrder(OrderInfo orderInfo);

    GoodsInfo queryGoodsInfo(String goods_uniq_id);

    void insertSubscribeInfo(SubscribeInfo subscribeInfo);
}
