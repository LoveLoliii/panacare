package com.panacealab.panacare.service;

import java.util.Map;

/**
 * @author Lovelo
 */
public interface LoginService {
    /**
     * 验证账号与密码是否正确
     * @param account 账号
     * @param pwd 密码
     * @return Map
     */
    Map check(String account, String pwd);

    /**
     * 验证管理员账号与密码是否正确
     * @param account 管理员账号
     * @param pwd 密码
     * @return Map
     */
    Map adminCheck(String account, String pwd);
    /**
     * 检查是否存在这样的openId
     * @param openid 唯一用户标识
     * @return 是否存在
     * */
    boolean isExist(String openid);
    /**
     * 获取登陆态
     * @param openid 1
     * @return 返回token
     * */
    String getWxLoginState(String openid);

    /**
     * 第一次使用的微信用户进行登陆
     * @param openid uid
     * @param sk secret_key
     * @param un user name
     * @param sex 性别
     *
     * */
    void registerWxUser(String openid,String sk,String un,String sex);
    /**
     * 更新微信用户login的sessionKey\
     * @param sessionKey 
     * */
    void updateSessionKey(String sessionKey);
}
