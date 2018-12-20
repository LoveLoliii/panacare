package com.panacealab.panacare.entity;

public class RecommendRewardRecord {

    private Integer recommend_reward_record_id;
    private String user_uniq_id;
    private Integer recommend_reward_id;
    private String recommend_reward_record_sendtime;
    private Integer recommend_reward_record_getstate;


    public Integer getRecommend_reward_record_id() {
        return recommend_reward_record_id;
    }

    public void setRecommend_reward_record_id(Integer recommend_reward_record_id) {
        this.recommend_reward_record_id = recommend_reward_record_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public Integer getRecommend_reward_id() {
        return recommend_reward_id;
    }

    public void setRecommend_reward_id(Integer recommend_reward_id) {
        this.recommend_reward_id = recommend_reward_id;
    }

    public String getRecommend_reward_record_sendtime() {
        return recommend_reward_record_sendtime;
    }

    public void setRecommend_reward_record_sendtime(String recommend_reward_record_sendtime) {
        this.recommend_reward_record_sendtime = recommend_reward_record_sendtime;
    }

    public Integer getRecommend_reward_record_getstate() {
        return recommend_reward_record_getstate;
    }

    public void setRecommend_reward_record_getstate(Integer recommend_reward_record_getstate) {
        this.recommend_reward_record_getstate = recommend_reward_record_getstate;
    }

    @Override
    public String toString() {
        return "RecommendRewardRecord{" +
                "recommend_reward_record_id=" + recommend_reward_record_id +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", recommend_reward_id=" + recommend_reward_id +
                ", recommend_reward_record_sendtime='" + recommend_reward_record_sendtime + '\'' +
                '}';
    }
}
