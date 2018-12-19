package com.panacealab.panacare.entity;

public class TaskInfo {


    private Integer task_id;
    private String task_name;
    private String task_content;
    private String task_reward;
    private Integer task_state;
    private String task_create_time;
    private String task_end_time;


    public Integer getTask_id() {
        return task_id;
    }

    public void setTask_id(Integer task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getTask_content() {
        return task_content;
    }

    public void setTask_content(String task_content) {
        this.task_content = task_content;
    }

    public String getTask_reward() {
        return task_reward;
    }

    public void setTask_reward(String task_reward) {
        this.task_reward = task_reward;
    }

    public Integer getTask_state() {
        return task_state;
    }

    public void setTask_state(Integer task_state) {
        this.task_state = task_state;
    }

    public String getTask_create_time() {
        return task_create_time;
    }

    public void setTask_create_time(String task_create_time) {
        this.task_create_time = task_create_time;
    }

    public String getTask_end_time() {
        return task_end_time;
    }

    public void setTask_end_time(String task_end_time) {
        this.task_end_time = task_end_time;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "task_id=" + task_id +
                ", task_name='" + task_name + '\'' +
                ", task_content='" + task_content + '\'' +
                ", task_reward='" + task_reward + '\'' +
                ", task_state=" + task_state +
                ", task_create_time='" + task_create_time + '\'' +
                ", task_end_time='" + task_end_time + '\'' +
                '}';
    }
}
