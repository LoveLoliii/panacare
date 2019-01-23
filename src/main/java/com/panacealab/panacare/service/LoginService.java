package com.panacealab.panacare.service;

import java.util.Map;

public interface LoginService {
    Map check(String account, String pwd);

    Map adminCheck(String account, String pwd);
}
