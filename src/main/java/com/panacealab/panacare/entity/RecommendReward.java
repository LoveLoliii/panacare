package com.panacealab.panacare.entity;

/**
 * @author Loveloliii
 */
public class RecommendReward {

    private Integer recommend_reward_id;
    private Integer recommend_reward_count;
    private String recommend_reward_content;

    public Integer getRecommend_reward_id() {
        return recommend_reward_id;
    }

    public void setRecommend_reward_id(Integer recommend_reward_id) {
        this.recommend_reward_id = recommend_reward_id;
    }

    public Integer getRecommend_reward_count() {
        return recommend_reward_count;
    }

    public void setRecommend_reward_count(Integer recommend_reward_count) {
        this.recommend_reward_count = recommend_reward_count;
    }

    public String getRecommend_reward_content() {
        return recommend_reward_content;
    }

    public void setRecommend_reward_content(String recommend_reward_content) {
        this.recommend_reward_content = recommend_reward_content;
    }

    @Override
    public String toString() {
        return "RecommendReward{" +
                "recommend_reward_id=" + recommend_reward_id +
                ", recommend_reward_count=" + recommend_reward_count +
                ", recommend_reward_content=" + recommend_reward_content +
                '}';
    }
}
