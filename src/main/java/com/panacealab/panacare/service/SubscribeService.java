package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.SubscribeInfo;

import java.util.List;
import java.util.Map;

/**
 * @author loveloliii
 */
public interface SubscribeService {
    Map getSubscribeInfoByUid(String userUniqId);

    Map getSubscribeInfoAll();
    /**
     * 添加订阅
     * @param subscribeInfo 订阅信息
     * @return String 操作结果
     */
    String addSubscribeInfo(SubscribeInfo subscribeInfo);

    String modifySubscribeInfo(SubscribeInfo subscribeInfo);

    /**
     * 查询重复订阅
     * @param goodsUniqId gud
     * @param userUniqId  uud
     * @return int
     */
    int checkRepeatSub(String userUniqId, String goodsUniqId);

    Map getSubscribeInfoForApp(String userUniqId);
}
