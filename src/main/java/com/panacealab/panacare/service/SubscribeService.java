package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.SubscribeInfo;

import java.util.Map;

public interface SubscribeService {
    Map getSubscribeInfoByUid(String user_uniq_id);

    Map getSubscribeInfoAll();

    String addSubscribeInfo(SubscribeInfo subscribeInfo);
}
