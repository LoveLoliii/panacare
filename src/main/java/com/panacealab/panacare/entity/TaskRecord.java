package com.panacealab.panacare.entity;

public class TaskRecord {

        private Integer task_record_id;
        private String user_uniq_id;
        private Integer task_id;
        private String task_progress;
        private String task_finish_time;
        private String task_reward_get_time;
        private String task_reward_get_state;


    public Integer getTask_record_id() {
        return task_record_id;
    }

    public void setTask_record_id(Integer task_record_id) {
        this.task_record_id = task_record_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getTask_progress() {
        return task_progress;
    }

    public void setTask_progress(String task_progress) {
        this.task_progress = task_progress;
    }

    public String getTask_finish_time() {
        return task_finish_time;
    }

    public void setTask_finish_time(String task_finish_time) {
        this.task_finish_time = task_finish_time;
    }

    public String getTask_reward_get_time() {
        return task_reward_get_time;
    }

    public void setTask_reward_get_time(String task_reward_get_time) {
        this.task_reward_get_time = task_reward_get_time;
    }

    public String getTask_reward_get_state() {
        return task_reward_get_state;
    }

    public void setTask_reward_get_state(String task_reward_get_state) {
        this.task_reward_get_state = task_reward_get_state;
    }


    @Override
    public String toString() {
        return "TaskRecordController{" +
                "task_record_id=" + task_record_id +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", task_id=" + task_id +
                ", task_progress='" + task_progress + '\'' +
                ", task_finish_time='" + task_finish_time + '\'' +
                ", task_reward_get_time='" + task_reward_get_time + '\'' +
                ", task_reward_get_state='" + task_reward_get_state + '\'' +
                '}';
    }
}
