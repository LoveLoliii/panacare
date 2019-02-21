package com.panacealab.panacare.entity;

/**
 * @author loveloliii
 * @description 意见与建议
 * @date 2019/2/21.
 */
public class Suggestion {

    private Integer suggestion_id;
    private String user_uniq_id;
    private String suggestion_description;
    private String suggestion_time;
    private Integer suggestion_state;

    public Integer getSuggestion_id() {
        return suggestion_id;
    }

    public void setSuggestion_id(Integer suggestion_id) {
        this.suggestion_id = suggestion_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public String getSuggestion_description() {
        return suggestion_description;
    }

    public void setSuggestion_description(String suggestion_description) {
        this.suggestion_description = suggestion_description;
    }

    public String getSuggestion_time() {
        return suggestion_time;
    }

    public void setSuggestion_time(String suggestion_time) {
        this.suggestion_time = suggestion_time;
    }

    public Integer getSuggestion_state() {
        return suggestion_state;
    }

    public void setSuggestion_state(Integer suggestion_state) {
        this.suggestion_state = suggestion_state;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "suggestion_id=" + suggestion_id +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", suggestion_description='" + suggestion_description + '\'' +
                ", suggestion_time='" + suggestion_time + '\'' +
                ", suggestion_state='" + suggestion_state + '\'' +
                '}';
    }
}
