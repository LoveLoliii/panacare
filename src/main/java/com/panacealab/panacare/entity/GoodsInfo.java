package com.panacealab.panacare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author Loveloliii
 */
@Data
@Builder
@AllArgsConstructor
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
    /**
     * 无构造参数时 会报 No constructor found in xxx ，添加上下面的无参构造参数会报参数不匹配 添加上lombok的 @AllArgsConstructor后正常。?
     * */
    public GoodsInfo() {
    }
}
