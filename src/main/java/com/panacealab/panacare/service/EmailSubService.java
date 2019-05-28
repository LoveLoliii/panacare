package com.panacealab.panacare.service;

/**
 * @author loveloliii
 * @date 2019/5/14.
 */
public interface EmailSubService {
    /**
     * 保存订阅产品信息的邮箱
     * @param email 邮箱
     * @param cr country/region
     * */
    void saveSubscribeEmail(String email,String cr);
}
