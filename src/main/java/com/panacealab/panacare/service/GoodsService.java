package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.GoodsInfo;

import java.util.Map;

/**
 * @author Loveloliii
 */
public interface GoodsService {
    /**
     * 获取所有商品信息
     * @return Map
     */
    Map getAllGoodsInfo();

    /**
     * 添加商品信息
     * @param goodsInfo 商品信息
     * @return String
     */
    String addGoodsInfo(GoodsInfo goodsInfo);

    /**
     * 更新或者下架（更改上架状态）商品
     * @param goodsInfo 商品信息
     * @return String
     */
    int updateGoodsInfo(GoodsInfo goodsInfo);
}
