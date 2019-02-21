package com.panacealab.panacare.entity;

public class RepairInfo {
    private Integer repair_id;
    private String user_uniq_id;
    private Integer customer_service_id;
    private String repair_problem_description;
    private String repair_file_id;
    private String repair_create_time;
    private String repair_handling_state;

    public Integer getRepair_id() {
        return repair_id;
    }

    public void setRepair_id(Integer repair_id) {
        this.repair_id = repair_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public Integer getCustomer_service_id() {
        return customer_service_id;
    }

    public void setCustomer_service_id(Integer customer_service_id) {
        this.customer_service_id = customer_service_id;
    }

    public String getRepair_problem_description() {
        return repair_problem_description;
    }

    public void setRepair_problem_description(String repair_problem_description) {
        this.repair_problem_description = repair_problem_description;
    }

    public String getRepair_file_id() {
        return repair_file_id;
    }

    public void setRepair_file_id(String repair_file_id) {
        this.repair_file_id = repair_file_id;
    }

    public String getRepair_create_time() {
        return repair_create_time;
    }

    public void setRepair_create_time(String repair_create_time) {
        this.repair_create_time = repair_create_time;
    }


    public String getRepair_handling_state() {
        return repair_handling_state;
    }

    public void setRepair_handling_state(String repair_handling_state) {
        this.repair_handling_state = repair_handling_state;
    }

    @Override
    public String toString() {
        return "RepairInfo{" +
                "repair_id=" + repair_id +
                ", user_uniq_id='" + user_uniq_id + '\'' +
                ", customer_service_id=" + customer_service_id +
                ", repair_problem_description='" + repair_problem_description + '\'' +
                ", repair_file_id=" + repair_file_id +
                ", repair_create_time='" + repair_create_time + '\'' +
                ", repair_handing_state='" + repair_handling_state + '\'' +
                '}';
    }
}
