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
}
