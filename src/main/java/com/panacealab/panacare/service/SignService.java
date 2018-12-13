package com.panacealab.panacare.service;

import com.panacealab.panacare.entity.UserInfo;

public interface SignService {
    String getVerificationCode(String mail);

    String verifyMail(String mail, String code);

    String sign(UserInfo u, String mail, String code);
}
