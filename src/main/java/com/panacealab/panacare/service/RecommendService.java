package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.RecommendRewardRecord;

import java.util.Map;

/**
 * @author Loveloliii
 */
public interface RecommendService {
    String addRecommendInfo(String user_uniq_id, String user_referee);


    /**
     * 获取奖励内容
     * @return Map
     */
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

    /**
     * 改变奖励领取状态 从未领取改为领取
     * @param userUniqId uud
     * @param recommendRewardRecordId rri
     * @return int
     */
    int applyReward(String userUniqId, String recommendRewardRecordId);
}
