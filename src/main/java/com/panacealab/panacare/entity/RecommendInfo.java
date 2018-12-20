package com.panacealab.panacare.entity;

public class RecommendInfo {

        private Integer recommend_record_id;
        private String user_uniq_id;
        private String recommend_user_uniq_id;

    public Integer getRecommend_record_id() {
        return recommend_record_id;
    }

    public void setRecommend_record_id(Integer recommend_record_id) {
        this.recommend_record_id = recommend_record_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public String getRecommend_user_uniq_id() {
        return recommend_user_uniq_id;
    }

    public void setRecommend_user_uniq_id(String recommend_user_uniq_id) {
        this.recommend_user_uniq_id = recommend_user_uniq_id;
    }

    @Override
    public String toString() {
        return "RecommendInfo{" +
                "recommend_record_id=" + recommend_record_id +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", recommend_user_uniq_id='" + recommend_user_uniq_id + '\'' +
                '}';
    }
}
