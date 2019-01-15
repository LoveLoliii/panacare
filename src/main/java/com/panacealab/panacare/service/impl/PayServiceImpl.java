package com.panacealab.panacare.service.impl;

import com.panacealab.panacare.dao.PayDao;
import com.panacealab.panacare.entity.AlipaymentOrder;
import com.panacealab.panacare.entity.GoodsInfo;
import com.panacealab.panacare.entity.OrderInfo;
import com.panacealab.panacare.entity.SubscribeInfo;
import com.panacealab.panacare.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Autowired
    private PayDao payDao;
    @Override
    public void saveAlipaymentOrder(AlipaymentOrder alipaymentOrder) {

        payDao.insert(alipaymentOrder);


    }

    @Override
    public void saveOrder(OrderInfo orderInfo) {
        payDao.insertOrder(orderInfo);
    }

    @Override
    public GoodsInfo queryGoodsInfo(String goods_uniq_id) {

        return payDao.queryGoodsInfo(goods_uniq_id);
    }

    @Override
    public void saveSubscribeInfo(SubscribeInfo subscribeInfo) {
        payDao.insertSubscribeInfo(subscribeInfo);
    }
}
