package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.RecommendRewardRecord;

import java.util.Map;

public interface RecommendService {
    String addRecommendInfo(String user_uniq_id, String user_referee);

    Map getRecommendRewardInfo();

    /**
     * 通过uid获取该用户的奖励领取记录
     * @param user_uniq_id uid
     * @return Map
     *
     * */
    Map<String,Object> getRecommendRewardRecord(String user_uniq_id);

    String addRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord);

    /**
     *可以更新但没必要
     */
    @Deprecated
    String updateRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord);
    Map getUserReferee(String user_uniq_id);
    Map getRecommendRewardCount(String user_uniq_id);

}
