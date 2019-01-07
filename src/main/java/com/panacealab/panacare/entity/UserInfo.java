package com.panacealab.panacare.entity;



//import javax.persistence.*;

import java.io.Serializable;

/*@Entity
@Table(name = "user_info")*/
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer user_id;
    private String user_uniq_id;
    private String user_name;
    private String user_pwd;
    private String user_sex;
    private String user_both;
    private String user_mail;
    private String user_register_time;
    private String user_condition_code;
    private String user_referee;
    private String user_phone_num;
    private String user_address;
    private Integer user_validity;
    private Integer user_authority;
/*    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_both() {
        return user_both;
    }

    public void setUser_both(String user_both) {
        this.user_both = user_both;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_register_time() {
        return user_register_time;
    }

    public void setUser_register_time(String user_register_time) {
        this.user_register_time = user_register_time;
    }

    public String getUser_condition_code() {
        return user_condition_code;
    }

    public void setUser_condition_code(String user_condition_code) {
        this.user_condition_code = user_condition_code;
    }

    public String getUser_referee() {
        return user_referee;
    }

    public void setUser_referee(String user_referee) {
        this.user_referee = user_referee;
    }

    public String getUser_phone_num() {
        return user_phone_num;
    }

    public void setUser_phone_num(String user_phone_num) {
        this.user_phone_num = user_phone_num;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public Integer getUser_validity() {
        return user_validity;
    }

    public void setUser_validity(Integer user_validity) {
        this.user_validity = user_validity;
    }

    public Integer getUser_authority() {
        return user_authority;
    }

    public void setUser_authority(Integer user_authority) {
        this.user_authority = user_authority;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", user_sex='" + user_sex + '\'' +
                ", user_both='" + user_both + '\'' +
                ", user_mail='" + user_mail + '\'' +
                ", user_register_time='" + user_register_time + '\'' +
                ", user_condition_code='" + user_condition_code + '\'' +
                ", user_referee='" + user_referee + '\'' +
                ", user_phone_num='" + user_phone_num + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_validity=" + user_validity +
                '}';
    }
}
