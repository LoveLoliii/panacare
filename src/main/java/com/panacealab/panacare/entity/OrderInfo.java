package com.panacealab.panacare.entity;

public class OrderInfo {                //订单信息

    private Integer order_id;           //订单ID
    private String order_number;        //订单编号
    private String goods_uniq_id;       //商品uid
    private String user_uniq_id;        //用户UUID
    private Integer order_counts;       //数量
    private String order_state;         //订单状态
    private String order_created_time;  //订单生成时间
    private String order_pay_way;       //支付方式
    private String order_finished_time; //订单结束时间


    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getGoods_uniq_id() {
        return goods_uniq_id;
    }

    public void setGoods_uniq_id(String goods_uniq_id) {
        this.goods_uniq_id = goods_uniq_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public Integer getOrder_counts() {
        return order_counts;
    }

    public void setOrder_counts(Integer order_counts) {
        this.order_counts = order_counts;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_created_time() {
        return order_created_time;
    }

    public void setOrder_created_time(String order_created_time) {
        this.order_created_time = order_created_time;
    }

    public String getOrder_pay_way() {
        return order_pay_way;
    }

    public void setOrder_pay_way(String order_pay_way) {
        this.order_pay_way = order_pay_way;
    }

    public String getOrder_finished_time() {
        return order_finished_time;
    }

    public void setOrder_finished_time(String order_finished_time) {
        this.order_finished_time = order_finished_time;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "order_id=" + order_id +
                ", order_number='" + order_number + '\'' +
                ", goods_uniq_id='" + goods_uniq_id + '\'' +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", order_counts=" + order_counts +
                ", order_state='" + order_state + '\'' +
                ", order_created_time='" + order_created_time + '\'' +
                '}';
    }
}
