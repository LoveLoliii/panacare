package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.UserInfo;

import java.util.Map;

/**
 * @author Loveloliii
 */
public interface SignService {
    /**
     * 获取验证码
     * @param mail 注册邮箱
     * @return Map
     */
    Map getVerificationCode(String mail);

    String verifyMail(String mail, String code);

    /**
     * 注册
     * @param u user info
     * @param mail 账号
     * @param code 验证码
     * @return String
     */
    String sign(UserInfo u, String mail, String code);
}
