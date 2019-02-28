package com.panacealab.panacare.entity;

/**
 * @author Loveloliii
 */
public class RecommendReward {

    private Integer recommend_reward_id;
    private Integer recommend_reward_mount;
    private Integer recommend_reward_content;

    public Integer getRecommend_reward_id() {
        return recommend_reward_id;
    }

    public void setRecommend_reward_id(Integer recommend_reward_id) {
        this.recommend_reward_id = recommend_reward_id;
    }

    public Integer getRecommend_reward_mount() {
        return recommend_reward_mount;
    }

    public void setRecommend_reward_mount(Integer recommend_reward_mount) {
        this.recommend_reward_mount = recommend_reward_mount;
    }

    public Integer getRecommend_reward_content() {
        return recommend_reward_content;
    }

    public void setRecommend_reward_content(Integer recommend_reward_content) {
        this.recommend_reward_content = recommend_reward_content;
    }

    @Override
    public String toString() {
        return "RecommendReward{" +
                "recommend_reward_id=" + recommend_reward_id +
                ", recommend_reward_mount=" + recommend_reward_mount +
                ", recommend_reward_content=" + recommend_reward_content +
                '}';
    }
}
