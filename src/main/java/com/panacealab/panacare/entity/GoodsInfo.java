package com.panacealab.panacare.entity;

public class GoodsInfo {
    private Integer goods_id;
    private String goods_uniq_id;
    private String goods_name;
    private String goods_subscribe_uprice;
    private String goods_onsale_subscribe_uprice;
    private String goods_buy_uprice;
    private String goods_onsale_buy_uprice;
    private String goods_palate;
    private String goods_palate_description;
    private String goods_type;
    private String goods_type_description;
    private String goods_description;
    private Integer goods_stock;
    private Integer goods_state;
    private String goods_reserved;


    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_uniq_id() {
        return goods_uniq_id;
    }

    public void setGoods_uniq_id(String goods_uniq_id) {
        this.goods_uniq_id = goods_uniq_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_subscribe_uprice() {
        return goods_subscribe_uprice;
    }

    public void setGoods_subscribe_uprice(String goods_subscribe_uprice) {
        this.goods_subscribe_uprice = goods_subscribe_uprice;
    }

    public String getGoods_onsale_subscribe_uprice() {
        return goods_onsale_subscribe_uprice;
    }

    public void setGoods_onsale_subscribe_uprice(String goods_onsale_subscribe_uprice) {
        this.goods_onsale_subscribe_uprice = goods_onsale_subscribe_uprice;
    }

    public String getGoods_buy_uprice() {
        return goods_buy_uprice;
    }

    public void setGoods_buy_uprice(String goods_buy_uprice) {
        this.goods_buy_uprice = goods_buy_uprice;
    }

    public String getGoods_onsale_buy_uprice() {
        return goods_onsale_buy_uprice;
    }

    public void setGoods_onsale_buy_uprice(String goods_onsale_buy_uprice) {
        this.goods_onsale_buy_uprice = goods_onsale_buy_uprice;
    }

    public String getGoods_palate() {
        return goods_palate;
    }

    public void setGoods_palate(String goods_palate) {
        this.goods_palate = goods_palate;
    }

    public String getGoods_palate_description() {
        return goods_palate_description;
    }

    public void setGoods_palate_description(String goods_palate_description) {
        this.goods_palate_description = goods_palate_description;
    }

    public String getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(String goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_type_description() {
        return goods_type_description;
    }

    public void setGoods_type_description(String goods_type_description) {
        this.goods_type_description = goods_type_description;
    }

    public String getGoods_description() {
        return goods_description;
    }

    public void setGoods_description(String goods_description) {
        this.goods_description = goods_description;
    }

    public Integer getGoods_stock() {
        return goods_stock;
    }

    public void setGoods_stock(Integer goods_stock) {
        this.goods_stock = goods_stock;
    }

    public Integer getGoods_state() {
        return goods_state;
    }

    public void setGoods_state(Integer goods_state) {
        this.goods_state = goods_state;
    }

    public String getGoods_reserved() {
        return goods_reserved;
    }

    public void setGoods_reserved(String goods_reserved) {
        this.goods_reserved = goods_reserved;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "goods_id=" + goods_id +
                ", goods_uniq_id='" + goods_uniq_id + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_subscribe_uprice='" + goods_subscribe_uprice + '\'' +
                ", goods_onsale_subscribe_uprice='" + goods_onsale_subscribe_uprice + '\'' +
                ", goods_buy_uprice='" + goods_buy_uprice + '\'' +
                ", goods_onsale_buy_uprice='" + goods_onsale_buy_uprice + '\'' +
                ", goods_palate='" + goods_palate + '\'' +
                ", goods_palate_description='" + goods_palate_description + '\'' +
                ", goods_type='" + goods_type + '\'' +
                ", goods_type_description='" + goods_type_description + '\'' +
                ", goods_description='" + goods_description + '\'' +
                ", goods_stock=" + goods_stock +
                ", goods_state=" + goods_state +
                ", goods_reserved='" + goods_reserved + '\'' +
                '}';
    }
}
