package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.RecommendRewardRecord;

import java.util.Map;

public interface RecommendService {
    String addRecommendInfo(String user_uniq_id, String user_referee);

    Map getRecommendRewardInfo();

    Map getRecommendRewardRecord();
    String addRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord);




@Deprecated
//可以更新但没必要
    String updateRecommendRewardRecord(RecommendRewardRecord recommendRewardRecord);

    Map getUserReferee(String user_uniq_id);

    Map getRecommendRewardCount(String user_uniq_id);

}
