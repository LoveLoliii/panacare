package com.panacealab.panacare.entity;

public enum AlipayTradeStatus {
    TRADE_SUCCESS,//交易支付成功
    TRADE_FINISHED,//交易结束，不可退款
    TRADE_CLOSED,//未付款交易超时关闭，或支付完成后全额退款
    WAIT_BUYER_PAY//交易创建，等待买家付款


}
