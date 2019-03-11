package com.panacealab.panacare.controller;

import com.panacealab.panacare.service.AppUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author loveloliii
 * @description 上传最新app，更新app版本，获取最新app版本，获取最新app
 * @date 2019/3/11.
 */
@Controller
public class AppUpdateController {
    @Autowired
    private AppUpdateService appUpdateService;

    @RequestMapping(path = "getAppVersion", method = RequestMethod.GET)
    private @ResponseBody
    Map getAppVersion(@RequestParam String product, @RequestParam int platform) {
        Map rsMap = new HashMap(2);
        String [] version = appUpdateService.getAppVersion(product,platform);

        return null;
    }

}
