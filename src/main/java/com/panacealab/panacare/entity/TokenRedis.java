package com.panacealab.panacare.entity;

import java.io.Serializable;

public class TokenRedis implements Serializable {
    //private String redis_key ;
    private String user_uniq_id;//做key
    private String token;

  /*  public String getRedis_key() {
        return redis_key;
    }

    public void setRedis_key(String redis_key) {
        this.redis_key = redis_key;
    }*/

    public String getUser_uniq_id() {
        return user_uniq_id;
    }

    public void setUser_uniq_id(String user_uniq_id) {
        this.user_uniq_id = user_uniq_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
