package com.panacealab.panacare.entity;

public class AlipaymentOrder {              //支付宝订单

    private Integer aorder_id;              //ID
    private String order_number;            //订单号
    private String aorder_trade_state;      //交易状态
    private String aorder_total_amount;     //订单金额
    private String aorder_receipt_amount;   //实收金额
    private String aorder_invoice_amount;   //开票金额
    private String aorder_buyer_pay_amount; //付款金额
    private String aorder_refund_fee;      //总付款金额

    public Integer getAorder_id() {
        return aorder_id;
    }

    public void setAorder_id(Integer aorder_id) {
        this.aorder_id = aorder_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getAorder_trade_state() {
        return aorder_trade_state;
    }

    public void setAorder_trade_state(String aorder_trade_state) {
        this.aorder_trade_state = aorder_trade_state;
    }

    public String getAorder_total_amount() {
        return aorder_total_amount;
    }

    public void setAorder_total_amount(String aorder_total_amount) {
        this.aorder_total_amount = aorder_total_amount;
    }

    public String getAorder_receipt_amount() {
        return aorder_receipt_amount;
    }

    public void setAorder_receipt_amount(String aorder_receipt_amount) {
        this.aorder_receipt_amount = aorder_receipt_amount;
    }

    public String getAorder_invoice_amount() {
        return aorder_invoice_amount;
    }

    public void setAorder_invoice_amount(String aorder_invoice_amount) {
        this.aorder_invoice_amount = aorder_invoice_amount;
    }

    public String getAorder_buyer_pay_amount() {
        return aorder_buyer_pay_amount;
    }

    public void setAorder_buyer_pay_amount(String aorder_buyer_pay_amount) {
        this.aorder_buyer_pay_amount = aorder_buyer_pay_amount;
    }

    public String getAorder_refund_fee() {
        return aorder_refund_fee;
    }

    public void setAorder_refund_fee(String aorder_refund_fee) {
        this.aorder_refund_fee = aorder_refund_fee;
    }
}
