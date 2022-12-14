package com.panacealab.panacare.entity;
/**
 * @author loveloliii
 * */
public class SubscribeInfo {
    private Integer subscribe_id;
    private String goods_uniq_id;
    private String user_uniq_id;
    private String subscribe_state;


    public Integer getSubscribe_id() {
        return subscribe_id;
    }

    public void setSubscribe_id(Integer subscribe_id) {
        this.subscribe_id = subscribe_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public String getGoods_uniq_id() {
        return goods_uniq_id;
    }

    public void setGoods_uniq_id(String goods_uniq_id) {
        this.goods_uniq_id = goods_uniq_id;
    }

    public String getSubscribe_state() {
        return subscribe_state;
    }

    public void setSubscribe_state(String subscribe_state) {
        this.subscribe_state = subscribe_state;
    }

    @Override
    public String toString() {
        return "SubscribeInfo{" +
                "subscribe_id=" + subscribe_id +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", goods_uniq_id=" + goods_uniq_id +
                ", subscribe_state='" + subscribe_state + '\'' +
                '}';
    }
}
