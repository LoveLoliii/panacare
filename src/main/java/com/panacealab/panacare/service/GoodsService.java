package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.GoodsInfo;

import java.util.Map;

public interface GoodsService {
    Map getAllGoodsInfo();

    String addGoodsInfo(GoodsInfo goodsInfo);
}
