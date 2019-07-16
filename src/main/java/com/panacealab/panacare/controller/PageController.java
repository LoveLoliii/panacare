package com.panacealab.panacare.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author loveloliii
 * 用于page跳转
 * @date 2019/5/30.
 */
@RestController
public class PageController implements ErrorController {
    /**
     * 错误页面返回信息
     * */
    @RequestMapping("/error")
    private String errorPage(){
        return "Something Error~ Please Check The Request~";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
